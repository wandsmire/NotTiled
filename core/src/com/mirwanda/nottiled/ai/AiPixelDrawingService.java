package com.mirwanda.nottiled.ai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

/** AI draws small sprites for the Pixel Editor via palette-slot grids. */
public class AiPixelDrawingService {

    public interface Listener {
        void onSuccess(int[][] grid, boolean fullReplace);
        void onPartial(int[][] grid, boolean fullReplace);
        void onFailure(String message);
    }

    private static final Json JSON = new Json();
    private static final int MAX_PRIOR_TURNS = 8;
    private static final int MAX_ATTEMPTS = 2;
    private static final String APPLIED_ACK = "Applied to active layer.";

    public static void requestPixel(String apiKey, String apiUrl, String model, String userPrompt,
            PixelPaletteInfo palette, String layerSnapshot, java.util.List<String> priorSteps, Listener listener) {
        if (apiKey == null || apiKey.trim().isEmpty()) {
            listener.onFailure("missing_api_key");
            return;
        }
        if (palette == null || !palette.isReady()) {
            listener.onFailure("bad_palette");
            return;
        }

        String url = apiUrl;
        if (url == null || url.trim().isEmpty())
            url = "https://openrouter.ai/api/v1/chat/completions";
        String modelName = model;
        if (modelName == null || modelName.trim().isEmpty())
            modelName = "deepseek/deepseek-v4-flash";

        String system = buildSystemPrompt(palette);
        String user = buildUserMessage(userPrompt, palette, layerSnapshot, null);
        requestAttempt(apiKey, url, modelName, system, priorSteps, user, palette, 0, listener);
    }

