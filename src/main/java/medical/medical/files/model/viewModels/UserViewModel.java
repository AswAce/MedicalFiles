package medical.medical.files.model.viewModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import medical.medical.files.model.enums.RoleEnum;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

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

}
