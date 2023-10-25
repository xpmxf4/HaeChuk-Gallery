package HailYoungHan.Board.repository.post;

import HailYoungHan.Board.dto.post.query.PostDbDTO;
import HailYoungHan.Board.dto.post.query.QPostDbDTO;
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
    public List<PostDbDTO> findPostsByMemberId(Long memberId) {

        return queryFactory
                .select(new QPostDbDTO(
                        post.id,
                        post.title,
                        post.content,
                        post.member.name,
                        post.isDeleted
                ))
                .from(post)
                .where(post.member.id.eq(memberId))
                .fetch();
    }

    @Override
    public List<PostDbDTO> findDeletedPostsByMemberId(Long memberId) {
        return queryFactory
                .select(new QPostDbDTO(
                        post.id,
                        post.title,
                        post.content,
                        post.member.name,
                        post.isDeleted
                ))
                .from(post)
                .where(post.member.id.eq(memberId).and(post.isDeleted))
                .fetch();
    }

    @Override
    public PostDbDTO findDTObyId(Long id) {

        return queryFactory
                .select(new QPostDbDTO(
                        post.id,
                        post.title,
                        post.content,
                        post.member.name,
                        post.isDeleted
                ))
                .from(post)
                .where(post.id.eq(id))
                .fetchOne();
    }

    @Override
    public List<PostDbDTO> findAllDTOs() {

        return queryFactory
                .select(new QPostDbDTO(
                        post.id,
                        post.title,
                        post.content,
                        post.member.name,
                        post.isDeleted
                ))
                .from(post)
                .fetch();
    }
}
