package HailYoungHan.Board.dto.member.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@Builder
//@AtLeastOneNotNull(fields = {"name", "email", "password"})
public class MemberUpdateDTO {
    @Size(min = 2, max = 50, message = "이름은 2자 이상, 50자 이하입니다.")
    @NotBlank
    private String name;

    @Pattern(regexp = "^[A-Za-z]+@[A-Za-z]+\\.[A-Za-z]{2,}$")
    @NotBlank
    private String email;

    @Size(min = 8, message = "비밀번호는 최소 8자리 이상입니다.")
    @NotBlank
    private String password;
}
