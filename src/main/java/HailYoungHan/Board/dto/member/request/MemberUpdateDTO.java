package HailYoungHan.Board.dto.member.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
//@AtLeastOneNotNull(fields = {"name", "email", "password"})
public class MemberUpdateDTO {
    @Size(min = 2, max = 50, message = "이름은 2자 이상, 50자 이하입니다.")
    @Nullable // @NotBlank 박으라고
    private String name;

    @Email(message = "유효한 이메일 형식이 아닙니다.") // 민재왈 : 씨발롬아 뚫린다니까, @Pattern 쓰라고
    @Nullable // @NotBlank
    private String email;

    @Size(min = 8, message = "비밀번호는 최소 8자리 이상입니다.")
    @Nullable // @NotBlank
    private String password;
}
