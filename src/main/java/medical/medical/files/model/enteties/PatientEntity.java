package medical.medical.files.model.enteties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import medical.medical.files.model.enums.DrinkingEnum;
import medical.medical.files.model.enums.ExerciseEnum;
import medical.medical.files.model.enums.SexEnum;
import medical.medical.files.model.enums.SmokingEnum;


import javax.persistence.*;

import java.util.LinkedHashSet;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "patients")

public class PatientEntity extends BaseEntity {
    @Column(nullable = false, name = "full_name")
    private String fullName;
    @Column(nullable = false)
    private int age;
    private String phone;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(nullable = false)
    private double height;

    @Column(nullable = false)
    private double weight;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SexEnum sex;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SmokingEnum smoking;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DrinkingEnum drinking;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ExerciseEnum exercise;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<DiseaseEntity> diseases = new LinkedHashSet<>();


    @Override
    public String toString() {
        return "PatientEntity{" +
                "fullName='" + fullName + '\'' +
                ", age=" + age +
                ", height=" + height +
                ", weight=" + weight +
                ", sex=" + sex +
                ", smoking=" + smoking +
                ", drinking=" + drinking +
                ", exercise=" + exercise +
                '}';
    }
}
