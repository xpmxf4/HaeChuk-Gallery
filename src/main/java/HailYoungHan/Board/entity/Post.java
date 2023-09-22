package HailYoungHan.Board.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post extends SysCols {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;
    private String name;
    private String content;
    private boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    public Post(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public Post(String name, String content, Member member) {
        this.name = name;
        this.content = content;
        this.member = member;
    }

    //===연관관계 메서드===//
    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setPost(this);
    }
}
