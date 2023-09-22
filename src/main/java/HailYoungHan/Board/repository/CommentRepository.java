package HailYoungHan.Board.repository;

import HailYoungHan.Board.entity.Comment;
import HailYoungHan.Board.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByMember(Member member);

    List<Comment> findByMemberAndIsDeleted(Member member, boolean isDeleted);
}
