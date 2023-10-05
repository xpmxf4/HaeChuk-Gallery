package HailYoungHan.Board.repository;

import HailYoungHan.Board.dto.PostDTO;
import HailYoungHan.Board.dto.QPostDTO;
import HailYoungHan.Board.entity.QPost;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static HailYoungHan.Board.entity.QPost.*;

public class PostCustomRepositoryImpl implements PostCustomRepository {

    private final JPAQueryFactory queryFactory;

    public PostCustomRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<PostDTO> getPostFromMemberId(Long id) {
        return queryFactory
                .select(new QPostDTO(
                        post.id,
                        post.title,
                        post.content,
                        post.isDeleted
                ))
                .from(post)
                .where(post.member.id.eq(id))
                .fetch();
    }

    @Override
    public List<PostDTO> getDeletedPostsFromMemberId(Long id) {
        return queryFactory
                .select(new QPostDTO(
                        post.id,
                        post.title,
                        post.content,
                        post.isDeleted
                ))
                .from(post)
                .where(post.member.id.eq(id).and(post.isDeleted))
                .fetch();
    }
}
