package medical.medical.files.web;

import medical.medical.files.exeptions.ExaminationNotFoundException;
import medical.medical.files.model.bindingModels.AddReviewBindingModel;
import medical.medical.files.model.serviceModels.AddReviewServiceModel;
import medical.medical.files.model.viewModels.AllReviewStartView;
import medical.medical.files.model.viewModels.ReviewViewModel;
import medical.medical.files.model.viewModels.SingleDepartmentView;

import medical.medical.files.service.ReviewService;
import medical.medical.files.service.MedicalBranchesService;
import medical.medical.files.service.PatientService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;

import java.util.Set;
import java.util.stream.Collectors;

@RequestMapping("/departments")
@Controller
public class DepartmentController {
    private final MedicalBranchesService medicalBranchesService;
    private final PatientService patientService;
    private final ReviewService feedBackService;
    private final ModelMapper modelMapper;
    private final ReviewService reviewService;


    public DepartmentController(MedicalBranchesService medicalBranchesService, PatientService patientService, ReviewService feedBackService, ModelMapper modelMapper, ReviewService reviewService) {
        this.medicalBranchesService = medicalBranchesService;
        this.patientService = patientService;
        this.feedBackService = feedBackService;
        this.modelMapper = modelMapper;
        this.reviewService = reviewService;
    }

    @GetMapping("/all")
    private String getAllDepartments(Model model) {
        ;
        model.addAttribute("departments", this.medicalBranchesService.getAllDepartments());
        return "hospital-departments/hospital-departments";
    }

    @GetMapping("/department/{id}")
    private String getAllDepartmentById(@PathVariable("id") long id, Model model) {
        SingleDepartmentView byId = this.medicalBranchesService.findById(id);
        Set<ReviewViewModel> allForDepartments = this.reviewService.findAllForDepartments(byId.getName());
        byId.setReviews(allForDepartments.stream().limit(5).collect(Collectors.toSet()));
        boolean b = this.patientService.patientHasExaminationsInThisDepartment(byId.getName());
        Set<String> doctors = this.patientService.findPatientDoctorsForThisDepartment(byId.getName());
        AllReviewStartView allReviewStartView = this.reviewService.getAllReviewsRatingForDepartment(byId.getName());
        model.addAttribute("rating", allReviewStartView);
        model.addAttribute("doctors", doctors);
        model.addAttribute("department", byId);
        model.addAttribute("depId", id);
        model.addAttribute("canPatientAdd", b);
        if (!model.containsAttribute("review")) {
            model.addAttribute("review", new AddReviewBindingModel());
        }

        return "hospital-departments/single-department";
    }

    @PostMapping("/department/{id}")
    private String getAllDepartmentByIdPost(@PathVariable("id") long id, @Valid @ModelAttribute("review")
            AddReviewBindingModel addReviewBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws IOException, ExaminationNotFoundException {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("review", addReviewBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.review", bindingResult);

            return "redirect:/departments/department/{id}";
        }
        AddReviewServiceModel addReviewServiceModel = this.modelMapper.map(addReviewBindingModel, AddReviewServiceModel.class);
        SingleDepartmentView byId = this.medicalBranchesService.findById(id);
        addReviewServiceModel.setDepartment(byId.getName());
        this.feedBackService.addFeedback(addReviewServiceModel);


        return "redirect:/departments/department/{id}";
    }


}
