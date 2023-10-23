package HailYoungHan.Board.dto.post;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
public class PostRegiDTO {

    @NotNull
    private Long memberId;

    @NotBlank
    @Size(min = 1, max = 50)
    private String title;

    @NotBlank
    @Size(min = 1, max = 1000)
    private String content;
}
