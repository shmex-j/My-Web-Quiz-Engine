package engine.persistance;

import engine.business.QuizCompletion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizCompletionRepository extends PagingAndSortingRepository<QuizCompletion, Integer> {
    Page<QuizCompletion> findAllBySolverEmail(Pageable pageable, String solverEmail);
}
