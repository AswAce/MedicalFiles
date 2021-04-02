package medical.medical.files.web;

import medical.medical.files.model.enteties.UserEntity;
import medical.medical.files.repositorie.RoleRepository;
import medical.medical.files.repositorie.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class AdminControllerTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MockMvc mockMvc;

    private long userId;

    @BeforeEach
    public void init() {
userRepository.deleteAll();
        UserEntity user1 = new UserEntity();
        user1.setUsername("admin");
        user1.setEmail("admin@.com");
        user1.setPassword("1234");

        UserEntity save = userRepository.save(user1);
        userId = save.getId();

    }

    @Test
    @WithMockUser(value = "admin", roles = {"USER", "ADMIN", "DOCTOR"})
    public void checkForCorrectStatusHomePageAdmin() throws Exception {


        mockMvc.perform(MockMvcRequestBuilders.
                get( "/admin/home", userId)).
                andExpect(status().isOk()).
                andExpect(view().
                        name("admin-panel/admin-home")).
                andExpect(model().attributeExists("allExamination")).
                andExpect(model().attributeExists("reviewsViews"));
    }
}
