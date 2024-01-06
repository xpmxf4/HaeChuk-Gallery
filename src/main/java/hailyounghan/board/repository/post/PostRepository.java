package hailyounghan.board.repository.post;

import hailyounghan.board.entity.Post;
//import io.hypersistence.utils.spring.repository.HibernateRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>,
//        HibernateRepository<Post>,
        PostCustomRepository {

    void deletePostById(Long postId);
}
