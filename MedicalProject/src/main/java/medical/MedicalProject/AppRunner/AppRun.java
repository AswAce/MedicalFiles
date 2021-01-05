package medical.MedicalProject.AppRunner;

import com.google.gson.Gson;
import medical.MedicalProject.Services.SeedDto.SeedDisease.DiseaseDto;
import medical.MedicalProject.Services.SeedDto.SeedHealthProfile.HealthProfileDto;
import medical.MedicalProject.Services.SeedDto.SeedLocation.LocationSeedDto;
import medical.MedicalProject.Services.SeedDto.SeedMedicineBranches.MedicineBranchesSeedDto;
import medical.MedicalProject.Services.SeedDto.SeedNegativeImpact.NegativeImpactDto;
import medical.MedicalProject.Services.SeedDto.SeedUser.UserDto;
import medical.MedicalProject.Services.SeedDto.SeedVisualResult.VisualResultDto;
import medical.MedicalProject.Services.SeedDto.SeetBloodResult.BloodResultDto;
import medical.MedicalProject.Services.Service.DiseaseResultService.DiseaseResultService;
import medical.MedicalProject.Services.Service.HealthProfileService.HealthProfileService;
import medical.MedicalProject.Services.Service.LocationService.LocationService;
import medical.MedicalProject.Services.Service.MedicineBranchService.MedicineBranchService;
import medical.MedicalProject.Services.Service.NegativeImpactService.NegativeImpactService;
import medical.MedicalProject.Services.Service.UserService.UserService;
import medical.MedicalProject.Services.ViewDto.HealthProfileView.HealthProfileViewDto;
import medical.MedicalProject.Utils.ValidationUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static medical.MedicalProject.DataLocationRoads.LocationOfFiles.*;

@Component
public class AppRun implements CommandLineRunner {
    private MedicineBranchService medicineBranchService;
    private LocationService locationService;
    private NegativeImpactService negativeImpactService;
    private Gson gson;
    private ValidationUtil validator;
    private UserService userService;
    private HealthProfileService healthProfileService;
    private DiseaseResultService diseaseResultService;

    public AppRun(MedicineBranchService medicineBranchService, LocationService locationService, NegativeImpactService negativeImpactService, Gson gson, ValidationUtil validator, UserService userService, HealthProfileService healthProfileService, DiseaseResultService diseaseResultService) {
        this.medicineBranchService = medicineBranchService;
        this.locationService = locationService;
        this.negativeImpactService = negativeImpactService;
        this.gson = gson;
        this.validator = validator;
        this.userService = userService;
        this.healthProfileService = healthProfileService;
        this.diseaseResultService = diseaseResultService;
    }

    @Override
    public void run(String... args) throws Exception {

        //To insert:
        // 1 :MedicineBranches : Ortopedy, Hearth etc....
        //2 Locations: Lower back left knee, hearth, face etc...
        //3 Negavtive impact lower back pain hight blood pressure, depression etc ...

//        addMedicineBranchFromFile();
//        addLocation();
//        addNegativeImpact();
//        addUser();
//        addHealthProfileToUser();
//        addBloodResult();
//        HealthProfileViewDto heathProfile = getHeathProfile(1);
        System.out.println();

        addDisease();

    }

    private void addDisease() {
        ArrayList<String> negativeImpact = new ArrayList<>();
        negativeImpact.add("back pain");
        negativeImpact.add("joint pain");
negativeImpact.add("Boner");
        negativeImpact.add("headache");

        VisualResultDto visualResultDto = new VisualResultDto("x-ray", "image adress");

        DiseaseDto diseaseDto = new DiseaseDto("Lower back pain",
                "Pain in lower spines after workout or sitting",
                false, "Orthopaedic",
                "lower back",negativeImpact,   visualResultDto);


        this.diseaseResultService.saveDisease(diseaseDto);
    }

    private HealthProfileViewDto getHeathProfile(int id) {
        return this.healthProfileService.getHealthProfile(id);
    }

    private void addBloodResult() {
        BloodResultDto bloodResultDto = new BloodResultDto("Asen_asw2@yahoo.com",
                "2016-03-04 11:30", "Vitamin D:0,15");
        this.healthProfileService.addBloodResult(bloodResultDto);
    }

    private void addHealthProfileToUser() {

        HealthProfileDto healthProfileDto = new HealthProfileDto("Asen_asw2@yahoo.com", 31, 1.86, 75);
        if (this.validator.isValid(healthProfileDto)) {
            this.healthProfileService.createHealthProfile(healthProfileDto);
        } else {
            this.validator.getViolation(healthProfileDto).stream().map(ConstraintViolation::getMessage).
                    forEach(System.out::println);
        }


    }

    private void addUser() {
        UserDto userDto = new UserDto("Asen_asw2@yahoo.com",
                "123456",
                "Asen",
                "Rangelov");

        this.userService.createUser(userDto);


    }

    private void addMedicineBranchFromFile() throws IOException {
        String allBranches = String.join("", Files.readAllLines(Path.of(MEDICINE_BRANCHES_LOCATIONS)));
        MedicineBranchesSeedDto[] medicineBranchesSeedDtos = this.gson.fromJson(allBranches, MedicineBranchesSeedDto[].class);
        this.medicineBranchService.insertMedicineBranchesFromFile(medicineBranchesSeedDtos);
    }


    private void addLocation() throws IOException {
        String injuryLocations = String.join("", Files.readAllLines(Path.of(LOCATION_FILE)));
        LocationSeedDto[] locationSeedDtos = this.gson.fromJson(injuryLocations, LocationSeedDto[].class);
        this.locationService.addLocationsFromFile(locationSeedDtos);
    }

    private void addNegativeImpact() throws IOException {
        String negativeImpacts = String.join("", Files.readAllLines(Path.of(NEGATIVE_IMPACT_FILE)));
        NegativeImpactDto[] negativeImpactFromFile = this.gson.fromJson(negativeImpacts, NegativeImpactDto[].class);
        for (NegativeImpactDto nI : negativeImpactFromFile
        ) {
            if (this.validator.isValid(nI)) {
                this.negativeImpactService.addNegativeImpact(nI);
            } else {
                this.validator.getViolation(nI).stream().map(ConstraintViolation::getMessage).
                        forEach(System.out::println);
            }

        }

    }
}
