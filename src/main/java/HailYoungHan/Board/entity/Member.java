package HailYoungHan.Board.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Member extends SysCols {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String name;
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Post> posts = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    public Member(String name, String password) {
        this.name = name;
        this.password = password;
    }

    //===연관관계 메서드===//
    public void addPost(Post post) {
        posts.add(post);
        post.setMember(this);
    }

    public void removePost(Post post) {
        posts.remove(post);
        post.setMember(null);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setMember(this);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setMember(null);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
