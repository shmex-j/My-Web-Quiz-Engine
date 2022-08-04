package engine.business;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
public class QuizCompletion {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @JsonProperty("completedAt")
    private Timestamp completedAt;
    @JsonProperty("id")
    private int quizId;
    @JsonIgnore
    private String solverEmail;

    public QuizCompletion(Timestamp completedAt, int quizId, String solverEmail) {
        this.completedAt = completedAt;
        this.quizId = quizId;
        this.solverEmail = solverEmail;
    }
}
