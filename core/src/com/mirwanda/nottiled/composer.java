package com.mirwanda.nottiled;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.leff.midi.MidiFile;
import com.leff.midi.MidiTrack;
import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.ProgramChange;
import com.leff.midi.event.meta.Tempo;
import com.leff.midi.event.meta.TimeSignature;
import com.leff.midi.examples.EventPrinter;
import com.leff.midi.util.MidiProcessor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class composer {
    String sequence;
    public composer(){

    }
    public composer(String sequence){
        this.sequence=sequence;
    }

    public int clayer=0;
    public int clayertick=0;
    public int cnote=0;
    public int cduration=0;
    public int cvoice=0; //channel
    public int cinstrument=0; //program
    public int ctick=0; //tick
    MidiTrack tempoTrack = new MidiTrack();
    MidiTrack noteTrack = new MidiTrack();


    public void setVoice(int voice){
        if (voice!=cvoice) {
            cvoice = voice;
            ctick = 0;
        }
    }

    public void setLayer(int layer){
        if (layer>clayer) {
            ctick = clayertick;
        }
        clayer = layer;
        clayertick=ctick;
    }

    public void changeProgram(int Program){
        if (cinstrument!=Program) {
            ProgramChange pc = new ProgramChange( ctick, cvoice, Program );
            noteTrack.insertEvent( pc );
        }
    }
    public void setInstrument(String instrument){
        switch (instrument.toUpperCase()){
            case "[PIANO]": changeProgram(0); break;
            case "[VIOLIN]": changeProgram(40); break;
            case "[FLUTE]": changeProgram(73); break;
            default: changeProgram(Integer.parseInt( instrument ) ); break;
        }

    }

    public void addNote(String note) {
        //C#123+C#5q+Dq
        String tnote=""; int toctave=5; String tlength="";
        String command= new String("");
        String sequence = note.toLowerCase();

        for (int i = 0; i < sequence.length(); i++) {
            String c = sequence.substring( i, i+1 );
            if ("abcdefgr#".contains( c )) {
                command+=c;
            }
        }
        tnote=command;

        command=new String("");
        for (int i = 0; i < sequence.length(); i++) {
            String c = sequence.substring( i, i+1 );
            if ("0123456789".contains( c )) {
                command+=c;
            }
        }
        toctave=(command.equalsIgnoreCase( "" )) ? 5 : Integer.parseInt(command);

        command=new String("");
        for (int i = 0; i < sequence.length(); i++) {
            String c = sequence.substring( i, i+1 );
            if ("whqistxo.".contains( c )) {
                command+=c;
            }
        }
        tlength=(command.equalsIgnoreCase( "" )) ? "q" : command;

        int inote=0;
        switch (tnote){
            case "c":  inote = 0;  break;
            case "c#": inote = 1;  break;
            case "d":  inote = 2;  break;
            case "d#": inote = 3;  break;
            case "e":  inote = 4;  break;
            case "f":  inote = 5;  break;
            case "f#": inote = 6;  break;
            case "g":  inote = 7;  break;
            case "g#": inote = 8;  break;
            case "a":  inote = 9;  break;
            case "a#": inote = 10; break;
            case "b":  inote = 11; break;
        }
        cnote= toctave*12+inote;

        long idur=0;
        switch (tlength){
            case "w": idur=1920;   break;
            case "h": idur=960;   break;
            case "q": idur=480;   break;
            case "i": idur=240;    break;
            case "s": idur=120;    break;
            case "t": idur=60;    break;
            case "x": idur=30;    break;
            case "o": idur=15;    break;

            case "w.": idur=1920+960;   break;
            case "h.": idur=960+480;   break;
            case "q.": idur=480+240;   break;
            case "i.": idur=240+120;    break;
            case "s.": idur=120+60;    break;
            case "t.": idur=60+30;    break;
            case "x.": idur=30+15;    break;
            case "o.": idur=15+7;    break;
        }

        if (tnote.toLowerCase().equalsIgnoreCase( "r" )){
            //Gdx.app.log("A",tnote);
            ctick+=idur;
        }else{
            noteTrack.insertNote(cvoice, cnote, 100, ctick, idur);
            if (!note.contains( "+" )) ctick+=idur;

        }

    }

    public void addDrum(String note) {
        //C#123+C#5q+Dq
        String tnote=""; int toctave=5; String tlength="";
        String command= new String("");
        String sequence = note.toUpperCase();


        tnote=sequence.substring( 0,sequence.indexOf( "]" )+1);

        tlength=sequence.substring( sequence.indexOf( "]" )+1).toLowerCase();
        Gdx.app.log(tlength+"","");
        int inote=0;
        switch (tnote){
            case "[ACOUSTIC_SNARE]":  inote = 38;  break;
            case "[BASS_DRUM]": inote = 36;  break;
            case "[PEDAL_HI_HAT]":  inote = 44;  break;
        }
        cnote= inote;

        long idur=0;
        switch (tlength){
            case "w": idur=1920;   break;
            case "h": idur=960;   break;
            case "q": idur=480;   break;
            case "i": idur=240;    break;
            case "s": idur=120;    break;
            case "t": idur=60;    break;
            case "x": idur=30;    break;
            case "o": idur=15;    break;

            case "w.": idur=1920+960;   break;
            case "h.": idur=960+480;   break;
            case "q.": idur=480+240;   break;
            case "i.": idur=240+120;    break;
            case "s.": idur=120+60;    break;
            case "t.": idur=60+30;    break;
            case "x.": idur=30+15;    break;
            case "o.": idur=15+7;    break;
        }

        if (tnote.toLowerCase().equalsIgnoreCase( "r" )){
            ctick+=idur;
        }else{
            noteTrack.insertNote(cvoice, cnote, 100, ctick, idur);
            if (!note.contains( "+" )) ctick+=idur;

        }



    }

    public void save(String path){
        // 1. Create some MidiTracks
        tempoTrack = new MidiTrack();
        noteTrack = new MidiTrack();

// 2. Add events to the tracks
// Track 0 is the tempo map
        //set the value for now. don't overcomplicate.
        TimeSignature ts = new TimeSignature();
        ts.setTimeSignature(4, 4, TimeSignature.DEFAULT_METER, TimeSignature.DEFAULT_DIVISION);

        Tempo tempo = new Tempo();
        tempo.setBpm(120);

        tempoTrack.insertEvent(ts);
        tempoTrack.insertEvent(tempo);

// Track 1 will have some notes in it
        String command=""; boolean block=false, blocked=false;
        for (int i=0;i<sequence.length();i++){
            String c = sequence.substring( i, i+1 );
            String exe =command;

            if (!c.equalsIgnoreCase( " " )) command+=c;
            if (c.equalsIgnoreCase( "[" ) && !blocked && exe.length()<2) {block=true; blocked=true; continue;}
            if (c.equalsIgnoreCase( "]" )) block=false;
            if (command.length()<=1) continue;
            if (block) continue;

            if ("TVLIABCDEFGRabcdefgr[".contains( c ) || i==sequence.length()-1) {
                if (i==sequence.length()-1) exe+=c;
                //Gdx.app.log( "Executed",exe );

                String execute = command.substring( 0,command.length()-1 );
                switch (exe.substring( 0, 1 )) {
                    case "V":
                        setVoice( Integer.parseInt( exe.substring( 1 ) ) );
                        break;
                    case "L":
                        setLayer(Integer.parseInt( exe.substring( 1 ) ) );
                        break;
                    case "I":
                        setInstrument( exe.substring( 1 ) );
                        break;
                    case "A": case "a":
                    case "B": case "b":
                    case "C": case "c":
                    case "D": case "d":
                    case "E": case "e":
                    case "F": case "f":
                    case "G": case "g":
                    case "R": case "r":
                        addNote( exe );
                        break;
                    case "[":
                        addDrum( exe );
                        break;
                    default:
                }

                command = c;
                exe="";
                blocked=false;
                if (c.equalsIgnoreCase( "[" ) && !blocked && exe.length()<2) {block=true; blocked=true; continue;}

            }


        }
// 3. Create a MidiFile with the tracks we created
        List<MidiTrack> tracks = new ArrayList<MidiTrack>();
        tracks.add(tempoTrack);
        tracks.add(noteTrack);

        MidiFile midi = new MidiFile(MidiFile.DEFAULT_RESOLUTION, tracks);
        //playmidi( midi );
// 4. Write the MIDI data to a file

        FileHandle fl = Gdx.files.external( path );
        File output = fl.file();
        try
        {
            midi.writeToFile(output);
        }
        catch(IOException e)
        {
            System.err.println(e);
        }


        /*
         */


    }

    private void playmidi(MidiFile midi){
        // Create a new MidiProcessor:
        MidiProcessor processor = new MidiProcessor(midi);

// Register for the events you're interested in:
        //EventPrinter ep = new EventPrinter("Individual Listener");
        //processor.registerEventListener(ep, Tempo.class);
        //processor.registerEventListener(ep, NoteOn.class);

// or listen for all events:
        EventPrinter ep2 = new EventPrinter("Listener For All");
        processor.registerEventListener(ep2, MidiEvent.class);

// Start the processor:
        processor.start();
    }
}
