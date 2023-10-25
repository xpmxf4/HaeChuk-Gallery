package HailYoungHan.Board.repository.post;

import HailYoungHan.Board.dto.post.query.PostDbDTO;

import java.util.List;

public interface PostCustomRepository {

    List<PostDbDTO> findPostsByMemberId(Long memberId);

    List<PostDbDTO> findDeletedPostsByMemberId(Long memberId);

    PostDbDTO findDTObyId(Long id);

    List<PostDbDTO> findAllDTOs();
}
