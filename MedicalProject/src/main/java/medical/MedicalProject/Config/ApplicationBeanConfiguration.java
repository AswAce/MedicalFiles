package medical.MedicalProject.Config;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import medical.MedicalProject.Utils.FIleIotImpl;
import medical.MedicalProject.Utils.ValidationUtil;
import medical.MedicalProject.Utils.ValidationUtilImpl;
import medical.MedicalProject.Utils.XMLParserImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;


@Configuration
public class ApplicationBeanConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }



    @Bean
    public Validator validator(){
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Bean
    public ValidationUtil validationUtil(){
        return new ValidationUtilImpl(validator());
    }

    @Bean
    public XMLParserImpl xmlParser() {
        return new XMLParserImpl();
    }

    @Bean
    public FIleIotImpl fileIot() {
        return new FIleIotImpl();
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting().create();
    }

}
