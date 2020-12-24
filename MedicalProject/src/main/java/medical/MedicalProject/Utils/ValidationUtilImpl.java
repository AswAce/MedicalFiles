package medical.MedicalProject.Utils;



import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;



public class ValidationUtilImpl implements ValidationUtil {

    private final Validator validator;


    public ValidationUtilImpl(Validator validator) {
        this.validator = validator;
    }


    @Override
    public <E> boolean isValid(E entity) {
        return this.validator.validate(entity).isEmpty();
    }

    @Override
    public <E> Set<ConstraintViolation<E>> getViolation(E entity) {
        return this.validator.validate(entity);
    }
}
