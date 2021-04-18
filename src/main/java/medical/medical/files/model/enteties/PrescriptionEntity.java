package medical.medical.files.model.enteties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "prescriptions")
public class PrescriptionEntity extends BaseEntity {

    @Column(name = "doctors_prescriptions", columnDefinition = "TEXT")
    private String doctorPrescription;

    private int days;





}
