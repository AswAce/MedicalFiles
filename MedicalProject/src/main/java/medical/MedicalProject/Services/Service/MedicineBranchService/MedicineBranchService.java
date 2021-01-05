package medical.MedicalProject.Services.Service.MedicineBranchService;

import medical.MedicalProject.Services.SeedDto.SeedMedicineBranches.MedicineBranchesSeedDto;
import medical.MedicalProject.entities.MedicineBranch;

import java.util.ArrayList;

public interface MedicineBranchService {

    void insertMedicineBranch(MedicineBranchesSeedDto medicineBranchesSeed);

    ArrayList<MedicineBranch> getAllMedicineBranch();

    void insertMedicineBranchesFromFile(MedicineBranchesSeedDto[] medicineBranchesSeedDtos);

    MedicineBranch getMedicalBranchByName(String medicineBranch);
}
