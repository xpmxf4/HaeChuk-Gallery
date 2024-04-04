package hailyounghan.comment.dto.request;

import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Builder
public class CommentRegiDTO {

    @NotBlank
    @Size(min = 1, max = 500)
    private String content;

    @NotNull
    private Long memberId;

    @NotNull
    private Long postId;

    @Nullable // 대댓글이 아니라면 null 이 들어옴.
    private Long parentCommentId;
}
