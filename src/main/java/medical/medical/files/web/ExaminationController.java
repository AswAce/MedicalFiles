package medical.medical.files.web;

import medical.medical.files.exeptions.ExaminationDoneExeption;
import medical.medical.files.exeptions.ExaminationNotFoundException;
import medical.medical.files.exeptions.WrongDoctorException;
import medical.medical.files.model.bindingModels.*;
import medical.medical.files.model.enums.ProgressionEnum;
import medical.medical.files.model.enums.SideOfTheBodyEnum;
import medical.medical.files.model.serviceModels.*;
import medical.medical.files.model.viewModels.ExaminationViewModel;
import medical.medical.files.model.viewModels.LocationViewModel;
import medical.medical.files.model.viewModels.PatientViewModel;
import medical.medical.files.service.DiseaseService;
import medical.medical.files.service.ExaminationService;
import medical.medical.files.service.PatientService;
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

@RequestMapping("/examinations")
@Controller
public class ExaminationController {
    private final ModelMapper modelMapper;
    private final PatientService patientService;
    private final ExaminationService examinationService;
    private final DiseaseService diseaseService;

    public ExaminationController(ModelMapper modelMapper, PatientService patientService, ExaminationService examinationService, DiseaseService diseaseService) {
        this.modelMapper = modelMapper;
        this.patientService = patientService;
        this.examinationService = examinationService;
        this.diseaseService = diseaseService;
    }

    @GetMapping("/add")
    private String addExamination(Model model) {
        if (!model.containsAttribute("addExamination")) {
            model.addAttribute("addExamination", new AddExaminationBindingModel());
        }
        return "examination/add-examination";
    }

    @PostMapping("/add")
    private String addExaminationPost(@Valid @ModelAttribute("addExamination") AddExaminationBindingModel addExaminationBindingModel
            , BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addExamination", addExaminationBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addExamination", bindingResult);

