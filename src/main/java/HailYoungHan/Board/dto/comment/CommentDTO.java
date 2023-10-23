package HailYoungHan.Board.dto.comment;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
public class CommentDTO {

    @NotBlank
    @Size(min = 1, max = 500)
    private String content;

    @NotNull
    private Boolean isDeleted;

    @QueryProjection
    public CommentDTO(String content, boolean isDeleted) {
        this.content = content;
        this.isDeleted = isDeleted;
    }
}
