package medical.medical.files.web;


import medical.medical.files.model.viewModels.PatientViewModel;
import medical.medical.files.model.viewModels.ReviewViewModel;
import medical.medical.files.model.viewModels.SetExaminationsForUserView;
import medical.medical.files.service.ExaminationService;
import medical.medical.files.service.PatientService;
import medical.medical.files.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Set;

@RestController
@RequestMapping("/admin")
public class AdminRestControllers {

    private final ReviewService reviewService;
    private final ExaminationService examinationService;
    private final PatientService patientService;

    public AdminRestControllers(ReviewService reviewService, ExaminationService examinationService, PatientService patientService) {
        this.reviewService = reviewService;
        this.examinationService = examinationService;
        this.patientService = patientService;

    }


    @GetMapping("/allReviews")
    public ResponseEntity<Set<ReviewViewModel>> getAllReviews() {
        return ResponseEntity.ok().body(this.reviewService.findAll());

    }

    @GetMapping("/allExaminations")
    public ResponseEntity<ArrayList<SetExaminationsForUserView>> getAll() {
        return ResponseEntity.ok().body(this.examinationService.getAll());

    }

    @GetMapping("/allPatients")
    public ResponseEntity<ArrayList<PatientViewModel>> getAllPatients() {
        return ResponseEntity.ok().body(this.patientService.getAll());

    }
}
