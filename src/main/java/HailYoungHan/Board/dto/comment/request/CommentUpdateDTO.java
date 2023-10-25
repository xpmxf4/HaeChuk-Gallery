package HailYoungHan.Board.dto.comment.request;

import HailYoungHan.Board.entity.Comment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CommentUpdateDTO {

    @NotBlank
    @Size(min = 1, max = 500)
    private String content;

    @NotNull
    private Boolean isDeleted;

    public Comment mapToEntity(Long commentId) {
        return Comment.builder()
                .id(commentId)
                .content(content)
                .isDeleted(isDeleted)
                .build();
    }
}
