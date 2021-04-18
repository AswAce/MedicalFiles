package medical.medical.files.service.impl;

import medical.medical.files.config.currentUser.IAuthenticationFacade;
import medical.medical.files.exeptions.ExaminationNotFoundException;
import medical.medical.files.exeptions.WrongDoctorException;
import medical.medical.files.model.enteties.*;
import medical.medical.files.model.enums.MedicalBranchesEnum;
import medical.medical.files.model.enums.ProgressionEnum;
import medical.medical.files.model.serviceModels.*;
import medical.medical.files.model.viewModels.*;
import medical.medical.files.repositorie.ExaminationRepository;
import medical.medical.files.service.*;
import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ExaminationServiceImpl implements ExaminationService {
    private final ModelMapper modelMapper;
    private final ExaminationRepository examinationRepository;
    private final DoctorService doctorService;
    private final UserService userService;
    private final CloudinaryService cloudinaryService;
    private final IAuthenticationFacade authenticationFacade;

    public ExaminationServiceImpl(ModelMapper modelMapper, ExaminationRepository examinationRepository, DoctorService doctorService, UserService userService, CloudinaryService cloudinaryService, IAuthenticationFacade authenticationFacade) {
        this.modelMapper = modelMapper;
        this.examinationRepository = examinationRepository;
        this.doctorService = doctorService;
        this.userService = userService;
        this.cloudinaryService = cloudinaryService;

        this.authenticationFacade = authenticationFacade;
    }


    @Override
    public void addPrescription(AddPrescriptionServiceModel prescriptionServiceModel) throws ExaminationNotFoundException {
        ExaminationEntity examinationEntity = this.examinationRepository.findById(prescriptionServiceModel.getExaminationId()).orElseThrow(ExaminationNotFoundException::new);
        PrescriptionEntity prescription = examinationEntity.getPrescription();
        if (prescription != null) {
            prescription.setDays(prescriptionServiceModel.getDays());
            prescription.setDoctorPrescription(prescriptionServiceModel.getDoctorPrescription());
            examinationEntity.setPrescription(prescription);
        } else {

            PrescriptionEntity newPrescription = new PrescriptionEntity(prescriptionServiceModel.getDoctorPrescription(), prescriptionServiceModel.getDays());
            examinationEntity.setPrescription(newPrescription);
        }

        ;
        UserEntity byUserName = this.userService.findByUserName(authenticationFacade.getAuthentication().getName());
        ;
        if (byUserName.getDoctorEntity() != null &&
                byUserName.getDoctorEntity().getId() == examinationEntity.getDoctor().getId()) {
            this.examinationRepository.save(examinationEntity);
        }

    }


    @Override
    public void completeExamination(long examinationId) throws ExaminationNotFoundException {
        ExaminationEntity examinationEntity = this.examinationRepository.findById(examinationId).orElseThrow(ExaminationNotFoundException::new);

        String name = this.authenticationFacade.getAuthentication().getName();
        UserEntity byUserName = this.userService.findByUserName(name);
        if (byUserName.getDoctorEntity() != null && byUserName.getDoctorEntity().getId() == examinationEntity.getDoctor().getId()) {
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
        if (examination.getProgression() != null && examination.getPrescription() != null) {
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
    public List<SetExaminationsForUserView> findAllExaminationsForThisUser(long id) {
        Set<ExaminationEntity> examinations;
        if (isUserDoctorTrueOrPatientFalse()) {
            examinations = this.examinationRepository.findAllByDoctorId(id);
        } else {
            examinations = this.examinationRepository.findAllByPatientId(id);
        }

        List<SetExaminationsForUserView> setExaminations = new ArrayList<>();
        for (ExaminationEntity examination : examinations) {
            SetExaminationsForUserView map = this.modelMapper.map(examination, SetExaminationsForUserView.class);
            map.setPhone(examination.getPatient().getPhone());
            map.setPatientName(examination.getPatient().getFullName());
            map.setPatientPhoto(examination.getPatient().getImageUrl());
            setExaminations.add(map);
        }

        return setExaminations;
    }

    @Override
    public ExaminationViewModel getExaminationView(long id) throws ExaminationNotFoundException {
        ExaminationEntity examinationEntity = this.examinationRepository.findById(id).orElseThrow(() -> new ExaminationNotFoundException("examination not found"));
        examinationEntity.getPatient().getId();

        ExaminationViewModel examinationViewModel = this.modelMapper.map(examinationEntity, ExaminationViewModel.class);
        examinationViewModel.setPatientId(examinationEntity.getPatient().getId());
        examinationViewModel.setDoctorId(examinationEntity.getDoctor().getId());

        return examinationViewModel;
    }


    //Examination creation
    @Override
    public ExaminationEntity addExamination(AddExaminationServiceModel examinationServiceModel) {
        ExaminationEntity examinationEntity = this.modelMapper.map(examinationServiceModel, ExaminationEntity.class);
        String[] split = examinationServiceModel.getDoctorName().split("-");
        DoctorEntity byIdEntity = this.doctorService.findByIdEntity(Long.valueOf(split[1]));
        if (!isUserDoctorTrueOrPatientFalse()) {

            ;
            UserEntity byUserName = this.userService.findByUserName(this.authenticationFacade.getAuthentication().getName());

            PatientEntity patientEntity = byUserName.getPatientEntity();
            examinationEntity.setPatient(patientEntity);
        }
        examinationEntity.setDoctor(byIdEntity);

        examinationEntity.setProgression(ProgressionEnum.BOOKED);
        return this.examinationRepository.save(examinationEntity);

    }

    @Override
    public void addLocationDetails(ExaminationByDoctorServiceModel examinationByDoctorServiceModel) throws ExaminationNotFoundException {
        ExaminationEntity examinationEntity = this.examinationRepository.findById(examinationByDoctorServiceModel.getExaminationId()).orElseThrow(() -> new ExaminationNotFoundException("Examination not found"));
        if (examinationEntity.getProgression() == ProgressionEnum.BOOKED) {
            examinationEntity.setProgression(ProgressionEnum.CURRENT);
        }
        if (examinationEntity.getLocation() != null) {
            LocationEntity locationEntity = examinationEntity.getLocation();
            locationEntity.setExactLocation(examinationByDoctorServiceModel.getExactLocation());
            locationEntity.setPartOfTheBody(examinationByDoctorServiceModel.getPartOfTheBody());
            locationEntity.setSideOfTheBody(examinationByDoctorServiceModel.getSideOfTheBody());
        } else {
            LocationEntity locationEntity = new LocationEntity(examinationByDoctorServiceModel.getPartOfTheBody(),
                    examinationByDoctorServiceModel.getSideOfTheBody(),
                    examinationByDoctorServiceModel.getExactLocation());
            examinationEntity.setLocation(locationEntity);
        }


        UserViewModel byUserNameView = this.userService.findByUserNameView(this.authenticationFacade.getAuthentication().getName());
        ;
        if (byUserNameView.getRoleId() == examinationEntity.getDoctor().getId()) {
            this.examinationRepository.save(examinationEntity);

        }
    }

    @Override
    public ArrayList<ExaminationViewModel> findExaminationFoThisPatient(long patientId) {
        Set<ExaminationEntity> allByPatientId = this.examinationRepository.findAllByPatientId(patientId);
        List<ExaminationViewModel> examinationViewModels = allByPatientId.
                stream().filter(examinationEntity -> examinationEntity.getLocation() != null).
                map(examinationEntity -> {


                    ExaminationViewModel examinationViewModel = modelMapper.map(examinationEntity, ExaminationViewModel.class);
                    LocationViewModel map = this.modelMapper.map(examinationEntity.getLocation(), LocationViewModel.class);
                    map.setPartOfTheBody(examinationEntity.getLocation().getPartOfTheBody());
                    map.setSideOfTheBody(examinationEntity.getLocation().getSideOfTheBody());

                    examinationViewModel.setLocation(map);

                    return examinationViewModel;
                }).
                collect(Collectors.toList());
        return (ArrayList) examinationViewModels;
    }

    @Override
    public ArrayList<ExaminationViewModel> findAllByPatientIdAndDepartment(long id, MedicalBranchesEnum name) {
        List<ExaminationEntity> allByPatientIdAndMedicalBranch = this.examinationRepository.
                findAllByPatientIdAndTypeOfBranch(id, name);
        List<ExaminationViewModel> examinationViewModels = allByPatientIdAndMedicalBranch.stream().
                map(examinationEntity -> modelMapper.map(examinationEntity, ExaminationViewModel.class)).
                collect(Collectors.toList());
        return (ArrayList<ExaminationViewModel>) examinationViewModels;
    }

    @Override
    public int countAll() {
        return (int) this.examinationRepository.count();
    }

    @Override
    public void deleteExamiantionsForDoctor(DoctorEntity doctorEntity) {
        Set<ExaminationEntity> allByDoctorId = this.examinationRepository.findAllByDoctorId(doctorEntity.getId());
        allByDoctorId.stream().forEach(examinationEntity -> this.examinationRepository.delete(examinationEntity));
    }

    @Override
    public ArrayList<SetExaminationsForUserView> getAll() {
        List<ExaminationEntity> examinations = this.examinationRepository.
                findAll();


        List<SetExaminationsForUserView> setExaminations = new ArrayList<>();
        for (ExaminationEntity examination : examinations) {
            SetExaminationsForUserView map = this.modelMapper.map(examination, SetExaminationsForUserView.class);
            map.setPhone(examination.getPatient().getPhone());
            map.setPatientName(examination.getPatient().getFullName());
            map.setPatientPhoto(examination.getPatient().getImageUrl());
            setExaminations.add(map);
        }


        return (ArrayList<SetExaminationsForUserView>) setExaminations;

    }

    @Override

    public void deleteExamination(long id) {

        this.examinationRepository.deleteById(id);
    }


    @Override
    public void addAdditionalData(AdditionalDataServiceModel additionalDataServiceModel) throws ExaminationNotFoundException, WrongDoctorException, IOException {

        LocationEntity locationEntity = new LocationEntity(additionalDataServiceModel.getPartOfTheBody(),
                additionalDataServiceModel.getSideOfTheBody(),
                additionalDataServiceModel.getExactLocation());
        AdditionalDataEntity additionalDataEntity = new AdditionalDataEntity();
        additionalDataEntity.setType(additionalDataServiceModel.getType());
        additionalDataEntity.setDate(additionalDataServiceModel.getDate());
        additionalDataEntity.setLocationEntity(locationEntity);
        if (additionalDataServiceModel.getFile() != null) {
            String fileLink = this.cloudinaryService.uploadImage(additionalDataServiceModel.getFile());
            additionalDataEntity.setFile(fileLink);
        }


        ExaminationEntity examinationEntity = this.examinationRepository.findById(additionalDataServiceModel.getExaminationId()).orElseThrow(ExaminationNotFoundException::new);

        if (this.userService.findByUserName(this.authenticationFacade.getAuthentication().getName()).getDoctorEntity().getId() == examinationEntity.getDoctor().getId()) {


            examinationEntity.getAdditionalData().add(additionalDataEntity);
            this.examinationRepository.save(examinationEntity);
        } else {
            throw new WrongDoctorException("Doctor who don't work on this examination can't add additional data");
        }

    }

    private boolean isUserDoctorTrueOrPatientFalse() {

        UserEntity byUserName = this.userService.findByUserName(this.authenticationFacade.getAuthentication().getName());
        if (byUserName.getDoctorEntity() != null) {
            return true;
        }
        return false;

    }
}
