package HailYoungHan.Board.repository;

import HailYoungHan.Board.dto.PostDTO;
import HailYoungHan.Board.dto.PostUpdateDTO;

import java.util.List;

public interface PostCustomRepository {

    Long updatePost(Long id, PostUpdateDTO updateDTO);

    List<PostDTO> getPostsFromMemberId(Long id);

    List<PostDTO> getDeletedPostsFromMemberId(Long id);

    PostDTO findDTObyId(Long id);
}
