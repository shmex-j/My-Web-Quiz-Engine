package engine.presentation;

import lombok.Getter;

@Getter
public class AnswerResponse {
    private final boolean success;
    private final String feedback;

    public AnswerResponse(boolean success, String feedback) {
        this.success = success;
        this.feedback = feedback;
    }
}
