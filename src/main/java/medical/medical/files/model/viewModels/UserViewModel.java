package medical.medical.files.model.viewModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserViewModel {
    private long id;
    private String username;
    private String email;
    private String role;
    private long roleId;
    private String photo;
    private String fullName;
    private String password;
    private String oldPassword;
    private String repeatPassword;

}
