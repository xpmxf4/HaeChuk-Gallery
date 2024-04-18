package hailyounghan.post.repository;

import com.querydsl.core.Tuple;
import hailyounghan.post.dto.query.PostDbDTO;

import java.util.List;

public interface PostCustomRepository {

    List<PostDbDTO> findPostsByMemberId(Long memberId, Integer offset, Integer limit);

    List<PostDbDTO> findDeletedPostsByMemberId(Long memberId, Integer offset, Integer limit);

    PostDbDTO findDTObyId(Long id);

    List<PostDbDTO> findAllDTOs(Integer offset, Integer limit);

    List<PostDbDTO> findDTOsByKeyword(String keyword, Integer offset, Integer limit);

    List<Tuple> findTop10Posts();
}