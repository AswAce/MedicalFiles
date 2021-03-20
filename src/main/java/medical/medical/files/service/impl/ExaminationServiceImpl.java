package medical.medical.files.service.impl;

import medical.medical.files.exeptions.ExaminationNotFoundException;
import medical.medical.files.model.bindingModels.FeedbackBindingModel;
import medical.medical.files.model.enteties.*;
import medical.medical.files.model.enums.PartOfTheBodyEnum;
import medical.medical.files.model.enums.ProgressionEnum;
import medical.medical.files.model.enums.SideOfTheBodyEnum;
import medical.medical.files.model.serviceModels.*;
import medical.medical.files.model.viewModels.AddAdditionalDataViewModel;
import medical.medical.files.model.viewModels.ExaminationViewModel;
import medical.medical.files.model.viewModels.PrescriptionViewModel;
import medical.medical.files.repositorie.ExaminationRepository;
import medical.medical.files.service.ExaminationService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ExaminationServiceImpl implements ExaminationService {
    private final ModelMapper modelMapper;
    private final ExaminationRepository examinationRepository;

    public ExaminationServiceImpl(ModelMapper modelMapper, ExaminationRepository examinationRepository) {
        this.modelMapper = modelMapper;
        this.examinationRepository = examinationRepository;
    }

    @Override
    public void editExamination(AddExaminationServiceModel examinationServiceModel, Long id) {
        ExaminationEntity examinationEntity = this.modelMapper.
                map(examinationServiceModel, ExaminationEntity.class);
        Optional<ExaminationEntity> byId = this.examinationRepository.findById(id);
        ExaminationEntity fromDB = modelMapper.map(byId, ExaminationEntity.class);
        examinationEntity.setId(id);
        examinationEntity.setDoctor(fromDB.getDoctor());
        examinationEntity.setProgression(fromDB.getProgression());
        this.examinationRepository.save(examinationEntity);


    }

    @Override
    public void deleteExamination(long id) {
        this.examinationRepository.deleteById(id);
    }

    @Override
    public void addDoctorPart(ExaminationByDoctorServiceModel examinationByDoctorServiceModel, long examinationId) throws ExaminationNotFoundException {
        ExaminationEntity examinationEntity = this.examinationRepository.findById(examinationId).orElseThrow(ExaminationNotFoundException::new);
        if (examinationEntity.getDoctor().getId() == examinationByDoctorServiceModel.getDoctorId()) {

            examinationEntity.setProgression(ProgressionEnum.CURRENT);
            String exactLocation = examinationByDoctorServiceModel.getExactLocation();
            PartOfTheBodyEnum partOfTheBody = examinationByDoctorServiceModel.getPartOfTheBody();
            SideOfTheBodyEnum sideOfTheBody = examinationByDoctorServiceModel.getSideOfTheBody();
            LocationEntity locationEntity = new LocationEntity(partOfTheBody, sideOfTheBody, exactLocation);

            examinationEntity.setLocation(locationEntity);
            this.examinationRepository.save(examinationEntity);
        }
    }

    @Override
    public void addPrescription(PrescriptionServiceModel prescriptionServiceModel, long id) throws ExaminationNotFoundException {
        ExaminationEntity examinationEntity = this.examinationRepository.findById(id).orElseThrow(ExaminationNotFoundException::new);
        PrescriptionEntity prescriptionEntity = this.modelMapper.map(prescriptionServiceModel, PrescriptionEntity.class);
        if (examinationEntity.getDoctor().getId() == prescriptionServiceModel.getDoctorId()) {
            examinationEntity.setPrescription(prescriptionEntity);
        }


    }

    @Override
    public void addAdditionalData(AdditionalDataServiceModel additionalDataServiceModel, long examinationId) throws ExaminationNotFoundException {

        LocationEntity locationEntity = new LocationEntity(additionalDataServiceModel.getPartOfTheBody(),
                additionalDataServiceModel.getSideOfTheBody(),
                additionalDataServiceModel.getExactLocation());
        AdditionalDataEntity additionalDataEntity = this.modelMapper.map(additionalDataServiceModel,
                AdditionalDataEntity.class);
        additionalDataEntity.setLocationEntity(locationEntity);
        ExaminationEntity examinationRepositoryById = this.examinationRepository.findById(examinationId).orElseThrow(ExaminationNotFoundException::new);
        if (examinationRepositoryById.getDoctor().getId()
                == additionalDataServiceModel.getDoctorId()) {
            examinationRepositoryById.getAdditionalData().add(additionalDataEntity);
        }
    }

    @Override
    public void completeExamination(long doctorId, long examinationId) throws ExaminationNotFoundException {
        ExaminationEntity examinationEntity = this.examinationRepository.findById(examinationId).orElseThrow(ExaminationNotFoundException::new);
        if (examinationEntity.getDoctor().getId() == doctorId) {
            examinationEntity.setProgression(ProgressionEnum.DONE);
            this.examinationRepository.save(examinationEntity);
        }
    }

    @Override
    public ExaminationEntity findByExaminationId(long examinationId) throws ExaminationNotFoundException {

        return this.examinationRepository.findById(examinationId).orElseThrow(() -> new ExaminationNotFoundException("Examination: " + examinationId + " was not found."));
    }

    @Override
    public ExaminationViewModel getExamination(ExaminationEntity examination) {
        List<AddAdditionalDataViewModel> additionalDataViewModels = examination.getAdditionalData().stream().map(additionalDataEntity ->
                this.modelMapper.map(additionalDataEntity, AddAdditionalDataViewModel.class)).collect(Collectors.toList());
        ExaminationViewModel examinationViewModel = this.modelMapper.map(examination, ExaminationViewModel.class);
        if (examination.getProgression() != null) {
            PrescriptionViewModel prescriptionViewModel = this.modelMapper.map(examination.getPrescription(), PrescriptionViewModel.class);
            examinationViewModel.setPrescription(prescriptionViewModel);
        }
        if (examination.getDoctor() != null) {
            examinationViewModel.setDoctorFullName(examination.getDoctor().getFullName());
        }
        examinationViewModel.setAdditionalData(additionalDataViewModels);


        return examinationViewModel;
    }

    @Override
    public ArrayList<ExaminationViewModel> findByDoctorId(long doctorId) {
        Set<ExaminationEntity> allByDoctorId = this.examinationRepository.
                findAllByDoctorId(doctorId);
        return (ArrayList<ExaminationViewModel>) allByDoctorId.stream().map(this::getExamination).collect(Collectors.toList());

    }

    @Override
    public FeedbackBindingModel addFeedback(FeedbackServiceModel feedbackServiceModel, long examinationId) throws ExaminationNotFoundException {
        FeedbackEntity feedback = this.modelMapper.map(feedbackServiceModel, FeedbackEntity.class);
        ExaminationEntity examinationEntity = this.examinationRepository.findById(examinationId).orElseThrow(ExaminationNotFoundException::new);
        if (examinationEntity.getProgression()!=null&&examinationEntity.getProgression().equals(ProgressionEnum.DONE)) {

            ExaminationEntity examinationEntity1 = this.examinationRepository.saveAndFlush(examinationEntity);
            return this.modelMapper.map(examinationEntity1, FeedbackBindingModel.class);

        }
        return null;

    }

    @Override
    public ExaminationEntity addExamination(AddExaminationServiceModel examinationServiceModel) {
        ExaminationEntity examinationEntity = this.modelMapper.map(examinationServiceModel, ExaminationEntity.class);
        return this.examinationRepository.save(examinationEntity);

    }


}
