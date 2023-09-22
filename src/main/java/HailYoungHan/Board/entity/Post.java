package HailYoungHan.Board.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Post extends SysCols {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;
    private String name;
    private String content;
    private boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    //===연관관계 메서드===//
    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setPost(this);
    }
}
