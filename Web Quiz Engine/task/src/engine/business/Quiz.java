package engine.business;

import engine.presentation.QuizDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String text;
    @ElementCollection
    private List<String> options;
    @ElementCollection
    private Set<String> answer;
    private String author;

    public QuizDto toDto() {
        return new QuizDto(
                id, title, text, options, answer
        );
    }

    public static Quiz fromDto(QuizDto dto, String author) {
        return new Quiz(
                dto.getId(), dto.getTitle(), dto.getText(),
                dto.getOptions(), dto.getAnswer() == null ? Collections.emptySet() : dto.getAnswer(),
                author
        );
    }
}
