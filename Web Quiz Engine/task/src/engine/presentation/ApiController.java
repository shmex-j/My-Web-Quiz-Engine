package engine.presentation;

import engine.business.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestController
public class ApiController {

    private final QuizService quizService;
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    public ApiController(QuizService quizService, UserService userService, PasswordEncoder passwordEncoder) {
        this.quizService = quizService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/api/register")
    public void register(@RequestBody @Valid UserDto dto) {
        userService.addUser(new User(dto.getEmail(), passwordEncoder.encode(dto.getPassword())));
    }

    @GetMapping("/api/quizzes")
    public Page<QuizDto> getAllQuizzes(@RequestParam int page) {
        return new PageImpl<>(
                quizService.getAllQuizzes(page)
                        .stream()
                        .map(Quiz::toDto)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/api/quizzes/{id}")
    public QuizDto getQuiz(@PathVariable int id) {
        return quizService.getQuiz(id).toDto();
    }

    @PostMapping("/api/quizzes")
    public QuizDto postQuiz(@RequestBody @Valid QuizDto dto, Principal principal) {
        var author = principal.getName();
        return quizService.addQuiz(Quiz.fromDto(dto, author)).toDto();
    }

    @PostMapping("/api/quizzes/{id}/solve")
    public AnswerResponse postAnswer(@PathVariable int id, @RequestBody AnswerRequest answer, Principal principal) {
        if (answer.getAnswer().equals(quizService.getQuiz(id).getAnswer())) {
            quizService.addSolved(principal.getName(), id);
            return new AnswerResponse(true, "Congratulations, you're right!");
        } else {
            return new AnswerResponse(false, "Wrong answer! Please, try again.");
        }
    }

    @GetMapping("/api/quizzes/completed")
    public Page<QuizCompletion> getCompletions(@RequestParam int page, Principal principal) {
        return quizService.getSolved(page, principal.getName());
    }

    @DeleteMapping("/api/quizzes/{id}")
    public ResponseEntity<?> deleteQuiz(@PathVariable int id, Principal principal) {
        var quiz = quizService.getQuiz(id);
        if (!quiz.getAuthor().equals(principal.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        quizService.removeQuiz(quiz);
        return ResponseEntity.noContent().build();
    }
}
