package medical.medical.files.web;

import medical.medical.files.config.validation.ValidationErrorConfigImpl;
import medical.medical.files.exeptions.ExaminationNotFoundException;
import medical.medical.files.model.bindingModels.FeedbackBindingModel;
import medical.medical.files.model.bindingModels.PatientBindingModel;
import medical.medical.files.model.serviceModels.FeedbackServiceModel;
import medical.medical.files.model.serviceModels.PatientServiceModel;
import medical.medical.files.model.viewModels.DiseaseViewModel;
import medical.medical.files.model.viewModels.ExaminationViewModel;
import medical.medical.files.model.viewModels.PatientViewModel;
import medical.medical.files.service.ExaminationService;
import medical.medical.files.service.PatientService;
import medical.medical.files.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
@RequestMapping("/patients")
public class PatientController {

    private static final String PATIENT_PROFILE = "patient-profile/";
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final PatientService patientService;
    private final ExaminationService examinationService;
    private final ValidationErrorConfigImpl validationConfig;

    public PatientController(ModelMapper modelMapper, UserService userService, PatientService patientService, ExaminationService examinationService, ValidationErrorConfigImpl validationConfig) {
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.patientService = patientService;
        this.examinationService = examinationService;
        this.validationConfig = validationConfig;
    }

    @PostMapping(value = "/create")
    private ResponseEntity<PatientBindingModel> createPatient(@Valid @RequestBody PatientBindingModel patientBindingModel, BindingResult bindingResult) {
//Working fine
        if (bindingResult.hasErrors()) {
            patientBindingModel.
                    setErrors(this.validationConfig.getValidationMessages(bindingResult));

            return new ResponseEntity(patientBindingModel, HttpStatus.BAD_REQUEST);
        }
        PatientServiceModel patientServiceModel = this.modelMapper.
                map(patientBindingModel, PatientServiceModel.class);
        this.userService.addPatient(patientServiceModel, patientBindingModel.getUserId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/update")
    private ResponseEntity<PatientBindingModel> updatePatient(@Valid @RequestBody PatientBindingModel patientBindingModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            patientBindingModel.
                    setErrors(this.validationConfig.getValidationMessages(bindingResult));

            return new ResponseEntity(patientBindingModel, HttpStatus.BAD_REQUEST);
        }
        PatientServiceModel patientServiceModel = this.modelMapper.
                map(patientBindingModel, PatientServiceModel.class);
        this.patientService.savePatient(patientServiceModel);
        return new ResponseEntity("Patient " + patientBindingModel.getFullName() + " is updated", HttpStatus.OK);
    }

    @GetMapping(value = "/{patientId}")
    private ResponseEntity<PatientViewModel> getPatientData(@PathVariable long patientId) {
        PatientViewModel byId = this.patientService.findById(patientId);
        return new ResponseEntity<>(byId, HttpStatus.OK);
        //Working OK
    }

    @GetMapping(value = "{patientId}/examination/{examinationId}")
    private ResponseEntity<ExaminationViewModel> getPatientExaminationById(@PathVariable long patientId, @PathVariable long examinationId) throws ExaminationNotFoundException {
//OK soso

        try {
            ExaminationViewModel patientExamination = this.patientService.getPatientExamination(patientId, examinationId);
            return new ResponseEntity<>(patientExamination, HttpStatus.OK);
        } catch (ExaminationNotFoundException e) {
            return new ResponseEntity(e.getStatusCode(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/id/diseases")
    private String getPatientDisease() {

        // TODO: 14-Mar-21
        return null;
        //OK soso

    }

    @GetMapping("/id/comments")
    private String getPatientComments() {

        // TODO: 14-Mar-21
        return PATIENT_PROFILE+"patient-comments";
        //OK soso

    }

    @GetMapping("/id/examinations")
    private String getPatientExaminations() {

        // TODO: 14-Mar-21
        return "doctor-profile/doctor-examinations";
        //OK soso

    }
    @GetMapping("/id/delete")
    private String deletePatient() {

        // TODO: 14-Mar-21
        return "redirect:/home";
        //OK soso

    } @PostMapping("/id/edit")
    private String editPatient() {

        // TODO: 14-Mar-21
        return "redirect:/home";
        //OK soso

    }

//    @PostMapping("/examination/{examinationId}/feedback")
//    private ResponseEntity<FeedbackBindingModel> commentExamination(@Valid @RequestBody FeedbackBindingModel feedbackBindingModel, BindingResult bindingResult,
//                                                                    @PathVariable long examinationId) throws ExaminationNotFoundException {
//        if (bindingResult.hasErrors()) {
//
//
//        }
//        FeedbackServiceModel feedbackServiceModel = this.modelMapper.map(feedbackBindingModel, FeedbackServiceModel.class);
//        FeedbackBindingModel addFeedback = this.examinationService.addFeedback(feedbackServiceModel, examinationId);
//        return new ResponseEntity<>(addFeedback, HttpStatus.OK);
//    }
    //Ok SO SO
}
