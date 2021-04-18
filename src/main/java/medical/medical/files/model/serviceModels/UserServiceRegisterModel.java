package medical.medical.files.model.serviceModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import medical.medical.files.model.enums.RoleEnum;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserServiceRegisterModel {


    private String username;

    private String email;


    private String password;

    private RoleEnum role;
}
