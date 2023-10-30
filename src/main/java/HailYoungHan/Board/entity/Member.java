package HailYoungHan.Board.entity;

import HailYoungHan.Board.dto.member.request.MemberUpdateDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends SysTimeCols {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Post> posts = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    public void mapFromUpdateDto(MemberUpdateDTO updateDTO) { // if문을 왜 씀?
        if (updateDTO.getName() != null)
            this.name = updateDTO.getName();
        if (updateDTO.getPassword() != null)
            this.password = updateDTO.getPassword();
        if (updateDTO.getEmail() != null)
            this.email = updateDTO.getEmail();
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

}
