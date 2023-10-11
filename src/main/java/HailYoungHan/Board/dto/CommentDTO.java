package HailYoungHan.Board.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class CommentDTO {

    private String content;
    private Boolean isDeleted;

    @QueryProjection
    public CommentDTO(Long commentId, String content, boolean isDeleted) {
        this.content = content;
        this.isDeleted = isDeleted;
    }
}
