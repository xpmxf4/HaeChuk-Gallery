package HailYoungHan.Board.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CommentResponseDTO {

    private List<CommentDTO> comments;

    public CommentResponseDTO(List<CommentDTO> comments) {
        this.comments = comments;
    }
}
