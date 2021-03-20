package medical.medical.files.web;

import medical.medical.files.exeptions.ExaminationNotFoundException;
import medical.medical.files.model.bindingModels.AddExaminationBindingModel;
import medical.medical.files.model.bindingModels.AddAdditionalDataBindingModel;
import medical.medical.files.model.bindingModels.ExaminationByDoctorBindingModel;
import medical.medical.files.model.bindingModels.PrescriptionBindingModel;
import medical.medical.files.model.serviceModels.AddExaminationServiceModel;
import medical.medical.files.model.serviceModels.AdditionalDataServiceModel;
import medical.medical.files.model.serviceModels.ExaminationByDoctorServiceModel;
import medical.medical.files.model.serviceModels.PrescriptionServiceModel;
import medical.medical.files.service.ExaminationService;
import medical.medical.files.service.PatientService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/examinations")
@Controller
public class ExaminationController {
    private final ModelMapper modelMapper;
    private final PatientService patientService;
    private final ExaminationService examinationService;

    public ExaminationController(ModelMapper modelMapper, PatientService patientService, ExaminationService examinationService) {
        this.modelMapper = modelMapper;
        this.patientService = patientService;
        this.examinationService = examinationService;
    }
    @GetMapping("/add")
    private String addExamination(Model model){


        return "examination/add-examination";
    }
    @PostMapping("/add")
    private void addExaminationPost(@RequestBody AddExaminationBindingModel addExaminationBindingModel) {
        AddExaminationServiceModel examinationServiceModel = this.modelMapper.map(addExaminationBindingModel, AddExaminationServiceModel.class);
        this.patientService.addExaminationToThePatient(examinationServiceModel);
  //ToDo make the logic to add examination
    }



    @GetMapping("/edit/{id}")
    private void editExamination(@RequestBody AddExaminationBindingModel addExaminationBindingModel, Long examinationId) {
        AddExaminationServiceModel examinationServiceModel = this.modelMapper.
                map(addExaminationBindingModel, AddExaminationServiceModel.class);

        this.examinationService.editExamination(examinationServiceModel, examinationId);
    }

    private void deleteExamination(long examinationId) {
        this.examinationService.deleteExamination(examinationId);
    }

    private void doctorEditExamination(@RequestBody
                                               ExaminationByDoctorBindingModel examinationByDoctorBindingModel, long examinationId) throws ExaminationNotFoundException {
        ExaminationByDoctorServiceModel examinationByDoctorServiceModel = this.
                modelMapper.map(examinationByDoctorBindingModel,
                ExaminationByDoctorServiceModel.class);

        this.examinationService.addDoctorPart(examinationByDoctorServiceModel, examinationId);
    }

    public void addPrescription(@RequestBody PrescriptionBindingModel prescriptionBindingModel, long examinationId) throws ExaminationNotFoundException {
        PrescriptionServiceModel prescriptionServiceModel = this.modelMapper.map(prescriptionBindingModel, PrescriptionServiceModel.class);

        this.examinationService.addPrescription(prescriptionServiceModel, examinationId);
    }

    @GetMapping("examination/{id}/add-additional-data")
    public void addAdditionalData(@RequestBody AddAdditionalDataBindingModel additionalDataBindingModel, long examinationId) throws ExaminationNotFoundException {

        AdditionalDataServiceModel additionalDataServiceModel = this.modelMapper.
                map(additionalDataBindingModel, AdditionalDataServiceModel.class);

        this.examinationService.addAdditionalData(additionalDataServiceModel, examinationId);

    }

    public  void completeExamination(long doctorId,long examinationId) throws ExaminationNotFoundException {

        this.examinationService.completeExamination(doctorId,examinationId);
    }
    @GetMapping("/examination/Id")
    private String getExaminationViewById(){

        return  "examination/single-examination-view";
    }
}
