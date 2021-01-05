package medical.MedicalProject.Services.Service.MedicineBranchService;

import medical.MedicalProject.Repositories.MedicineBranchRepository;
import medical.MedicalProject.Services.SeedDto.SeedMedicineBranches.MedicineBranchesSeedDto;
import medical.MedicalProject.entities.MedicineBranch;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class MedicineBranchServiceImpl implements MedicineBranchService {

    private final MedicineBranchRepository medicineBranchRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public MedicineBranchServiceImpl(MedicineBranchRepository medicineBranchRepository, ModelMapper modelMapper) {
        this.medicineBranchRepository = medicineBranchRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void insertMedicineBranch(MedicineBranchesSeedDto medicineBranchesSeed) {


        for (MedicineBranch medicineBranchName : getAllMedicineBranch()
        ) {
            if (medicineBranchName.getName().equals(medicineBranchesSeed.getName())) {
// create and return error massage
                System.out.println("already exist");
                return;
            }

        }
        MedicineBranch medicineBranch = this.modelMapper.map(medicineBranchesSeed, MedicineBranch.class);
        this.medicineBranchRepository.save(medicineBranch);

    }

    @Override
    public ArrayList<MedicineBranch> getAllMedicineBranch() {

        return (ArrayList<MedicineBranch>) this.medicineBranchRepository.findAll();
    }

    @Override
    public void insertMedicineBranchesFromFile(MedicineBranchesSeedDto[] medicineBranchesSeedDtos) {
        Arrays.stream(medicineBranchesSeedDtos).forEach(this::insertMedicineBranch);
    }

    @Override
    public MedicineBranch getMedicalBranchByName(String medicineBranch) {
        return this.medicineBranchRepository.findByName(medicineBranch);
    }
}
