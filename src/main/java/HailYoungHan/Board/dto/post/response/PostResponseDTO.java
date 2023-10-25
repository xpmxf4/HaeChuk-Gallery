package HailYoungHan.Board.dto.post.response;

import HailYoungHan.Board.dto.post.query.PostDbDTO;
import lombok.Getter;

import java.util.List;

// [ ... ] 대신
// 이를 { posts : [ ... ] } 의 형태로 내보내기 위한 ResponseDTO
@Getter
public class PostResponseDTO {

    private List<PostDbDTO> posts;

    public PostResponseDTO(List<PostDbDTO> posts) {
        this.posts = posts;
    }

}
