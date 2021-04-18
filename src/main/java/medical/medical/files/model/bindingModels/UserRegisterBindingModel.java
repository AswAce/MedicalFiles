package medical.medical.files.model.bindingModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import medical.medical.files.model.enums.RoleEnum;
import net.minidev.json.annotate.JsonIgnore;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterBindingModel {

    private long id;

    @NotEmpty

    @Size(min = 3, max = 10, message = "size must be between 3 and 10")
    private String username;
    @NotEmpty
    @Email(message = "enter valid email")
    private String email;


    private String oldPassword;
    @NotEmpty
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "Password must contain Minimum eight characters, at least one letter and one number:")
    @JsonIgnore(value = true)
    private String password;
    @JsonIgnore(value = true)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "Password must contain Minimum eight characters, at least one letter and one number:")
    private String repeatPassword;
    @NotNull
    private RoleEnum role;

    public boolean passwordMatch() {
        return this.password.equals(repeatPassword);
    }
}
