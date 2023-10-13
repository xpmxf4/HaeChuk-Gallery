package HailYoungHan.Board.validation;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @AtLeastOneNotNull 어노테이션을 위한 검증기 구현.
 */
public class AtLeastOneNotNullValidator implements ConstraintValidator<AtLeastOneNotNull, Object> {

    private String[] fields;

    @Override
    public void initialize(AtLeastOneNotNull constraintAnnotation) {
        this.fields = constraintAnnotation.fields();
    }

    /**
     * 객체 내의 지정된 필드 중 최소 하나가 null이 아닌지 확인합니다.
     *
     * @param value 검증할 객체.
     * @param context 검증 프로세스의 컨텍스트.
     * @return 최소 하나의 필드가 null이 아니면 true, 그렇지 않으면 false.
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        if (value == null) {
            return true;
        }

        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(value);
        for (String field : fields) {
            if (beanWrapper.getPropertyValue(field) != null) {
                return true;
            }
        }

        return false;
    }
}
