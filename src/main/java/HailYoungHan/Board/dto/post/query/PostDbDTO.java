package HailYoungHan.Board.dto.post.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@Builder
public class PostDbDTO {

    @NotNull
    private Long id;

    @NotBlank
    @Size(min = 1, max = 50)
    private String title;

    @NotBlank
    @Size(min = 1, max = 1000)
    private String content;

    @NotBlank
    @Size(min = 2, max = 50)    // Member 의 name
    private String writer;

    @NotNull
    private boolean isDeleted;

    @QueryProjection
    public PostDbDTO(Long id, String title, String content, String writer, boolean isDeleted) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.isDeleted = isDeleted;
    }
}
