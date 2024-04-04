package hailyounghan.post.repository;

import hailyounghan.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>,
//        HibernateRepository<Post>,
        PostCustomRepository {

    void deletePostById(Long postId);
}
