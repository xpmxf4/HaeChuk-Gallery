package HailYoungHan.Board.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostDTO {

    private Long id;
    private String title;
    private String content;
    private boolean isDeleted;

    @QueryProjection
    public PostDTO(Long id, String title, String content, boolean isDeleted) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.isDeleted = isDeleted;
    }
}