            return "redirect:/examinations/add";
        }
        String doctorName = addExaminationBindingModel.getDoctorName();
        boolean equals = doctorName.equals("Still no doctors for this department");

        if (doctorName.equals("Still no doctors for this department")) {
            redirectAttributes.addFlashAttribute("addExamination", addExaminationBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addExamination", bindingResult);
            redirectAttributes.addFlashAttribute("noDoc", true);
            return "redirect:/examinations/add";
        }
        AddExaminationServiceModel examinationServiceModel = this.modelMapper.map(addExaminationBindingModel, AddExaminationServiceModel.class);
        this.examinationService.addExamination(examinationServiceModel);


        return "redirect:/home";

    }


    @GetMapping("/examination/{id}")
    private String getExaminationViewById(Model model, @PathVariable long id) throws ExaminationNotFoundException {
        ExaminationViewModel examinationView = this.examinationService.getExaminationView(id);

        model.addAttribute("examinationDetails", examinationView);
        model.addAttribute("additionalDataArray", examinationView.getAdditionalData());
        if (examinationView.getLocation() != null) {
            String location = createLocation(examinationView.getLocation());
            model.addAttribute("location", location);
        }
        PatientViewModel patientViewModel = this.patientService.
                findById(examinationView.getPatientId());
        model.addAttribute("patientInfo", patientViewModel);

        model.addAttribute("patientDiseases", patientViewModel.getDiseaseViewModels());
        ArrayList<ExaminationViewModel> examinationFoThisPatient = this.examinationService.findExaminationFoThisPatient(examinationView.getPatientId());
        if (examinationFoThisPatient.size() >= 0 && examinationFoThisPatient.contains(examinationView)) {
            examinationFoThisPatient.remove(examinationView);
        }

        model.addAttribute("patientExaminations", examinationFoThisPatient);

        if (!model.containsAttribute("examinationByDoctorBindingModel")) {
            model.addAttribute("examinationByDoctorBindingModel", new
                    ExaminationByDoctorBindingModel());
        }
        return "examination/single-examination-view-doctor-part";
    }


    @PostMapping("/examination/{id}")
    private String getExaminationViewByIdPost(@PathVariable long id,
                                              @Valid @ModelAttribute("examinationByDoctorBindingModel")
                                                      ExaminationByDoctorBindingModel examinationByDoctorBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws IOException, ExaminationNotFoundException {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("examinationByDoctorBindingModel", examinationByDoctorBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.examinationByDoctorBindingModel", bindingResult);

            return "redirect:/examination/{id}";
        }
        ExaminationByDoctorServiceModel examinationByDoctorServiceModel = this.modelMapper.map(examinationByDoctorBindingModel, ExaminationByDoctorServiceModel.class);
        examinationByDoctorServiceModel.setExaminationId(id);
        this.examinationService.addLocationDetails(examinationByDoctorServiceModel);
        return "redirect:{id}";
    }


    @GetMapping("examination/{id}/add-additional-data")
    private String addAdditionalData(@PathVariable long id,

                                     Model model) throws ExaminationNotFoundException {
        ;
        model.addAttribute("doctorId", this.examinationService.getExaminationView(id).getDoctorId());
        model.addAttribute("examinationId", id);
        if (!model.containsAttribute("additionalDataBindingModel")) {
            model.addAttribute("additionalDataBindingModel",
                    new AddAdditionalDataBindingModel());
        }
//
        return "examination/add-additional-data";
    }

    @PostMapping("examination/{id}/add-additional-data")
    private String addAdditionalDataPost(@PathVariable long id,
                                         @Valid @ModelAttribute("additionalDataBindingModel") AddAdditionalDataBindingModel additionalDataBindingModel,
                                         BindingResult bindingResult,
                                         RedirectAttributes redirectAttributes
    ) throws ExaminationNotFoundException, WrongDoctorException, IOException {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("additionalDataBindingModel", additionalDataBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.additionalDataBindingModel", bindingResult);

            return "redirect:/examinations/examination/{id}/add-additional-data";

        }
        AdditionalDataServiceModel additionalDataServiceModel = this.modelMapper.
                map(additionalDataBindingModel, AdditionalDataServiceModel.class);
        additionalDataServiceModel.setExaminationId(id);
        this.examinationService.addAdditionalData(additionalDataServiceModel);

        return "redirect:/examinations/examination/{id}";

    }

    @GetMapping("/examination/{id}/add-prescription")
    private String addPrescription(@PathVariable long id,
                                   Model model) throws ExaminationNotFoundException {
        model.addAttribute("examinationId", id);
        model.addAttribute("pastPrescriptionExist", false);
        if (this.examinationService.getExaminationView(id).getPrescription() != null) {
            model.addAttribute("pastPrescriptionExist", true);
            model.addAttribute("pastPrescription", this.examinationService.getExaminationView(id).getPrescription());

        }

        model.addAttribute("doctorId", this.examinationService.getExaminationView(id).getDoctorId());
        if (!model.containsAttribute("addPrescriptionBindingModel")) {
            model.addAttribute("addPrescriptionBindingModel",
                    new AddPrescriptionBindingModel());
        }

        return "examination/add-prescription";
    }

    @PostMapping("/examination/{id}/add-prescription")
    private String addPrescriptionPost(@PathVariable long id,
                                       @Valid @ModelAttribute("addPrescriptionBindingModel") AddPrescriptionBindingModel addPrescriptionBindingModel,
                                       BindingResult bindingResult,
                                       RedirectAttributes redirectAttributes) throws
            ExaminationNotFoundException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addPrescriptionBindingModel", addPrescriptionBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addPrescriptionBindingModel", bindingResult);

            return "redirect:/examinations/examination/{id}/add-prescription";

        }
        AddPrescriptionServiceModel addPrescriptionServiceModel = this.modelMapper.map(addPrescriptionBindingModel, AddPrescriptionServiceModel.class);
        addPrescriptionServiceModel.setExaminationId(id);
        this.examinationService.addPrescription(addPrescriptionServiceModel);


        return "redirect:/examinations/examination/{id}/add-prescription";
    }

    @GetMapping("/examination/{id}/add-disease")
    private String addDiseaseForPatient(@PathVariable long id, Model model) {
        model.addAttribute("examinationId", id);


        if (!model.containsAttribute("addDisease")) {
            model.addAttribute("addDisease", new AddDiseaseBindingModel());
        }

        return "examination/add-disease";
    }

    @PostMapping("/examination/{id}/add-disease")
    private String addDiseaseForPatientPost(@PathVariable long id,
                                            @Valid @ModelAttribute("addDisease") AddDiseaseBindingModel addDiseaseBindingModel,
                                            BindingResult bindingResult,
                                            RedirectAttributes redirectAttributes) throws ExaminationNotFoundException, ExaminationDoneExeption {


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addDisease", addDiseaseBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addDisease", bindingResult);
            return "redirect:/examinations/examination/{id}/add-disease";
        }
        if (this.examinationService.getExaminationView(id).getProgressionEnum() != ProgressionEnum.DONE) {
            AddDiseaseServiceModel addDiseaseServiceModel = getDiseaseMap(addDiseaseBindingModel, id);
            this.patientService.addDiseaseToThePatient(addDiseaseServiceModel);
            return "redirect:/examinations/examination/{id}/add-prescription";
        }

        throw new ExaminationDoneExeption("Examination is done doc you can\t add disease to this patient");

    }

    @GetMapping("/examination/{id}/complete")
    public String completeExamination(@PathVariable long id) throws ExaminationNotFoundException {


        this.examinationService.completeExamination(id);
        return "redirect:/examinations/examination/{id}";
    }

    private AddDiseaseServiceModel getDiseaseMap(AddDiseaseBindingModel addDiseaseBindingModel, long id) throws ExaminationNotFoundException {
        AddDiseaseServiceModel addDiseaseServiceModel = this.modelMapper.map(addDiseaseBindingModel, AddDiseaseServiceModel.class);
        addDiseaseServiceModel.setChronic(setBooleanForDisease(addDiseaseBindingModel.getIsChronic()));
        addDiseaseServiceModel.setCurable(setBooleanForDisease(addDiseaseBindingModel.getIsCurable()));
        addDiseaseServiceModel.setPatientId(this.examinationService.getExaminationView(id).getPatientId());
        addDiseaseServiceModel.setExaminationId(id);
        return addDiseaseServiceModel;
    }


    private String createLocation(LocationViewModel location) {
        if (location.getSideOfTheBody() == SideOfTheBodyEnum.NONE) {
            if (location.getExactLocation().isEmpty()) {
                return "Location is: " + location.getPartOfTheBody().name().toLowerCase();
            } else {
                return "Location is: " + location.getPartOfTheBody().name().toLowerCase() + " details: " + location.getExactLocation();
            }

        } else if (location.getExactLocation().isEmpty()) {
            return "Location is: " + location.getSideOfTheBody().name().toLowerCase() + "  " + location.getPartOfTheBody().name().toLowerCase()

                    ;
        } else {
            return "Location is: " + location.getSideOfTheBody().name().toLowerCase() + "  " + location.getPartOfTheBody().name().toLowerCase() +

                    " details: " + location.getExactLocation();
        }
    }

    private boolean setBooleanForDisease(String bool) {
        if (bool == "true") {
            return true;
        }
        return false;
    }
}
//