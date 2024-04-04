package hailyounghan.popularPost.repository;

import hailyounghan.popularPost.entity.PopularPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PopularPostRepository extends JpaRepository<PopularPost, Long>, PopularPostCustomRepository {

}
