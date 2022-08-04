package engine.business;

import engine.business.exception.QuizNotFoundException;
import engine.persistance.QuizCompletionRepository;
import engine.persistance.QuizRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class QuizService {

    private final QuizRepository repository;
    private final QuizCompletionRepository completionRepository;

    public QuizService(QuizRepository repository, QuizCompletionRepository completionRepository) {
        this.repository = repository;
        this.completionRepository = completionRepository;
    }

    public Quiz addQuiz(Quiz quiz) {
        return repository.save(quiz);
    }

    public Quiz getQuiz(int id) {
        return repository.findById(id).orElseThrow(QuizNotFoundException::new);
    }

    public Page<Quiz> getAllQuizzes(int page) {
        return repository.findAll(Pageable.ofSize(10).withPage(page));
    }

    public void removeQuiz(Quiz quiz) {
        repository.delete(quiz);
    }

    public void addSolved(String name, int id) {
        completionRepository.save(new QuizCompletion(Timestamp.valueOf(LocalDateTime.now()), id, name));
    }

    public Page<QuizCompletion> getSolved(int page, String email) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("completedAt").descending());
        return completionRepository.findAllBySolverEmail(pageable, email);
    }
}
