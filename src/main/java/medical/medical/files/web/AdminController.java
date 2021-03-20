package medical.medical.files.web;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin")
@Controller
public class AdminController {

    @GetMapping("/home")
    private String adminHomePage(){


        return "admin-panel/admin-home";
    }
    @GetMapping("/reviews")
    private String adminReviews(){


        return "admin-panel/all-reviews";
    }
    @GetMapping("/examinations")
    private String adminRExaminations(){


        return "admin-panel/admin-all-examinations";
    }
}
