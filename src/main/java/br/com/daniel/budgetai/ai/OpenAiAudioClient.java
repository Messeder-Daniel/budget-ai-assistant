package br.com.daniel.budgetai.ai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;

@Component
public class OpenAiAudioClient {
    private final RestClient client;

    public OpenAiAudioClient(RestClient.Builder builder, @Value("${openai.base-url}") String baseUrl,
                             @Value("${openai.api-key}") String apiKey) {
        this.client = builder.baseUrl(baseUrl).defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey).build();
    }

    public String transcribe(MultipartFile audio) throws IOException {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("model", "whisper-1");
        body.add("language", "pt");
        body.add("file", new ByteArrayResource(audio.getBytes()) {
            @Override public String getFilename() { return audio.getOriginalFilename(); }
        });
        Map response = client.post().uri("/v1/audio/transcriptions").contentType(MediaType.MULTIPART_FORM_DATA)
                .body(body).retrieve().body(Map.class);
        return String.valueOf(response.get("text"));
    }

    public byte[] speech(String text) {
        Map<String, String> payload = Map.of("model", "gpt-4o-mini-tts", "voice", "alloy", "input", text);
        return client.post().uri("/v1/audio/speech").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.valueOf("audio/mpeg")).body(payload).retrieve().body(byte[].class);
    }
}
