package hailyounghan.board.service;


import hailyounghan.board.dto.post.query.PostDbDTO;
import hailyounghan.board.dto.post.request.PostRegiDTO;
import hailyounghan.board.dto.post.request.PostUpdateDTO;
import hailyounghan.board.dto.post.response.PostResponseDTO;
import hailyounghan.board.entity.Member;
import hailyounghan.board.entity.Post;
import hailyounghan.board.exception.CustomException;
import hailyounghan.board.repository.member.MemberRepository;
import hailyounghan.board.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static hailyounghan.board.exception.ErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    // 게시글 등록
    @Transactional
    public void registerPost(PostRegiDTO postRegiDTO) {
        Long memberId = postRegiDTO.getMemberId();

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND_BY_ID, memberId));

        Post post = Post.mapFromRegiDto(member, postRegiDTO);

        postRepository.save(post);
    }

    // 게시글 수정
    @Transactional
    public void updatePost(Long postId, PostUpdateDTO postUpdateDTO) {
        // DB 에 해당 게시물 존재하는 지 확인
        Post post = postRepository
                .findById(postId)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND_BY_ID, postId));

        // post 의 field 들 업데이트
        post.updateFieldsFromUpdateDto(postUpdateDTO);
    }

    // 특정 게시물 조회
    public PostDbDTO getSinglePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND_BY_ID, postId));

        return post.mapToDbDTO();
    }

    // 전체 게시물 조회
    public PostResponseDTO getAllPosts(Integer offset, Integer limit) {
        List<PostDbDTO> allDTOs = postRepository.findAllDTOs(offset, limit);
        return new PostResponseDTO(allDTOs);
    }

    // 특정 사용자의 게시물 조회
    public PostResponseDTO getPostsByMemberId(Long memberId, Integer offset, Integer limit) {
        if (!memberRepository.existsById(memberId))
            throw new CustomException(MEMBER_NOT_FOUND_BY_ID, memberId);

        List<PostDbDTO> memberPosts = postRepository.findPostsByMemberId(memberId, offset, limit);
        return new PostResponseDTO(memberPosts);
    }

    public PostResponseDTO searchByKeyword(String keyword, Integer offset, Integer limit) {
        List<PostDbDTO> searchResult = postRepository.findDTOsByKeyword(keyword, offset, limit);
        return new PostResponseDTO(searchResult);
    }

    // 특정 사용자의 삭제된 게시물 조회
    public PostResponseDTO findDeletedPostsByMemberId(Long memberId, Integer offset, Integer limit) {
        if (!memberRepository.existsById(memberId))
            throw new CustomException(MEMBER_NOT_FOUND_BY_ID, memberId);

        List<PostDbDTO> memberDeletedPosts = postRepository.findDeletedPostsByMemberId(memberId, offset, limit);
        return new PostResponseDTO(memberDeletedPosts);
    }

    // 게시물 삭제
    public void deletePost(Long postId) {
        if (!postRepository.existsById(postId))
            throw new CustomException(POST_NOT_FOUND_BY_ID, postId);

        postRepository.deletePostById(postId);
    }
}

