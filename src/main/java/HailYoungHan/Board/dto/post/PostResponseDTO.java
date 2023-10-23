package HailYoungHan.Board.dto.post;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
public class PostResponseDTO {

    @NotNull
    private List<PostDTO> posts;

    public PostResponseDTO(List<PostDTO> posts) {
        this.posts = posts;
    }

}
