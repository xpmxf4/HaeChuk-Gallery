package HailYoungHan.Board.dto.member;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class MemberDTO {

    @NotNull
    private Long id;

    @Size(min = 2, max = 50, message = "이름은 2자 이상, 50자 이하입니다.")
    @NotBlank(message = "회원 이름은 비어있을 수 없습니다.")
    private String name;

    @NotBlank(message = "회원 이메일은 비어있을 수 없습니다.")
    @Email(message = "유효한 이메일 형식이 아닙니다")
    private String email;

    @QueryProjection
    public MemberDTO(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    @QueryProjection
    public MemberDTO(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
