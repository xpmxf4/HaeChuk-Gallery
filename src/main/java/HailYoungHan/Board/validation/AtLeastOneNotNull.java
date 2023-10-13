package HailYoungHan.Board.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 지정된 필드 중 최소 하나가 null이 아님을 보장하기 위한 사용자 정의 유효성 검증 어노테이션.
 */
@Documented
@Constraint(validatedBy = AtLeastOneNotNullValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AtLeastOneNotNull {

    String message() default "최소한 하나의 필드는 null이 아니어야 합니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] fields() default {};
}