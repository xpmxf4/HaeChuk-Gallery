package HailYoungHan.Board.repository;

import HailYoungHan.Board.entity.Member;
import HailYoungHan.Board.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByMember(Member member);

    List<Post> findByMemberAndIsDeleted(Member member, boolean isDeleted);
}
