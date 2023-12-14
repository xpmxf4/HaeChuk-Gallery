package HailYoungHan.Board.repository.popularPost;

import HailYoungHan.Board.dto.popularPost.query.PopularPostDTO;

import java.util.List;

public interface PopularPostCustomRepository {

    List<PopularPostDTO> findAllDTOs();
}