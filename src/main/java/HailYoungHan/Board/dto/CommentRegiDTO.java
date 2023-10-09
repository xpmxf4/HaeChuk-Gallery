package HailYoungHan.Board.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class CommentRegiDTO {

    private String content;
    private Long memberId;
    private Long postId;
    private Long parentCommentId;

    @QueryProjection
    public CommentRegiDTO(String content, Long memberId, Long postId, Long parentCommentId) {
        this.content = content;
        this.memberId = memberId;
        this.postId = postId;
        this.parentCommentId = parentCommentId;
    }
}
