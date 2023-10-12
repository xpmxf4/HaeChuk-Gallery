package HailYoungHan.Board.repository;

import HailYoungHan.Board.dto.comment.CommentDTO;
import HailYoungHan.Board.dto.comment.QCommentDTO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;

import javax.persistence.EntityManager;

import java.util.List;

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

    @Override
    public CommentDTO findDTOById(Long commentId) {

        return queryFactory
                .select(new QCommentDTO(
                        comment.id,
                        comment.content,
                        comment.isDeleted
                ))
                .from(comment)
                .where(comment.id.eq(commentId))
                .fetchOne();

    }

    @Override
    public List<CommentDTO> findAllDTOs() {

        return queryFactory
                .select(new QCommentDTO(
                        comment.id,
                        comment.content,
                        comment.isDeleted
                ))
                .from(comment)
                .fetch();
    }

    @Override
    public List<CommentDTO> findAllDTOsByMemberId(Long memberId, boolean isDeleted) {
        return queryFactory
                .select(new QCommentDTO(
                        comment.id,
                        comment.content,
                        comment.isDeleted
                ))
                .from(comment)
                .where(comment.member.id.eq(memberId).and(comment.isDeleted.eq(isDeleted)))
                .fetch();
    }

    @Override
    public List<CommentDTO> findAllDTOsBYMemberIdAndIsDeleted(Long memberId){

        return queryFactory
                .select(new QCommentDTO(
                        comment.id,
                        comment.content,
                        comment.isDeleted
                ))
                .from(comment)
                .where(comment.member.id.eq(memberId).and(comment.isDeleted.eq(true)))
                .fetch();
    }
}
