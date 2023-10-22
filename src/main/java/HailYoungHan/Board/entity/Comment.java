package HailYoungHan.Board.entity;

import HailYoungHan.Board.dto.comment.CommentRegiDTO;
import HailYoungHan.Board.entity.SysCols;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends SysCols {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;
    private String content;
    private Boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent")
    private List<Comment> children = new ArrayList<>();

    public static Comment mapFromRegiDto(Member author,
                                         Post commentedPost,
                                         Comment parentComment,
                                         CommentRegiDTO commentRegiDTO) {

        return Comment.builder()
                .content(commentRegiDTO.getContent())
                .member(author)
                .post(commentedPost)
                .parent(parentComment)
                .build();
    }

    //===연관관계 메서드===//
    public void addChild(Comment comment) {
        children.add(comment);
        comment.setParent(this);
    }

    private void setParent(Comment parent) {
        this.parent = parent;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public void setMember(Member author) {
        this.member = author;
    }
}
