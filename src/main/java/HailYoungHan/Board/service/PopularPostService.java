package HailYoungHan.Board.service;

import HailYoungHan.Board.dto.popularPost.query.PopularPostDTO;
import HailYoungHan.Board.dto.popularPost.response.PopularPostResponseDTO;
import HailYoungHan.Board.entity.Member;
import HailYoungHan.Board.entity.PopularPost;
import HailYoungHan.Board.entity.QMember;
import HailYoungHan.Board.entity.QPost;
import HailYoungHan.Board.repository.popularPost.PopularPostRepository;
import HailYoungHan.Board.repository.post.PostRepository;
import com.querydsl.core.Tuple;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static HailYoungHan.Board.entity.QMember.member;
import static HailYoungHan.Board.entity.QPost.post;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PopularPostService {

    private final PopularPostRepository popularPostRepository;
    private final PostRepository postRepository;

    public PopularPostResponseDTO getPopularPosts() {
        List<PopularPostDTO> allDTOs = popularPostRepository.findAllDTOs();
        return new PopularPostResponseDTO(allDTOs);
    }

    @Transactional
    @Scheduled(cron = "0 0 * * * *")
    public void updatePopularPosts() {
        // 새로운 상위 10개 게시물 추출
        List<Tuple> top10Posts = postRepository.findTop10Posts();

        // 기존의 data 삭제
        popularPostRepository.deleteAllInBatch();

        // 변환 후 새로운 data 삽입
        List<PopularPost> popularPosts = convertToPopularPost(top10Posts);
        popularPostRepository.saveAll(popularPosts);
    }

    private List<PopularPost> convertToPopularPost(List<Tuple> top10Posts) {
        List<PopularPost> popularPosts = new ArrayList<>();

        for (Tuple tuple : top10Posts) {
            Long postId = tuple.get(post.id);
            String title = tuple.get(post.title);
            Member member = tuple.get(post.member);

            PopularPost popularPost = PopularPost.builder()
                    .id(postId)
                    .title(title)
                    .member(member)
                    .build();

            popularPosts.add(popularPost);
        }

        return popularPosts;
    }
}
