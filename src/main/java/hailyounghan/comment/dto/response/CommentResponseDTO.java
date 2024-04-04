package hailyounghan.comment.dto.response;

import hailyounghan.comment.dto.query.CommentDbDTO;
import lombok.Getter;

import java.util.List;

// [ ... ] 대신
// { comments : [...] } 형태로 내보내기 위한 DTO
@Getter
public class CommentResponseDTO {

    private List<CommentDbDTO> comments;

    public CommentResponseDTO(List<CommentDbDTO> comments) {
        this.comments = comments;
    }
}
