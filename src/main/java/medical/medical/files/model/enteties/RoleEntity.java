package medical.medical.files.model.enteties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import medical.medical.files.model.enums.RoleEnum;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity(name = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class RoleEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private RoleEnum role;
}
