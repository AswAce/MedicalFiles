package medical.medical.files.model.enteties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import medical.medical.files.model.enums.HospitalRoleEnum;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class UserEntity extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false, unique = true, name = "emails")
    private String email;


    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<RoleEntity> roles = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    private PatientEntity patientEntity;
    @OneToOne(cascade = CascadeType.ALL)
    private DoctorEntity doctorEntity;
}
