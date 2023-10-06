package HailYoungHan.Board.repository;

import HailYoungHan.Board.dto.PostDTO;
import HailYoungHan.Board.dto.PostUpdateDTO;

import java.util.List;

public interface PostCustomRepository {

    Long updatePost(Long id, PostUpdateDTO updateDTO);

    List<PostDTO> findPostsByMemberId(Long memberId);

    List<PostDTO> findDeletedPostsByMemberId(Long memberId);

    PostDTO findDTObyId(Long id);

    List<PostDTO> findAllDTOs();

    Long deletePost(Long postId);
}
