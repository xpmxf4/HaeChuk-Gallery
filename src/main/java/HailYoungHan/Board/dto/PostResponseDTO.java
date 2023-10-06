package HailYoungHan.Board.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class PostResponseDTO {

    private List<PostDTO> posts;

    public PostResponseDTO(List<PostDTO> posts) {
        this.posts = posts;
    }

}
