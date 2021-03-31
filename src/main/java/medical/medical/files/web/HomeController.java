package medical.medical.files.web;

import medical.medical.files.model.viewModels.DoctorSetViewModel;
import medical.medical.files.model.viewModels.UserViewModel;
import medical.medical.files.model.viewModels.UserViewPosition;
import medical.medical.files.service.CarouselService;
import medical.medical.files.service.DoctorService;
import medical.medical.files.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
//Todo: add lert for login or registration into fragments.
public class HomeController {
    private static final String ROLE_PATIENT = "ROLE_PATIENT";
    private static final String ROLE_DOCTOR = "ROLE_DOCTOR";
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private final UserService userService;
    private final CarouselService carouselService;
    private final DoctorService doctorService;

    public HomeController(UserService userService, CarouselService carouselService, DoctorService doctorService) {
        this.userService = userService;
        this.carouselService = carouselService;
        this.doctorService = doctorService;
    }



    @GetMapping("/")
    public String index(Model model) {
        ArrayList<DoctorSetViewModel> carouselDoctors = (ArrayList<DoctorSetViewModel>) this.carouselService.getCarouselDoctors();
        model.addAttribute("doctorsCarousel", carouselDoctors);
        return "home";
    }

    @GetMapping("/home")
    public String home(@AuthenticationPrincipal UserDetails securityService, Model model) {
        if (this.doctorService.getCount()
                > 0) {
            ArrayList<DoctorSetViewModel> carouselDoctors = (ArrayList<DoctorSetViewModel>) this.carouselService.getCarouselDoctors();

            model.addAttribute("doctorsCarousel", carouselDoctors);
        }

        if (securityService != null) {
            String username = securityService.getUsername();
            UserViewModel byUserNameView = this.userService.findByUserNameView(username);
            model.addAttribute("user", byUserNameView);
            if (username == "admin") {
                return "redirect:/admin-panel/admin-home";
            }
            if (byUserNameView.getRole() != null && byUserNameView.getRole().equals("doctor")) {
                model.addAttribute("isDoctor", true);
            } else if ( byUserNameView.getRole() != null && byUserNameView.getRole().equals("patient")) {
                model.addAttribute("isDoctor", false);
            }
            List<String> authorityList = getAuthorityList(securityService.getAuthorities());
            String redirect = redirectNewUsersToFinishAccount(username, authorityList);
            return redirect;
        }

        return "home";
    }


    @GetMapping("/contact-us")
    public String contactUs() {

        return "contact-us";
    }


    private String redirectNewUsersToFinishAccount(String username, List<String> authorities) {

        String newDoctorOrPatient = isNewDoctorOrPatient(username, authorities);
        if (username == "admin") {
            return "redirect:/admin/home";
        }
        switch (newDoctorOrPatient) {
            case "admin":
                return "redirect:/admin/home";

            case "notNew":
                return "home";
            case "doctor":
                return "redirect:doctors/create";

            case "patient":
                return "redirect:patients/create";

        }
        return "home";
    }


    private String isNewDoctorOrPatient(String username, List<String> authorities) {
        UserViewPosition byUsername = this.userService.findByUserNameForDoctorPatientFields(username);
        if (byUsername == null) {
            return "notNew";
        }
        if (!byUsername.isDoctor() && authorities.contains(ROLE_ADMIN)) {
            return "admin";
        }
        if (!byUsername.isDoctor() && authorities.contains(ROLE_DOCTOR)) {
            return "doctor";
        }
        if (!byUsername.isPatient() && authorities.contains(ROLE_PATIENT)) {
            return "patient";
        } else return "notNew";

    }

    private List<String> getAuthorityList(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream().map(grantedAuthority -> grantedAuthority.toString()).collect(Collectors.toList());

    }

}
