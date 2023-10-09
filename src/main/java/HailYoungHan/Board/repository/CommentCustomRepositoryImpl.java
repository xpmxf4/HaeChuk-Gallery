package HailYoungHan.Board.repository;

import HailYoungHan.Board.entity.Comment;
import HailYoungHan.Board.entity.Member;
import HailYoungHan.Board.entity.Post;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

import static HailYoungHan.Board.entity.QComment.comment;

public class CommentCustomRepositoryImpl implements CommentCustomRepository {

    private final JPAQueryFactory queryFactory;

    public CommentCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public void insertCommentDTO(String content, Member author, Post commentedPost, Comment parentComment) {

        queryFactory
                .insert(comment)
                .columns(comment.content, comment.member, comment.post, comment.parent)
                .values(content, author, commentedPost, parentComment)
                .execute();
    }
}
