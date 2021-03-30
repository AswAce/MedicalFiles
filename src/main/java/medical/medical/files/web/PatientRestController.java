package medical.medical.files.web;

import medical.medical.files.model.viewModels.BodyVIewModel;
import medical.medical.files.model.viewModels.ExaminationViewModel;
import medical.medical.files.model.viewModels.UserViewModel;
import medical.medical.files.service.ExaminationService;
import medical.medical.files.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/patient")
public class PatientRestController {
    private final ExaminationService examinationService;
    private final UserService userService;
//Todo fix pgotos and add the page with Body
    public PatientRestController(ExaminationService examinationService, UserService userService) {
        this.examinationService = examinationService;
        this.userService = userService;
    }

    @GetMapping("/body-examinations")
    public ResponseEntity<ArrayList<BodyVIewModel>> getAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ;
        UserViewModel byUserNameView = this.userService.findByUserNameView(authentication.getName());
        long r = byUserNameView.getRoleId();
        ArrayList<ExaminationViewModel> examinationFoThisPatient =
                this.examinationService.findExaminationFoThisPatient(r);

        ArrayList<BodyVIewModel> collect = (ArrayList<BodyVIewModel>) examinationFoThisPatient.stream().map(examinationViewModel -> {
            BodyVIewModel bodyVIewModel = new BodyVIewModel();
            bodyVIewModel.setPart(examinationViewModel.getLocation().getPartOfTheBody().name());
            bodyVIewModel.setSide(examinationViewModel.getLocation().getSideOfTheBody().name().toLowerCase());
            return bodyVIewModel;
        }).collect(Collectors.toList());
        return ResponseEntity.ok().body(collect);

    }
}
