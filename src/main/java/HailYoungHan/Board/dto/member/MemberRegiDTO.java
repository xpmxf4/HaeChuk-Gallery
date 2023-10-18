package HailYoungHan.Board.dto.member;


import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Builder
public class MemberRegiDTO {

    @NotBlank(message = "회원 등록시 이름은 필수입니다.")
    @Size(min = 2, max = 50, message = "이름은 2자 이상, 50자 이하입니다.")
    private String name;

    @NotBlank(message = "회원 등록시 이메일은 필수입니다.")
    @Email
    private String email;

    @NotBlank(message = "회원 등록시 비밀번호는 필수입니다.")
    @Size(min = 8, message = "비밀번호는 최소 8자리 이상입니다.")
    private String password;
}
