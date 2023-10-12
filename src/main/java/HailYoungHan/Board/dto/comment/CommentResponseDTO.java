package HailYoungHan.Board.dto.comment;

import lombok.Getter;

import java.util.List;

@Getter
public class CommentResponseDTO {

    private List<CommentDTO> comments;

    public CommentResponseDTO(List<CommentDTO> comments) {
        this.comments = comments;
    }
}
