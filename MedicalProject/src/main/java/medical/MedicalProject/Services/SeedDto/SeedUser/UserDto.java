package medical.MedicalProject.Services.SeedDto.SeedUser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserDto {
    @NonNull
    @NotNull
    @Email(message = "Enter valid email")
    private String email;
    @NonNull
    @NotNull
    @Size(min = 6, message = "password size does not match")
    private String password;
    @NonNull
    @NotNull
    private String firstName;
    @NotNull
    @NonNull
    private String lastName;

}
