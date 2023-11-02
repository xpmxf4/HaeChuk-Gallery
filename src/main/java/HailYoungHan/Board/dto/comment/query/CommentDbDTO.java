package HailYoungHan.Board.dto.comment.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Builder
public class CommentDbDTO {

    @NotBlank
    @Size(min = 1, max = 500)
    private String content;

    @NotNull
    private Boolean isDeleted;

    @QueryProjection
    public CommentDbDTO(String content, boolean isDeleted) {
        this.content = content;
        this.isDeleted = isDeleted;
    }
}
