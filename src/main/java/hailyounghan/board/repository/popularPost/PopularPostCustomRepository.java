package hailyounghan.board.repository.popularPost;

import hailyounghan.board.dto.popularPost.query.PopularPostDTO;

import java.util.List;

public interface PopularPostCustomRepository {

    List<PopularPostDTO> findAllDTOs();
}