package HailYoungHan.Board.dto.comment;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
public class CommentResponseDTO {

    @NotNull
    private List<CommentDTO> comments;

    public CommentResponseDTO(List<CommentDTO> comments) {
        this.comments = comments;
    }
}
