package hailyounghan.comment.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hailyounghan.comment.dto.query.CommentDbDTO;
import hailyounghan.comment.dto.query.QCommentDbDTO;

import javax.persistence.EntityManager;
import java.util.List;

import static hailyounghan.comment.entity.QComment.comment;


public class CommentCustomRepositoryImpl implements CommentCustomRepository {

    private final JPAQueryFactory queryFactory;

    public CommentCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public CommentDbDTO findDTOById(Long commentId) {

        return queryFactory
                .select(new QCommentDbDTO(
                        comment.content,
                        comment.isDeleted
                ))
                .from(comment)
                .where(comment.id.eq(commentId))
                .fetchOne();

    }

    @Override
    public List<CommentDbDTO> findAllDTOs() {

        return queryFactory
                .select(new QCommentDbDTO(
                        comment.content,
                        comment.isDeleted
                ))
                .from(comment)
                .fetch();
    }

    @Override
    public List<CommentDbDTO> findAllDTOsByMemberId(Long memberId) {
        return queryFactory
                .select(new QCommentDbDTO(
                        comment.content,
                        comment.isDeleted
                ))
                .from(comment)
                .where(comment.member.id.eq(memberId))
                .fetch();
    }

    @Override
    public List<CommentDbDTO> findAllDeletedDTOsByMemberId(Long memberId, boolean isDeleted) {
        return queryFactory
                .select(new QCommentDbDTO(
                        comment.content,
                        comment.isDeleted
                ))
                .from(comment)
                .where(comment.member.id.eq(memberId).and(comment.isDeleted.eq(isDeleted)))
                .fetch();
    }

}
