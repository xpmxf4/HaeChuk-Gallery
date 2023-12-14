package HailYoungHan.Board.dto.popularPost.response;

import HailYoungHan.Board.dto.popularPost.query.PopularPostDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class PopularPostResponseDTO {

    private List<PopularPostDTO> popularPosts;

    public PopularPostResponseDTO(List<PopularPostDTO> popularPosts) {
        this.popularPosts = popularPosts;
    }
}
