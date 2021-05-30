package HoustoN.Sarafan.repo;

import HoustoN.Sarafan.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Long> {

}
