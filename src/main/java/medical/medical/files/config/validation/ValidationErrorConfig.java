package medical.medical.files.config.validation;

import org.springframework.validation.BindingResult;

import java.util.Map;

public interface ValidationErrorConfig {

    Map<String, String> getValidationMessages(BindingResult bindingResult);
}
