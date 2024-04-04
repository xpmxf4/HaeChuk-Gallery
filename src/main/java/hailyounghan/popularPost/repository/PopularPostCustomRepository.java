package hailyounghan.popularPost.repository;


import hailyounghan.popularPost.dto.query.PopularPostDTO;

import java.util.List;

public interface PopularPostCustomRepository {

    List<PopularPostDTO> findAllDTOs();
}