package HailYoungHan.Board.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentDTO {

    private Long id;
    private String content;
    private String writer;
    private PostDetails postDetails;
    private boolean isDeleted;

    @QueryProjection
    public CommentDTO(Long id, String content, String writer, Long postId, String postTitle, String postContent, boolean isDeleted) {
        this.id = id;
        this.content = content;
        this.writer = writer;
        this.postDetails = new PostDetails(postId, postTitle, postContent);
        this.isDeleted = isDeleted;
    }

    @Getter
    @NoArgsConstructor
    private static class PostDetails {
        private Long id;
        private String title;
        private String postContent;

        public PostDetails(Long id, String title, String postContent) {
            this.id = id;
            this.title = title;
            this.postContent = postContent;
        }
    }
}
