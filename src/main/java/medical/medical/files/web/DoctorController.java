package medical.medical.files.web;

import medical.medical.files.config.currentUser.IAuthenticationFacade;
import medical.medical.files.model.bindingModels.AddDoctorProfileBindingModel;
import medical.medical.files.model.enums.MedicalBranchesEnum;
import medical.medical.files.model.serviceModels.AddDoctorProfileServiceModel;
import medical.medical.files.model.viewModels.*;
import medical.medical.files.service.DoctorService;
import medical.medical.files.service.ExaminationService;
import medical.medical.files.service.ReviewService;
import medical.medical.files.service.UserService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequestMapping("/doctors")
@Controller
public class DoctorController {
    private static final String DOCTOR_PROFILE_FOLDER = "doctor-profile/";
    private final ExaminationService examinationService;
    private final ModelMapper modelMapper;
    private final DoctorService doctorService;
    private final UserService userService;
    private final ReviewService reviewService;
    private final IAuthenticationFacade authenticationFacade;


    public DoctorController(ExaminationService examinationService, ModelMapper modelMapper, DoctorService doctorService, UserService userService, ReviewService reviewService, IAuthenticationFacade authenticationFacade) {
        this.examinationService = examinationService;
        this.modelMapper = modelMapper;
        this.doctorService = doctorService;
        this.userService = userService;
        this.reviewService = reviewService;
        this.authenticationFacade = authenticationFacade;
    }


    @GetMapping("/create")
    private String createDoctor(Model model) {
        if (!model.containsAttribute("addDoctorProfile")) {
            model.addAttribute("addDoctorProfile", new AddDoctorProfileBindingModel());
        }
        model.addAttribute("departments", MedicalBranchesEnum.values());
        return DOCTOR_PROFILE_FOLDER + "add-doctor-profile";
    }

    //// TODO: 21-Mar-21  finish picture to cloud and for user
    @PostMapping("/create")
    private String createDoctorPost(@Valid @ModelAttribute("addDoctorProfile") AddDoctorProfileBindingModel addDoctorProfileBindingModel
            , BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addDoctorProfile", addDoctorProfileBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addDoctorProfile", bindingResult);

            return "redirect:/doctors/create";
        }
        AddDoctorProfileServiceModel addDoctorProfileServiceModel = this.modelMapper.map(addDoctorProfileBindingModel, AddDoctorProfileServiceModel.class);
        addDoctorProfileServiceModel.setImg(addDoctorProfileBindingModel.getImg());
        this.doctorService.saveDoctorToProfile(addDoctorProfileServiceModel);

        return "redirect:/home";
    }

    @GetMapping("/all")
    private String getAllDoctors(Model model) {
        model.addAttribute("allDoctors", this.doctorService.getAll());

        return "hospital-doctors/doctors-list";
    }

    @GetMapping("/doctor/profile")
    private String getProfileDoctor(Model model) {
        Authentication authentication = SecurityContextHolder.
                getContext().getAuthentication();
        String username = authentication.getName();
        UserViewModel byUserNameView = this.userService.findByUserNameView(username);
        if (byUserNameView.getUsername().equals("admin")) {
            return "redirect:/admin/home";
        }
        model.addAttribute("user", byUserNameView);
        SingleDoctorView singleDoctorView = this.doctorService.findById(byUserNameView.getRoleId());
        model.addAttribute("doctor", singleDoctorView);
        model.addAttribute("monday", singleDoctorView.getWorkingDays().get("MONDAY"));
        model.addAttribute("tuesday", singleDoctorView.getWorkingDays().get("TUESDAY"));
        model.addAttribute("wednesday", singleDoctorView.getWorkingDays().get("WEDNESDAY"));
        model.addAttribute("thursday", singleDoctorView.getWorkingDays().get("THURSDAY"));
        model.addAttribute("friday", singleDoctorView.getWorkingDays().get("FRIDAY"));
        return "doctor-profile/doctor-profile";
    }


    @GetMapping("/doctor/{id}")
    private String getViewDoctorById(@PathVariable("id") long id, Model model) {
        SingleDoctorView byId = this.doctorService.findById(id);
        Set<ReviewViewModel> allForDepartments = this.reviewService.findAllByDoctorId(id);
        AllReviewStartView allReviewsForDoctor = this.reviewService.getAllReviewsForDoctor(id);
        byId.setId(id);
        byId.setReviews(allForDepartments.stream().limit(5).collect(Collectors.toSet()));
        model.addAttribute("rating", allReviewsForDoctor);
        model.addAttribute("doctor", byId);
        model.addAttribute("monday", byId.getWorkingDays().get("MONDAY"));
        model.addAttribute("tuesday", byId.getWorkingDays().get("TUESDAY"));
        model.addAttribute("wednesday", byId.getWorkingDays().get("WEDNESDAY"));
        model.addAttribute("thursday", byId.getWorkingDays().get("THURSDAY"));
        model.addAttribute("friday", byId.getWorkingDays().get("FRIDAY"));


        return "hospital-doctors/single-doctor";
    }

    @GetMapping("/doctor/my/examinations")
    private String getDoctorByIdExaminations(  Model model) {

        long roleId = getId();
        List<SetExaminationsForUserView> setExaminations = this.examinationService.findAllExaminationsForThisUser(roleId);
        model.addAttribute("examinations", setExaminations);


        return "examination/user-examinations";
    }



    @GetMapping("/doctor/my/reviews")
    private String getDoctorByIdReviews( Model model) {
        long roleId = getId();

        model.addAttribute("reviews", this.reviewService.findAllByDoctorId(roleId));
        return "patient-profile/user-comments";
    }

    @GetMapping("/doctor/Id/edit")
    private String getDoctorByIdEdit(Model model) {
//        @PathVariable("id") long id, IMPLEMENT
        //ToDo edid same page like doctor profile
        return DOCTOR_PROFILE_FOLDER + "doctor-profile";
    }

    @GetMapping("/doctor/my/delete")
    private String getDoctorByIdDelete() {
        long id = this.getId();
        ArrayList<ExaminationViewModel> byDoctorId = this.examinationService.findByDoctorId(id);
        byDoctorId.forEach(examinationViewModel ->
                this.examinationService.deleteExamination(examinationViewModel.getId()));
        this.userService.deleteDoctor(id);

        return "redirect:/home";
    }
    private long getId() {
        String name = this.authenticationFacade.getAuthentication().getName();
        return this.userService.findByUserNameView(name).getRoleId();

    }
}
