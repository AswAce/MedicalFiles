package medical.medical.files.web;

import medical.medical.files.model.bindingModels.AddPatientBindingModel;
import medical.medical.files.model.serviceModels.PatientServiceModel;
import medical.medical.files.model.viewModels.PatientViewModel;
import medical.medical.files.model.viewModels.ReviewViewModel;
import medical.medical.files.model.viewModels.SetExaminationsForUserView;
import medical.medical.files.model.viewModels.UserViewModel;
import medical.medical.files.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/patients")
public class PatientController {

    private static final String PATIENT_PROFILE = "patient-profile/";
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final PatientService patientService;
    private final ExaminationService examinationService;
    private final ReviewService reviewService;


    public PatientController(ModelMapper modelMapper, UserService userService, PatientService patientService, ExaminationService examinationService, ReviewService reviewService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.patientService = patientService;
        this.examinationService = examinationService;


        this.reviewService = reviewService;
    }


    @GetMapping(value = "/patient/profile")
    private String patientProfile(Model model) {
        Authentication authentication = SecurityContextHolder.
                getContext().getAuthentication();
        String username = authentication.getName();
        UserViewModel byUserNameView = this.userService.findByUserNameView(username);
        model.addAttribute("user", byUserNameView);
        PatientViewModel byId = this.patientService.findById(byUserNameView.getRoleId());
        model.addAttribute("patient", byId);

        return "patient-profile/patient-profile";
    }

    @GetMapping(value = "/create")
    private String createPatient(Model model) {
        if (!model.containsAttribute("patientBindingModel")) {
            model.addAttribute("patientBindingModel", new AddPatientBindingModel());
        }

        return PATIENT_PROFILE + "add-patient-profile";
    }

    @PostMapping(value = "/create")
    private String createPatientPost(@Valid @ModelAttribute("patientBindingModel") AddPatientBindingModel patientBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws IOException {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("patientBindingModel", patientBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.patientBindingModel", bindingResult);

            return "redirect:/create";
        }

        PatientServiceModel patientServiceModel = this.modelMapper.map(patientBindingModel, PatientServiceModel.class);
        this.patientService.savePatient(patientServiceModel);
        return "redirect:/home";
    }

    @PostMapping("/update")
    private String updatePatient(@Valid @RequestBody AddPatientBindingModel patientBindingModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {


        }
        return null;
    }


    @GetMapping("/{id}/diseases")
    private String getPatientDisease(@PathVariable("id") long id, Model model) {
        PatientViewModel byId = this.patientService.findById(id);

        model.addAttribute("patientDiseases", byId.getDiseaseViewModels());

        return PATIENT_PROFILE + "patient-diseases";


    }

    @GetMapping("/{id}/comments")
    private String getPatientComments(@PathVariable("id") long id, Model model) {

        Set<ReviewViewModel> allByPatientId = this.reviewService.findAllByPatientId(id);

        model.addAttribute("reviews", allByPatientId);
        return PATIENT_PROFILE + "user-comments.html";
        //OK soso

    }

    @GetMapping("/{id}/examinations")
    private String getPatientExaminations(@PathVariable("id") long id, Model model) {
        List<SetExaminationsForUserView> examinationFoThisPatient = this.examinationService.findAllExaminationsForThisUser(id);
        model.addAttribute("examinations", examinationFoThisPatient);

        return "examination/user-examinations";

    }

    @GetMapping("/patient/{id}/delete")
    private String deletePatient(@PathVariable("id") long id) {

        this.userService.deleteUser(id);

        return "/";
        //OK soso

    }

    @PostMapping("/id/edit")
    private String editPatient() {

        // TODO: 14-Mar-21
        return "redirect:/home";
        //OK soso

    }


}
