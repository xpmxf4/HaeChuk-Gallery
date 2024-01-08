package hailyounghan.board.dto.popularPost.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class PopularPostDTO {

    private Long id;

    private String title;

    private String writer;

    @QueryProjection
    public PopularPostDTO(Long id, String title, String writer) {
        this.id = id;
        this.title = title;
        this.writer = writer;
    }
}
