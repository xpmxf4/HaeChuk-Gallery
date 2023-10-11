package HailYoungHan.Board.dto;

import java.util.List;

public class CommentResponseDTO {

    private List<CommentDTO> comments;

    public CommentResponseDTO(List<CommentDTO> comments) {
        this.comments = comments;
    }
}
