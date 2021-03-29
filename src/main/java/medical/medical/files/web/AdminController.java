package medical.medical.files.web;


import medical.medical.files.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
    private String adminHomePage(Model model) {
        model.addAttribute("examinations", this.examinationService.countAll());
        model.addAttribute("reviews", this.reviewService.countAll());
        model.addAttribute("doctors", this.doctorService.getCount());
        model.addAttribute("patients", this.patientService.countAll());
        model.addAttribute("reviewsViews", this.reviewService.findAll());

        model.addAttribute("allExamination",this.examinationService.getAll());
        return "admin-panel/admin-home";
    }

    @GetMapping("/reviews")
    private String adminReviews() {


        return "admin-panel/all-reviews";
    }

    @GetMapping("/examinations")
    private String adminRExaminations() {


        return "admin-panel/admin-all-examinations";
    }


    @GetMapping("/delete/review/{id}")
    private String deleteReview(@PathVariable("id") long id) {
        this.reviewService.deleteReview(id);

        return "redirect:/home";
    }

    @GetMapping("/delete/user/{id}")
    private String deleteDoctorUser(@PathVariable("id") long id) {
       this.userService.deleteDoctor(id);

        return "redirect:/home";
    }

}
