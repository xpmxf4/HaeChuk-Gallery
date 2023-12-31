package hailyounghan.board.repository.comment;

import hailyounghan.board.entity.Comment;
//import io.hypersistence.utils.spring.repository.HibernateRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>,
//        HibernateRepository<Comment>,
        CommentCustomRepository {

}
