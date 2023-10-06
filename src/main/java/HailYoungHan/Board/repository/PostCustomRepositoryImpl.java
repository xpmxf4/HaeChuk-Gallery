package HailYoungHan.Board.repository;

import HailYoungHan.Board.dto.PostDTO;
import HailYoungHan.Board.dto.PostUpdateDTO;
import HailYoungHan.Board.dto.QPostDTO;
import HailYoungHan.Board.entity.QPost;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;

import javax.persistence.EntityManager;
import java.util.List;

import static HailYoungHan.Board.entity.QPost.*;

public class PostCustomRepositoryImpl implements PostCustomRepository {

    private final JPAQueryFactory queryFactory;

    public PostCustomRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<PostDTO> getPostsFromMemberId(Long id) {
        return queryFactory
                .select(new QPostDTO(
                        post.id,
                        post.title,
                        post.content,
                        post.member.name,
                        post.isDeleted
                ))
                .from(post)
                .where(post.member.id.eq(id))
                .fetch();
    }

    @Override
    public Long updatePost(Long postId, PostUpdateDTO updateDTO) {
        JPAUpdateClause updateClause = queryFactory.update(post).where(post.id.eq(postId));

        // title 체크
        if (updateDTO.getTitle() != null && !updateDTO.getTitle().isEmpty()) {
            updateClause.set(post.title, updateDTO.getTitle());
        }

        // content 체크
        if (updateDTO.getContent() != null && !updateDTO.getContent().isEmpty()) {
            updateClause.set(post.content, updateDTO.getContent());
        }
        updateClause.execute();
        return postId;
    }

    @Override
    public List<PostDTO> getDeletedPostsFromMemberId(Long id) {
        return queryFactory
                .select(new QPostDTO(
                        post.id,
                        post.title,
                        post.content,
                        post.member.name,
                        post.isDeleted
                ))
                .from(post)
                .where(post.member.id.eq(id).and(post.isDeleted))
                .fetch();
    }

    @Override
    public PostDTO findDTObyId(Long id) {

        return queryFactory
                .select(new QPostDTO(
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
    public List<PostDTO> findAllDTOs() {

        return queryFactory
                .select(new QPostDTO(
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
