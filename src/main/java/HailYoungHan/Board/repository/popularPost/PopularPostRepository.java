package HailYoungHan.Board.repository.popularPost;

import HailYoungHan.Board.entity.PopularPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PopularPostRepository extends JpaRepository<PopularPost, Long> {

}