    private static void requestAttempt(String apiKey, String url, String model, String systemPrompt,
            java.util.List<String> priorSteps, String userMessage, PixelPaletteInfo palette, int attempt,
            Listener listener) {
        String body = buildRequestBody(model, systemPrompt, priorSteps, userMessage);

        Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.POST);
        request.setUrl(url.trim());
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Authorization", "Bearer " + apiKey.trim());
        request.setContent(body);

        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                final int code = httpResponse.getStatus().getStatusCode();
                if (code < 200 || code >= 300) {
                    final String errBody = httpResponse.getResultAsString();
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            listener.onFailure("http_" + code + "\n" + errBody);
                        }
                    });
                    return;
                }

                java.io.InputStream is = httpResponse.getResultAsStream();
                if (is == null) {
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            listener.onFailure("empty_response");
                        }
                    });
                    return;
                }

                StringBuilder accumulated = new StringBuilder();
                try {
                    java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(is, "UTF-8"));
                    String line;
                    long lastUpdate = System.currentTimeMillis();

                    while ((line = reader.readLine()) != null) {
                        line = line.trim();
                        if (line.startsWith("data: ") && !line.equals("data: [DONE]")) {
                            String jsonStr = line.substring(6);
                            String chunk = extractStreamingContent(jsonStr);
                            if (chunk != null) {
                                accumulated.append(chunk);

                                if (System.currentTimeMillis() - lastUpdate > 66) {
                                    lastUpdate = System.currentTimeMillis();
                                    final String currentText = accumulated.toString();
                                    Gdx.app.postRunnable(new Runnable() {
                                        @Override
                                        public void run() {
                                            String block = LeanPixelCodec.extractPixelBlock(currentText);
                                            int[][] partialGrid = LeanPixelCodec.decodePixelBlock(block, palette.canvasWidth, palette.canvasHeight);
                                            boolean fullReplace = LeanPixelCodec.wantsFullReplace(block);
                                            listener.onPartial(partialGrid, fullReplace);
                                        }
                                    });
                                }
                            }
                        }
                    }
                    reader.close();

                    final String finalText = accumulated.toString();
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            handleFinalResponse(finalText, apiKey, url, model, systemPrompt, priorSteps, userMessage, palette, attempt, listener);
                        }
                    });
                } catch (final Exception e) {
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            listener.onFailure("stream_error");
                        }
                    });
                }
            }

            @Override
            public void failed(Throwable t) {
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFailure("network");
                    }
                });
            }

            @Override
            public void cancelled() {
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFailure("cancelled");
                    }
                });
            }
        });
    }

    private static void handleFinalResponse(String content, String apiKey, String url, String model, String systemPrompt,
            java.util.List<String> priorSteps, String userMessage, PixelPaletteInfo palette, int attempt,
            Listener listener) {
        if (content == null || content.trim().isEmpty()) {
            listener.onFailure("empty_response");
            return;
        }
        try {
            String block = LeanPixelCodec.extractPixelBlock(content);
            LeanPixelCodec.Validation blockVal = LeanPixelCodec.validateBlock(block,
                    palette.canvasWidth, palette.canvasHeight, palette.slotCount);
            int[][] grid = LeanPixelCodec.decodePixelBlock(block, palette.canvasWidth,
                    palette.canvasHeight);
            LeanPixelCodec.Validation gridVal = LeanPixelCodec.validateAndNormalize(grid,
                    palette.canvasWidth, palette.canvasHeight, palette.slotCount);
            if ((!blockVal.ok || !gridVal.ok) && attempt + 1 < MAX_ATTEMPTS) {
                String fix = userMessage + "\n\nYour last PIXEL block was invalid: "
                        + joinErrors(blockVal, gridVal)
                        + "\nOutput a corrected PIXEL/END. Rules: F0 first, then R0 through R"
                        + (palette.canvasHeight - 1) + ", each row runs sum to "
                        + palette.canvasWidth + ". Slots 0-" + palette.slotCount + " only.";
                requestAttempt(apiKey, url, model, systemPrompt, priorSteps, fix, palette,
                        attempt + 1, listener);
                return;
            }
            if (!gridVal.ok && gridVal.errors.contains("no pixels")) {
                listener.onFailure("empty_sprite");
                return;
            }
            boolean fullReplace = LeanPixelCodec.wantsFullReplace(block);
            listener.onSuccess(grid, fullReplace);
        } catch (Exception e) {
            listener.onFailure("parse_error");
        }
    }

    private static String joinErrors(LeanPixelCodec.Validation a, LeanPixelCodec.Validation b) {
        StringBuilder sb = new StringBuilder();
        if (!a.ok && a.errors != null && !a.errors.isEmpty())
            sb.append(a.errors);
        if (!b.ok && b.errors != null && !b.errors.isEmpty()) {
            if (sb.length() > 0)
                sb.append(' ');
            sb.append(b.errors);
        }
        return sb.length() > 0 ? sb.toString() : "format error";
    }

    static String buildSystemPrompt(PixelPaletteInfo palette) {
        int w = palette.canvasWidth;
        int h = palette.canvasHeight;
        StringBuilder sb = new StringBuilder();
        sb.append("You draw pixel art sprites. Output ONLY a PIXEL block — no markdown, no explanation.\n\n");
        sb.append("STRICT RULES:\n");
        sb.append("1. Start with F0 (transparent fill)\n");
        sb.append("2. Then exactly ").append(h).append(" row lines: R0, R1, ... R").append(h - 1).append('\n');
        sb.append("3. Each R row run lengths MUST sum to ").append(w).append('\n');
        sb.append("4. Use ONLY slot numbers 0-").append(palette.slotCount).append(" (0=transparent)\n");
        sb.append("5. Center the sprite; keep symmetry when appropriate\n");
        sb.append("6. Pick slots by palette color meaning (e.g. dark slot for outline, light for highlight)\n\n");
        sb.append("Format:\nPIXEL\nF0\nR0,<slot>*<run>,...\n...\nR").append(h - 1).append(",...\nEND\n\n");
        sb.append("Canvas ").append(w).append('x').append(h).append('\n');
        sb.append(palette.formatForPrompt());
        sb.append("\nMatch slot colors to subject (metal=silver slot, wood=brown slot, etc.).\n");
        sb.append("On refine: output FULL F0 + all R rows again.\n");
        return sb.toString();
    }

    static String buildUserMessage(String userPrompt, PixelPaletteInfo palette, String snapshot,
            String validationHint) {
        StringBuilder sb = new StringBuilder();
        if (validationHint != null && !validationHint.isEmpty())
            sb.append(validationHint).append("\n\n");
        if (snapshot != null && !snapshot.isEmpty() && !snapshot.startsWith("EMPTY")) {
            sb.append("CURRENT (active layer, palette slots)\n");
            sb.append(snapshot);
            if (!snapshot.endsWith("\n"))
                sb.append('\n');
            sb.append("END\n\n");
            sb.append("Refine: output F0 plus ALL R0..R").append(palette.canvasHeight - 1).append(" rows.\n\n");
        }
        sb.append("Request: ");
        if (userPrompt == null || userPrompt.trim().isEmpty())
            sb.append("small centered game sprite icon.");
        else
            sb.append(userPrompt.trim());
        sb.append("\n\nPIXEL/END required. F0 then R0-R").append(palette.canvasHeight - 1);
        sb.append(", each row sums to ").append(palette.canvasWidth).append('.');
        return sb.toString();
    }

    static String buildRequestBody(String model, String systemPrompt, java.util.List<String> priorSteps,
            String finalUserMessage) {
        java.util.List<Message> msgs = new java.util.ArrayList<>();
        msgs.add(msg("system", systemPrompt));
        if (priorSteps != null && !priorSteps.isEmpty()) {
            int start = Math.max(0, priorSteps.size() - MAX_PRIOR_TURNS);
            for (int i = start; i < priorSteps.size(); i++) {
                msgs.add(msg("user", priorSteps.get(i)));
                msgs.add(msg("assistant", APPLIED_ACK));
            }
        }
        msgs.add(msg("user", finalUserMessage));
        ChatRequest req = new ChatRequest();
        req.model = model;
        req.temperature = 0.0f;
        req.max_tokens = 512;
        req.stream = true;
        req.messages = msgs.toArray(new Message[0]);
        JSON.setOutputType(JsonWriter.OutputType.json);
        return JSON.toJson(req);
    }

    static String extractAssistantContent(String responseBody) {
        JSON.setIgnoreUnknownFields(true);
        ChatResponse response = JSON.fromJson(ChatResponse.class, responseBody);
        if (response.choices != null && response.choices.length > 0 && response.choices[0].message != null)
            return response.choices[0].message.content;
        return responseBody;
    }

    static String extractStreamingContent(String jsonDelta) {
        JSON.setIgnoreUnknownFields(true);
        try {
            ChatResponse response = JSON.fromJson(ChatResponse.class, jsonDelta);
            if (response.choices != null && response.choices.length > 0 && response.choices[0].delta != null)
                return response.choices[0].delta.content;
        } catch(Exception ignored) {}
        return null;
    }

    private static Message msg(String role, String content) {
        Message m = new Message();
        m.role = role;
        m.content = content;
        return m;
    }

    private static class ChatRequest {
        public String model;
        public float temperature;
        public int max_tokens;
        public boolean stream;
        public Message[] messages;
    }

    private static class Message {
        public String role;
        public String content;
    }

    private static class ChatResponse {
        public Choice[] choices;
    }

    private static class Choice {
        public Message message;
        public Message delta;
    }
}
