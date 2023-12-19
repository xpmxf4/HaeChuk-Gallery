package HailYoungHan.Board.repository.post;

import HailYoungHan.Board.dto.popularPost.query.PopularPostDTO;
import HailYoungHan.Board.dto.post.query.PostDbDTO;
import com.querydsl.core.Tuple;

import java.util.List;

public interface PostCustomRepository {

    List<PostDbDTO> findPostsByMemberId(Long memberId, Integer offset, Integer limit);

    List<PostDbDTO> findDeletedPostsByMemberId(Long memberId, Integer offset, Integer limit);

    PostDbDTO findDTObyId(Long id);

    List<PostDbDTO> findAllDTOs(Integer offset, Integer limit);

    List<PostDbDTO> findDTOsByKeyword(String keyword, Integer offset, Integer limit);

    public List<Tuple> findTop10Posts();
}