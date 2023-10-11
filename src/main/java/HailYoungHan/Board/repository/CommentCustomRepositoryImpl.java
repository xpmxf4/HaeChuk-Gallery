package HailYoungHan.Board.repository;

import HailYoungHan.Board.dto.CommentDTO;
import HailYoungHan.Board.entity.QComment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;

import javax.persistence.EntityManager;

import static HailYoungHan.Board.entity.QComment.*;


public class CommentCustomRepositoryImpl implements CommentCustomRepository {

    private final JPAQueryFactory queryFactory;

    public CommentCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public void updateCommentDTO(Long commentId, CommentDTO dto) {

        JPAUpdateClause updateClause = queryFactory.update(comment).where(comment.id.eq(commentId));

        // content 변경 체크
        if (dto.getContent() != null)
            updateClause.set(comment.content, dto.getContent());

        // isDeleted 변경 체크
        if (dto.getIsDeleted() != null)
            updateClause.set(comment.isDeleted, dto.getIsDeleted());

        updateClause.execute();
    }
}
