package medical.medical.files.config.validation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

@Data

@Component
public class ValidationErrorConfigImpl implements ValidationErrorConfig {


    public Map<String, String> getValidationMessages(BindingResult bindingResult) {
        Map<String, String> map = new HashMap();
        ;
        for (int i = 0; i < bindingResult.getFieldErrors().size(); i++) {
            map.put
                    (bindingResult.getFieldErrors().get(i).getField(),
                            bindingResult.getFieldErrors().get(i).getDefaultMessage());
        }
        ;
        return map;
    }
}
