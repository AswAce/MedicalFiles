package medical.medical.files.service.impl;

import medical.medical.files.exeptions.ExaminationNotFoundException;
import medical.medical.files.model.bindingModels.PatientBindingModel;
import medical.medical.files.model.enteties.DoctorEntity;
import medical.medical.files.model.enteties.ExaminationEntity;
import medical.medical.files.model.enteties.PatientEntity;
import medical.medical.files.model.enums.ProgressionEnum;
import medical.medical.files.model.serviceModels.AddExaminationServiceModel;
import medical.medical.files.model.serviceModels.PatientServiceModel;
import medical.medical.files.model.viewModels.*;
import medical.medical.files.repositorie.PatientRepository;
import medical.medical.files.service.DoctorService;
import medical.medical.files.service.ExaminationService;
import medical.medical.files.service.PatientService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;
    private final DoctorService doctorService;
    private final ExaminationService examinationService;


    public PatientServiceImpl(PatientRepository patientRepository, ModelMapper modelMapper, DoctorService doctorService, ExaminationService examinationService) {
        this.patientRepository = patientRepository;
        this.modelMapper = modelMapper;
        this.doctorService = doctorService;
        this.examinationService = examinationService;
    }

    @Override
    public PatientBindingModel savePatient(PatientServiceModel createPatientServiceModel) {
        PatientEntity patientEntity = this.modelMapper.map(createPatientServiceModel, PatientEntity.class);
        PatientEntity save = this.patientRepository.save(patientEntity);
        return this.modelMapper.map(save, PatientBindingModel.class);
    }

    @Override
    public void addExaminationToThePatient(AddExaminationServiceModel examinationServiceModel) {
        //намираме пациент по ид и му добавяме нов преглед който той е създал
        PatientEntity patientEntity = this.patientRepository.
                findById(examinationServiceModel.getPatientId());

        //Може да не работи и да трябва да си върнеме патиент ентити
        DoctorEntity doctorByName = this.doctorService.findByName(examinationServiceModel.getDoctorName());
        ExaminationEntity examinationEntity = this.modelMapper.
                map(examinationServiceModel, ExaminationEntity.class);
        examinationEntity.setProgression(ProgressionEnum.BOOKED);
        examinationEntity.setDoctor(doctorByName);
        ExaminationEntity addedNewExamination = this.examinationService.addExamination(examinationServiceModel);

        patientEntity.getExaminations().add(addedNewExamination);

        this.patientRepository.save(patientEntity);
    }

    @Override
    public PatientViewModel findById(long patientId) {
        PatientEntity patientEntityById = this.patientRepository.findById(patientId);
        //Мap for patientVIew;
        return this.mapPatientEntityToPatientViewModel(patientEntityById);
    }

    @Override
    public ExaminationViewModel getPatientExamination(long patientId, long examinationId) throws ExaminationNotFoundException {
        ExaminationEntity byExaminationId = this.examinationService.findByExaminationId(examinationId);
        return this.examinationService.getExamination(byExaminationId);

    }

    @Override
    public ArrayList<DiseaseViewModel> getAllDiseasesByPatientId(long patientId) {

        List<DiseaseViewModel> diseaseViewModels = this.patientRepository.findById(patientId).getDiseases().
                stream().
                map(diseaseEntity -> this.modelMapper.map(diseaseEntity, DiseaseViewModel.class)).
                collect(Collectors.toList());
        return (ArrayList<DiseaseViewModel>) diseaseViewModels;
    }

    private PatientViewModel mapPatientEntityToPatientViewModel(PatientEntity patientEntity) {

        PatientViewModel patientViewModel = this.modelMapper.map(patientEntity, PatientViewModel.class);
//Може да го имаме в веба.
//        Мапваме вскичи прегледи към пациента;
        ArrayList<ExaminationViewModel> examinationViewModels = new ArrayList<>();
        for (ExaminationEntity examination : patientEntity.getExaminations()) {
            ExaminationViewModel examinationViewModel = this.examinationService.getExamination(examination);
            examinationViewModels.add(examinationViewModel);
        }


        return patientViewModel;
    }


}
