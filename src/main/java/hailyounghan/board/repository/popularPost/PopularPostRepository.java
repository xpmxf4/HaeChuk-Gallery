package hailyounghan.board.repository.popularPost;

import hailyounghan.board.entity.PopularPost;
import hailyounghan.board.repository.popularPost.PopularPostCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PopularPostRepository extends JpaRepository<PopularPost, Long>, PopularPostCustomRepository {

}
