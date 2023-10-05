package HailYoungHan.Board.service;


import HailYoungHan.Board.dto.PostRegiDTO;
import HailYoungHan.Board.entity.Member;
import HailYoungHan.Board.entity.Post;
import HailYoungHan.Board.repository.MemberRepository;
import HailYoungHan.Board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    // 게시글 등록
    @Transactional
    public Post registerPost(PostRegiDTO postRegiDTO) {
        Long memberId = postRegiDTO.getMemberId();
        String title = postRegiDTO.getTitle();
        String content = postRegiDTO.getContent();

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("No such member"));
        Post post = new Post(title, content, member);

        return postRepository.save(post);
    }
    // 게시글 수정
    // 특정 게시물 조회
    // 전체 게시물 조회
    // 특정 사용자의 게시물 조회
    // 특정 사용자의 삭제된 게시물 조회
    // 게시물 삭제
}

