package br.com.daniel.budgetai.ai;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/assistant")
public class AssistantController {
    private final AssistantService service;
    public AssistantController(AssistantService service) { this.service = service; }

    @PostMapping("/text")
    public AssistantResponse text(@Valid @RequestBody AssistantRequest request) {
        return new AssistantResponse(service.answer(request.message()));
    }

    @PostMapping(value = "/voice", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "audio/mpeg")
    public byte[] voice(@RequestPart("audio") MultipartFile audio) throws Exception {
        return service.answerVoice(audio);
    }

    public record AssistantRequest(@NotBlank String message) { }
    public record AssistantResponse(String response) { }
}
