package medical.medical.files.web;

import medical.medical.files.model.enums.RoleEnum;
import medical.medical.files.model.viewModels.UserViewPosition;
import medical.medical.files.security.UserDetailsSecurityService;
import medical.medical.files.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Controller
//Todo: add lert for login or registration into fragments.
public class HomeController {
    private static final String ROLE_PATIENT = "ROLE_PATIENT";
    private static final String ROLE_DOCTOR = "ROLE_DOCTOR";
    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index( ) {


        return "home";
    }

    @GetMapping("/home")
    public String home(@AuthenticationPrincipal UserDetails securityService) {
        if (securityService != null) {
            List<String> authorityList = getAuthorityList(securityService.getAuthorities());
            String redirect = redirectNewUsersToFinishAccount(securityService.getUsername(), authorityList);
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
        switch (newDoctorOrPatient) {
            case "doctor":
                return "redirect:doctors/create";

            case "patient":
                return "redirect:patients/create";
            case "notNew":
                return "home";
        }
        return "home";
    }


    private String isNewDoctorOrPatient(String username, List<String> authorities) {
        UserViewPosition byUsername = this.userService.findByUsername(username);
        if (byUsername == null) {
            return "notNew";
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
