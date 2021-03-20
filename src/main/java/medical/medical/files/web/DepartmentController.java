package medical.medical.files.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/departments")
@Controller
public class DepartmentController {

    @GetMapping("/all")
    public String getAllDepartments() {
        return "hospital-departments/hospital-departments";
    }

    @GetMapping("/department/{id}")
    public String getAllDepartmentById(@PathVariable("id") long id, Model model) {
        return "hospital-departments/single-department";
    }
}
