package medical.MedicalProject.Services.ViewDto.HealthProfileView;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import medical.MedicalProject.Services.SeedDto.SeedUser.UserDto;

import medical.MedicalProject.Services.ViewDto.BloodResultView.BloodResultDtoView;


import java.util.ArrayList;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class HealthProfileViewDto {

    private UserDto UserDto;
    private int age;
    private double height;
    private double weight;
    private double bdi;
    private ArrayList<BloodResultDtoView> bloodResultDtos;

}
