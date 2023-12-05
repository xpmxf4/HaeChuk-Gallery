package HailYoungHan.Board.repository.post;

import HailYoungHan.Board.dto.post.query.PostDbDTO;
import HailYoungHan.Board.dto.post.query.QPostDbDTO;
import HailYoungHan.Board.repository.post.PostCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static HailYoungHan.Board.entity.QMember.member;
import static HailYoungHan.Board.entity.QPost.*;

public class PostCustomRepositoryImpl implements PostCustomRepository {

    private final JPAQueryFactory queryFactory;

    public PostCustomRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<PostDbDTO> findPostsByMemberId(Long memberId,Integer offset, Integer limit) {

        return queryFactory
                .select(new QPostDbDTO(
                        post.id,
                        post.title,
                        post.content,
                        post.member.name,
                        post.isDeleted
                        , post.reg_date
                ))
                .from(post)
                .where(post.member.id.eq(memberId))
                .orderBy(post.reg_date.desc())
                .offset(offset)
                .limit(limit)
                .fetch();
    }

    @Override
    public List<PostDbDTO> findDeletedPostsByMemberId(Long memberId,Integer offset, Integer limit) {
        return queryFactory
                .select(new QPostDbDTO(
                        post.id,
                        post.title,
                        post.content,
                        post.member.name,
                        post.isDeleted,
                        post.reg_date
                ))
                .from(post)
                .where(post.member.id.eq(memberId).and(post.isDeleted.eq(true)))
                .orderBy(post.reg_date.desc())
                .offset(offset)
                .limit(limit)
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
                        , post.reg_date
                ))
                .from(post)
                .where(post.id.eq(id))
                .fetchOne();
    }

    @Override
    public List<PostDbDTO> findAllDTOs(Integer offset, Integer limit) {

        return queryFactory
                .select(new QPostDbDTO(
                        post.id,
                        post.title,
                        post.content,
                        post.member.name,
                        post.isDeleted,
                        post.reg_date
                ))
                .from(post)
                .leftJoin(post.member)
                .orderBy(post.reg_date.desc())
                .offset(offset)
                .limit(limit)
                .fetch();
    }

    public List<PostDbDTO> findDTOsByKeyword(String keyword, Integer offset, Integer limit) {
        return queryFactory
                .select(new QPostDbDTO(
                        post.id,
                        post.title,
                        post.content,
                        post.member.name,
                        post.isDeleted,
                        post.reg_date
                ))
                .from(post)
                .leftJoin(post.member, member) // 여기서 post.member와 member 엔티티를 연결
                .where(post.title.contains(keyword)
                        .or(post.content.contains(keyword)))
                .orderBy(post.reg_date.desc())
                .offset(offset)
                .limit(limit)
                .fetch();
    }
}
