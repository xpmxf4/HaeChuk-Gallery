package HailYoungHan.Board.dto.comment.request;

import HailYoungHan.Board.entity.Comment;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Getter
public class CommentUpdateDTO {

    @NotBlank
    @Size(min = 1, max = 500)
    private String content;

    @NotNull
    @Builder.Default
    private Boolean isDeleted = false;

    public Comment mapToEntity(Long commentId) {
        return Comment.builder()
                .id(commentId)
                .content(content)
                .isDeleted(isDeleted)
                .build();
    }
}
