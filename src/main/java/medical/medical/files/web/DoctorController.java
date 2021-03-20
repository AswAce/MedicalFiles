package medical.medical.files.web;

import medical.medical.files.model.bindingModels.AddDoctorProfileBindingModel;
import medical.medical.files.model.enums.MedicalBranchesEnum;
import medical.medical.files.model.serviceModels.AddDoctorProfileServiceModel;
import medical.medical.files.service.DoctorService;
import medical.medical.files.service.ExaminationService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;

@RequestMapping("/doctors")
@Controller
public class DoctorController {
    private final ExaminationService examinationService;
    private final ModelMapper modelMapper;
    private final DoctorService doctorService;
    private static final String DOCTOR_PROFILE_FOLDER = "doctor-profile/";

    public DoctorController(ExaminationService examinationService, ModelMapper modelMapper, DoctorService doctorService) {
        this.examinationService = examinationService;
        this.modelMapper = modelMapper;
        this.doctorService = doctorService;
    }

    @GetMapping("/create")
    private String createDoctor(Model model) {
        if (!model.containsAttribute("addDoctorProfile")) {
            model.addAttribute("addDoctorProfile", new AddDoctorProfileBindingModel());
        }
        model.addAttribute("departments", MedicalBranchesEnum.values());
        return DOCTOR_PROFILE_FOLDER + "add-doctor-profile";
    }

    @PostMapping("/create")
    private String createDoctorPost(@Valid @ModelAttribute("addDoctorProfile") AddDoctorProfileBindingModel addDoctorProfileBindingModel
            , BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails principalUser) throws IOException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addDoctorProfile", addDoctorProfileBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addDoctorProfile", bindingResult);

            return "redirect:/doctors/create";
        }
        AddDoctorProfileServiceModel addDoctorProfileServiceModel = this.modelMapper.map(addDoctorProfileBindingModel, AddDoctorProfileServiceModel.class);
        this.doctorService.saveDoctorToProfile(addDoctorProfileServiceModel);

        return "redirect:home";
    }

    @GetMapping("/all")
    private String getAllDoctors() {


        return "hospital-doctors/doctors-list";
    }

    @GetMapping("/doctor/{id}")
    private String getAllDoctorById(Model model) {
//        @PathVariable("id") long id, IMPLEMENT
        return "hospital-doctors/single-doctor";
    }

    @GetMapping("/doctor/Id/examinations")
    private String getDoctorByIdExaminations(Model model) {
//        @PathVariable("id") long id, IMPLEMENT
        return DOCTOR_PROFILE_FOLDER + "doctor-examinations";
    }

    @GetMapping("/doctor/Id/comments")
    private String getDoctorByIdComments(Model model) {
//        @PathVariable("id") long id, IMPLEMENT
        return DOCTOR_PROFILE_FOLDER + "comments-for-doctor";
    }

    @GetMapping("/doctor/Id/edit")
    private String getDoctorByIdEdit(Model model) {
//        @PathVariable("id") long id, IMPLEMENT
        //ToDo edid same page like doctor profile
        return DOCTOR_PROFILE_FOLDER + "doctor-profile";
    }

    @GetMapping("/doctor/Id/delete")
    private String getDoctorByIdDelete(@PathVariable("id") long id) {
        return "home";
    }
}
