package com.mirwanda.nottiled.desktop;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.mirwanda.nottiled.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class CollabServerLauncher {
    private static final List<actvClients> activeClients = Collections.synchronizedList(new ArrayList<>());
    private static Server server;

    public static void main(String[] args) {
        int port = 5555;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid port specified. Using default port 5555.");
            }
        }

        System.out.println("Starting NotTiled Collaboration Server...");
        
        // Initialize Server with large buffer sizes for TMX map synchronization
        server = new Server(9999999, 9999999);
        Kryo serverKryo = server.getKryo();
        serverKryo.register(TextChat.class);
        serverKryo.register(layerhistory.class);
        serverKryo.register(PlayerState.class);
        serverKryo.register(command.class);

        server.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof TextChat) {
                    TextChat chat = (TextChat) object;
                    System.out.println("[Chat] Broadcast: " + chat.text);
                    server.sendToAllTCP(chat);
                } else if (object instanceof command) {
                    command cmd = (command) object;
                    handleCommand(connection, cmd);
                } else if (object instanceof layerhistory) {
                    layerhistory history = (layerhistory) object;
                    System.out.println("[Data] Broadcast layerhistory");
                    server.sendToAllTCP(history);
                }
            }

            @Override
            public void disconnected(Connection connection) {
                String roomToDestroy = null;
                synchronized (activeClients) {
                    Iterator<actvClients> iterator = activeClients.iterator();
                    while (iterator.hasNext()) {
                        actvClients av = iterator.next();
                        if (av.connId == connection.getID()) {
                            System.out.println("[Network] Client disconnected: " + av.id);
                            if (av.creator && av.room != null && !av.room.isEmpty()) {
                                roomToDestroy = av.room;
                            }
                            iterator.remove();
                        }
                    }
                    if (roomToDestroy != null) {
                        for (actvClients av : activeClients) {
                            if (roomToDestroy.equalsIgnoreCase(av.room)) {
                                av.room = "";
                                av.creator = false;
                            }
                        }
                    }
                }
                if (roomToDestroy != null) {
                    command cc = new command();
                    cc.command = "roomDestroyed";
                    cc.room = roomToDestroy;
                    server.sendToAllTCP(cc);
                    System.out.println("[Server] Creator disconnected. Broadcasted room destruction: " + roomToDestroy);
                }
            }
        });

        try {
            // Bind only TCP port to be fully compatible with Cloudflare Tunnels (which only proxy TCP)
            server.bind(port);
            server.start();
            System.out.println("--------------------------------------------------");
            System.out.println("NotTiled Server successfully started!");
            System.out.println("Listening on TCP Port: " + port);
            System.out.println("To connect, expose this port using cloudflared / TCP Tunnel.");
            System.out.println("--------------------------------------------------");
        } catch (IOException e) {
            System.err.println("Fatal Error: Could not bind to port " + port);
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void handleCommand(Connection connection, command cmd) {
        command reply = new command();
        
        switch (cmd.command) {
            case "registerID":
                actvClients acv = new actvClients();
                acv.id = cmd.data;
                acv.room = "";
                acv.creator = false;
                acv.connId = connection.getID();
                activeClients.add(acv);
                System.out.println("[Server] Registering client: " + acv.id);

                reply.from = acv.id;
                reply.command = "registered";
                connection.sendTCP(reply);
                break;

            case "createRoom":
                boolean roomOk = true;
                synchronized (activeClients) {
                    for (actvClients av : activeClients) {
                        if (av.creator && av.room.equalsIgnoreCase(cmd.room)) {
                            roomOk = false;
                            break;
                        }
                    }
                    if (roomOk) {
                        for (actvClients av : activeClients) {
                            if (av.id.equalsIgnoreCase(cmd.from)) {
                                av.room = cmd.room;
                                av.creator = true;
                                break;
                            }
                        }
                    }
                }

                if (roomOk) {
                    reply.command = "roomCreateOK";
                    reply.room = cmd.room;
                    connection.sendTCP(reply);
                    System.out.println("[Server] Room created: \"" + cmd.room + "\" by " + cmd.from);
                } else {
                    reply.command = "roomCreateFailed";
                    connection.sendTCP(reply);
                    System.out.println("[Server] Room creation failed (already exists): \"" + cmd.room + "\"");
                }
                break;

            case "destroyRoom":
                boolean isCreator = false;
                synchronized (activeClients) {
                    for (actvClients av : activeClients) {
                        if (av.id.equalsIgnoreCase(cmd.from) && av.creator
                                && av.room != null && av.room.equalsIgnoreCase(cmd.room)) {
                            isCreator = true;
                            break;
                        }
                    }
                    if (isCreator) {
                        for (actvClients av : activeClients) {
                            if (av.room.equalsIgnoreCase(cmd.room)) {
                                av.room = "";
                                av.creator = false;
                            }
                        }
                    }
                }
                if (isCreator) {
                    reply.command = "roomDestroyed";
                    reply.room = cmd.room;
                    server.sendToAllTCP(reply);
                    System.out.println("[Server] Room destroyed: \"" + cmd.room + "\" by " + cmd.from);
                } else {
                    System.out.println("[Server] Unauthorized room destruction by " + cmd.from + " for \"" + cmd.room + "\"");
                }
                break;

            case "joinRequest":
                boolean isAvail = false;
                synchronized (activeClients) {
                    for (actvClients av : activeClients) {
                        if (av.room.equalsIgnoreCase(cmd.room) && av.creator) {
                            isAvail = true;
                            break;
                        }
                    }
                    if (isAvail) {
                        for (actvClients av : activeClients) {
                            if (av.id.equalsIgnoreCase(cmd.from)) {
                                av.room = cmd.room;
                                break;
                            }
                        }
                    }
                }

                if (isAvail) {
                    reply.command = "joinRequestAccepted";
                    reply.room = cmd.room;
                    connection.sendTCP(reply);
                    System.out.println("[Server] Join request accepted for " + cmd.from + " to room \"" + cmd.room + "\"");

                    // Broadcast join info to notify other room members
                    command joinInfo = new command();
                    joinInfo.command = "joinInformation";
                    joinInfo.room = cmd.room;
                    joinInfo.data = cmd.from;
                    server.sendToAllTCP(joinInfo);
                } else {
                    reply.command = "joinRequestRejected";
                    reply.room = cmd.room;
                    connection.sendTCP(reply);
                    System.out.println("[Server] Join request rejected for " + cmd.from + " to room \"" + cmd.room + "\"");
                }
                break;

            case "leaveRequest":
                synchronized (activeClients) {
                    for (actvClients av : activeClients) {
                        if (av.id.equalsIgnoreCase(cmd.from)) {
                            av.room = "";
                            break;
                        }
                    }
                }
                System.out.println("[Server] Client left room: " + cmd.from + " from room \"" + cmd.room + "\"");
                
                reply.command = "leaveRequestAccepted";
                reply.room = cmd.room;
                connection.sendTCP(reply);

                command leaveInfo = new command();
                leaveInfo.command = "leaveInformation";
                leaveInfo.room = cmd.room;
                leaveInfo.data = cmd.from;
                server.sendToAllTCP(leaveInfo);
                break;

            case "startdata":
            case "data":
            case "startDataAll":
            case "dataAll":
            case "draw":
            case "startAsset":
            case "assetData":
            case "saveAsset":
                // Forward drawing and synchronization packets directly to all clients
                server.sendToAllTCP(cmd);
                break;

            case "readThisBoy":
                reply.command = "mapInformation";
                reply.from = cmd.from;
                reply.room = cmd.room;
                server.sendToAllTCP(reply);
                System.out.println("[Server] Requesting map synchronization from: " + cmd.from);
                break;

            case "allReadThis":
                reply.command = "mapInformationAll";
                reply.from = cmd.from;
                reply.room = cmd.room;
                server.sendToAllTCP(reply);
                System.out.println("[Server] Broadcasting map update to all in room: " + cmd.room);
                break;

            case "disconnect":
                int index = -1;
                String roomToDestroy = null;
                synchronized (activeClients) {
                    for (int i = 0; i < activeClients.size(); i++) {
                        if (activeClients.get(i).id.equalsIgnoreCase(cmd.from)) {
                            actvClients av = activeClients.get(i);
                            if (av.creator && av.room != null && !av.room.isEmpty()) {
                                roomToDestroy = av.room;
                            }
                            index = i;
                            break;
                        }
                    }
                    if (index != -1) {
                        activeClients.remove(index);
                        System.out.println("[Server] Client registered ID disconnected: " + cmd.from);
                    }
                    if (roomToDestroy != null) {
                        for (actvClients av : activeClients) {
                            if (roomToDestroy.equalsIgnoreCase(av.room)) {
                                av.room = "";
                                av.creator = false;
                            }
                        }
                    }
                }
                if (roomToDestroy != null) {
                    reply.command = "roomDestroyed";
                    reply.room = roomToDestroy;
                    server.sendToAllTCP(reply);
                    System.out.println("[Server] Creator disconnected. Broadcasted room destruction: " + roomToDestroy);
                }
                break;
        }
    }
}
