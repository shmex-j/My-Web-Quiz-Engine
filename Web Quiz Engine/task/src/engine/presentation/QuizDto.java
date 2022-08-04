package engine.presentation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int id;
    @NotBlank(message = "Title of quiz should not be blank")
    private String title;
    @NotBlank(message = "Text of quiz should not be blank")
    private String text;
    @Size(min = 2, message = "There are must be at least 2 options")
    @NotEmpty
    private List<String> options;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<String> answer;
}
