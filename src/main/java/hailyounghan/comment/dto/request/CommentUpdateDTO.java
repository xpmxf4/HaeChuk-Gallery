package hailyounghan.comment.dto.request;

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
}
