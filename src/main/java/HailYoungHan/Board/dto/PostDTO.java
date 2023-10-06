package HailYoungHan.Board.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class PostDTO {

    private Long id;
    private String title;
    private String content;
    private String writer;
    private boolean isDeleted;

    @QueryProjection
    public PostDTO(Long id, String title, String content, String writer, boolean isDeleted) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.isDeleted = isDeleted;
    }
}
