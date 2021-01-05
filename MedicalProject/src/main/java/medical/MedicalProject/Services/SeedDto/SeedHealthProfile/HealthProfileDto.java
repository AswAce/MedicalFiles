package medical.MedicalProject.Services.SeedDto.SeedHealthProfile;

import lombok.*;
import medical.MedicalProject.entities.User;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class HealthProfileDto {

    @NotNull
    @NonNull
    private String userEmail;
    @NonNull

    @DecimalMin(value = "1", message = "Enter valid age")
    @DecimalMax(value = "120", message = "Enter valid age")
    private int age;
    @NonNull

    private double height;
    @NonNull

    private double weight;
    private double bdi;

    public HealthProfileDto(String userEmail, int age, double height, double weight) {
        this.userEmail = userEmail;
        this.age = age;
        this.height = height;
        this.weight = weight;

        this.bdi = Math.round((weight / (height * height)) * 100.0) / 100.0;
    }
}
