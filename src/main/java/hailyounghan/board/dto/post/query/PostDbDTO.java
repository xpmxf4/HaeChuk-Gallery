package hailyounghan.board.dto.post.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

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
    private Boolean isDeleted;

    private LocalDateTime reg_date;

    @QueryProjection
    public PostDbDTO(Long id, String title, String content, String writer, boolean isDeleted, LocalDateTime reg_date) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.isDeleted = isDeleted;
        this.reg_date = reg_date;
    }
}
