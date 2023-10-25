package HailYoungHan.Board.service;


import HailYoungHan.Board.dto.post.query.PostDbDTO;
import HailYoungHan.Board.dto.post.request.PostRegiDTO;
import HailYoungHan.Board.dto.post.request.PostUpdateDTO;
import HailYoungHan.Board.dto.post.response.PostResponseDTO;
import HailYoungHan.Board.entity.Member;
import HailYoungHan.Board.entity.Post;
import HailYoungHan.Board.exception.member.MemberNotFoundException;
import HailYoungHan.Board.exception.post.PostNotFoundException;
import HailYoungHan.Board.repository.member.MemberRepository;
import HailYoungHan.Board.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    // 게시글 등록
    @Transactional
    public Post registerPost(PostRegiDTO postRegiDTO) {
        Member member = memberRepository.findById(postRegiDTO.getMemberId())
                .orElseThrow(() -> new MemberNotFoundException(postRegiDTO.getMemberId()));

        Post post = Post.mapFromRegiDto(member, postRegiDTO);

        return postRepository.save(post);
    }

    // 게시글 수정
    @Transactional
    public void updatePost(Long postId, PostUpdateDTO postUpdateDTO) {
        // DB 에 해당 게시물 존재하는 지 확인
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        // postUpdateDTO ---(map)---> Post(Entity) 로 mapping
        post.mapFromUpdateDto(postUpdateDTO);

        // DB 에 save
        postRepository.save(post);
    }

    // 특정 게시물 조회
    public PostDbDTO getSinglePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        return post.mapToDbDTO();
    }

    // 전체 게시물 조회
    public PostResponseDTO getAllPosts() {
        List<PostDbDTO> allDTOs = postRepository.findAllDTOs();
        return new PostResponseDTO(allDTOs);
    }

    // 특정 사용자의 게시물 조회
    public PostResponseDTO getPostsByMemberId(Long memberId) {
        if (!memberRepository.existsById(memberId))
            throw new MemberNotFoundException("No such memberId : " + memberId);

        List<PostDbDTO> memberPosts = postRepository.findPostsByMemberId(memberId);
        return new PostResponseDTO(memberPosts);
    }

    // 특정 사용자의 삭제된 게시물 조회
    public PostResponseDTO findDeletedPostsByMemberId(Long memberId) {
        if (!memberRepository.existsById(memberId))
            throw new MemberNotFoundException("No Such memberId : " + memberId);

        List<PostDbDTO> memberDeletedPosts = postRepository.findDeletedPostsByMemberId(memberId);
        return new PostResponseDTO(memberDeletedPosts);
    }

    // 게시물 삭제
    public void deletePost(Long postId) {
        if (!postRepository.existsById(postId))
            throw new PostNotFoundException(postId);

        postRepository.deletePostById(postId);
    }
}

