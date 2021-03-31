package medical.medical.files.web;

import medical.medical.files.model.viewModels.DoctorSetViewModel;
import medical.medical.files.service.DoctorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/doctors")
public class DoctorRestController {
    private final DoctorService doctorService;

    public DoctorRestController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/allRest")
    public ResponseEntity<Set<DoctorSetViewModel>> getAll() {
        return ResponseEntity.ok().body(this.doctorService.getAll());

    }

}
