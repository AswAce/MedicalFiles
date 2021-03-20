package medical.medical.files.web;

import medical.medical.files.config.validation.ValidationErrorConfigImpl;
import medical.medical.files.model.bindingModels.UserRegisterBindingModel;
import medical.medical.files.model.enums.HospitalRoleEnum;
import medical.medical.files.model.enums.RoleEnum;
import medical.medical.files.model.serviceModels.UserServiceRegisterModel;
import medical.medical.files.model.viewModels.UserViewModel;
import medical.medical.files.service.UserService;
import org.dom4j.rule.Mode;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final String FOLDER = "register-login/";
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final ValidationErrorConfigImpl validationConfig;
    private static final String REGISTER_REDIRECT = "redirect:/users/register";


    public UserController(UserService userService, ModelMapper modelMapper, ValidationErrorConfigImpl validationConfig) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.validationConfig = validationConfig;
    }


    @GetMapping("/login")
    public String login() {
        return FOLDER+"page-login";
    }

    @GetMapping("/register")
    private String register(Model model) {

        if (!model.containsAttribute("registerUserBindingModel")) {
            model.addAttribute("registerUserBindingModel", new UserRegisterBindingModel());

//            model.addAttribute("userExist", false);
//            model.addAttribute("passwordDontMatch", false);
//
        }

        model.addAttribute("roles", HospitalRoleEnum.values());
        return FOLDER + "page-register";
    }

    @PostMapping("/register")
    private String registerPost(@Valid @ModelAttribute("registerUserBindingModel") UserRegisterBindingModel registerUserBindingModel
            , BindingResult bindingResult,
                                RedirectAttributes redirectAttributes
    ) {

        boolean exist = this.userService.userExist(registerUserBindingModel.getEmail(), registerUserBindingModel.getUsername());


        if (exist) {
            redirectAttributes.addFlashAttribute("registerUserBindingModel", registerUserBindingModel);

            redirectAttributes.addFlashAttribute("userExist", true);
            return REGISTER_REDIRECT;
        }
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("registerUserBindingModel", registerUserBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerUserBindingModel", bindingResult);
            System.out.println();
            return REGISTER_REDIRECT;
        }
        if (!registerUserBindingModel.passwordMatch()) {
            redirectAttributes.addFlashAttribute("registerUserBindingModel", registerUserBindingModel);

            redirectAttributes.addFlashAttribute("passwordDontMatch", true);

            return REGISTER_REDIRECT;
        }


        this.userService.saveUser(this.modelMapper.map(registerUserBindingModel, UserServiceRegisterModel.class));
        return "redirect:/users/login";
    }


    @PostMapping("/login-error")
    public ModelAndView failedLogin(@ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                                            String username) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("bad_credentials", true);
//        modelAndView.addObject("username", username);

        modelAndView.setViewName(FOLDER + "/page-login");

        return modelAndView;
    }

}
