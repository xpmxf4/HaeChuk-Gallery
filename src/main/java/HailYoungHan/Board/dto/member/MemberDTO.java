package HailYoungHan.Board.dto.member;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class MemberDTO {

    private Long id;
    @Size(min = 2, max = 50, message = "이름은 2자 이상, 50자 이하입니다.")
    private String name;
    @Email
    private String email;

    @QueryProjection
    public MemberDTO(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
