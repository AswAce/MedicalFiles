package medical.medical.files.web;


import medical.medical.files.model.viewModels.ExaminationViewModel;
import medical.medical.files.model.viewModels.ReviewViewModel;
import medical.medical.files.model.viewModels.SetExaminationsForUserView;
import medical.medical.files.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RequestMapping("/admin")
@Controller
public class AdminController {
    private final ExaminationService examinationService;
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final ReviewService reviewService;
    private final UserService userService;

    public AdminController(ExaminationService examinationService, PatientService patientService, DoctorService doctorService, ReviewService reviewService, UserService userService) {
        this.examinationService = examinationService;
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.reviewService = reviewService;

        this.userService = userService;
    }


    @GetMapping("/home")
    public String adminHomePage(Model model) {
        model.addAttribute("examinations", this.examinationService.countAll());
        model.addAttribute("reviews", this.reviewService.countAll());
        model.addAttribute("doctors", this.doctorService.getCount());
        model.addAttribute("patients", this.patientService.countAll());
        model.addAttribute("reviewsViews", this.reviewService.findAll());

        model.addAttribute("allExamination", this.examinationService.getAll());
        return "admin-panel/admin-home";
    }

    @GetMapping("/patients")
    public String adminReviews() {


        return "admin-panel/all-patients";
    }


    @GetMapping("/delete/review/{id}")
    public String deleteReview(@PathVariable("id") long id) {
        this.reviewService.deleteReview(id);

        return "redirect:/home";
    }

    @GetMapping("/delete/doctor/{id}")
    public String deleteDoctorUser(@PathVariable("id") long id) {
        ArrayList<ExaminationViewModel> byDoctorId = this.examinationService.findByDoctorId(id);
        byDoctorId.forEach(examinationViewModel ->
                this.examinationService.deleteExamination(examinationViewModel.getId()));
        this.userService.deleteDoctor(id);

        return "redirect:/home";
    }

    @GetMapping("delete-patient/{id}")
    public String deletePatient(@PathVariable("id") long id) {


        ArrayList<ExaminationViewModel> byUserId = this.examinationService.findExaminationFoThisPatient(id);
        byUserId.forEach(examinationViewModel ->
                this.examinationService.deleteExamination(examinationViewModel.getId()));

        Set<ReviewViewModel> allByPatientId = this.reviewService.findAllByPatientId(id);
        allByPatientId.
                forEach(reviewViewModel -> this.reviewService.deleteReview(reviewViewModel.getId()));
        userService.deletePatient(id);
        return "redirect:/home";
    }

}
