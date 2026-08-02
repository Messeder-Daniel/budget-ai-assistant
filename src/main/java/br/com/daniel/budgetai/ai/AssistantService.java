package br.com.daniel.budgetai.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
public class AssistantService {
    private final ChatClient chatClient;
    private final OpenAiAudioClient audioClient;
    private final boolean aiConfigured;

    public AssistantService(ChatClient budgetChatClient, OpenAiAudioClient audioClient,
                            @Value("${openai.api-key}") String apiKey) {
        this.chatClient = budgetChatClient;
        this.audioClient = audioClient;
        this.aiConfigured = !"not-configured".equals(apiKey);
    }

    public String answer(String message) {
        requireAiKey();
        return chatClient.prompt().user(message).call().content();
    }

    public byte[] answerVoice(MultipartFile audio) throws IOException {
        requireAiKey();
        return audioClient.speech(answer(audioClient.transcribe(audio)));
    }

    private void requireAiKey() {
        if (!aiConfigured) {
            throw new IllegalStateException("Configure a variável OPENAI_API_KEY para usar os recursos de IA.");
        }
    }
}
