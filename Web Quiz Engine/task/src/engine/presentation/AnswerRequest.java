package engine.presentation;

import lombok.Data;

import java.util.Set;

@Data
public class AnswerRequest {
    private Set<String> answer;
}
