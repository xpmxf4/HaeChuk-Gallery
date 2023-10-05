package HailYoungHan.Board.repository;

import HailYoungHan.Board.dto.PostDTO;

import java.util.List;

public interface PostCustomRepository {

    List<PostDTO> getPostFromMemberId(Long id);

    List<PostDTO> getDeletedPostsFromMemberId(Long id);
}
