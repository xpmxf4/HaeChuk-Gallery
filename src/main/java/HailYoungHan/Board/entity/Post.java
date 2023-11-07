package HailYoungHan.Board.entity;

import HailYoungHan.Board.dto.post.query.PostDbDTO;
import HailYoungHan.Board.dto.post.request.PostRegiDTO;
import HailYoungHan.Board.dto.post.request.PostUpdateDTO;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends SysTimeCols {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @Column(length = 50)
    private String title;

    @Column(length = 1000)
    private String content;

    private Boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    public static Post mapFromRegiDto(Member member, PostRegiDTO postRegiDTO) {

        return Post.builder()
                .member(member)
                .title(postRegiDTO.getTitle())
                .content(postRegiDTO.getContent())
                .build();
    }

    public void setMember(Member author) {
        this.member = author;
    }

    //===연관관계 메서드===//
    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setPost(this);
    }

    public void updateFieldsFromUpdateDto(PostUpdateDTO updateDTO) {
        if (updateDTO.getTitle() != null)
            this.title = updateDTO.getTitle();
        if (updateDTO.getContent() != null)
            this.content = updateDTO.getContent();
    }

    public PostDbDTO mapToDbDTO() {
        return PostDbDTO.builder()
                .id(id)
                .title(title)
                .content(content)
                .writer(member.getName())
                .isDeleted(isDeleted)
                .build();
    }
}
