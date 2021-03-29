package medical.medical.files.model.enteties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import medical.medical.files.model.enums.MedicalBranchesEnum;

import javax.persistence.*;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "doctors")
public class DoctorEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "medical_branch")
    private MedicalBranchesEnum medicalBranch;
    @Column(nullable = false, name = "full_name")
    private String fullName;
    @Column(columnDefinition = "TEXT")
    private String bio;
    @Column(name = "picture_url")
    private String photo;
    @Column(nullable = false)
    private String phone;

    @Column(name = "room_number", nullable = false)
    private String roomNumber;

    @Column(nullable = false)
    private int experience;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private ScheduleEntity schedule;

    @Override
    public String toString() {
        return "DoctorEntity{" +
                "medicalBranch=" + medicalBranch +
                ", fullName='" + fullName + '\'' +
                ", bio='" + bio + '\'' +
                ", pictureUrl='" + photo + '\'' +
                ", phone='" + phone + '\'' +
                ", roomNumber='" + roomNumber + '\'' +
                ", experience=" + experience +

                '}';
    }
}
