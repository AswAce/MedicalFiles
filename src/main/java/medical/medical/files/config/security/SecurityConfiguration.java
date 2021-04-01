package medical.medical.files.config.security;

import medical.medical.files.security.UserDetailsSecurityService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final UserDetailsSecurityService userDetailsSecurityService;
    //Изваждаме си юзера който ще рпверяваме
    private final PasswordEncoder passwordEncoder;

    public SecurityConfiguration(UserDetailsSecurityService userDetailsSecurityService, PasswordEncoder passwordEncoder) {
        this.userDetailsSecurityService = userDetailsSecurityService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsSecurityService).passwordEncoder(passwordEncoder);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests().
                // allow access to static resources to anyone
                        requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll().
                antMatchers("/", "/users/login", "/users/register", "/home").permitAll().
                antMatchers("/departments/all", "/departments/department/*/", "/doctors/all", "/doctors/doctor/*/").permitAll().
                // allow access to index, user login and registration to anyone
                        antMatchers("/doctors/create", "/patients/create").authenticated().
                antMatchers("/patients/patient/profile",
                        "/patients/*/",
                        "/patients/*/diseases",
                        "/patients/*/comments",
                        "/patients/*/examinations"
                ).hasRole("PATIENT").

                antMatchers(
                        "/doctors/doctor/profile",
                        "/examinations/examination/*/add-additional-data",
                        "/examinations/examination/*/add-disease",
                        "/doctors/doctor/*/examinations",
                        "/doctors/doctor/*/reviews").hasRole("DOCTOR").

                antMatchers("/admin/**","/doctor/*/delete").hasRole("ADMIN").
//    /departments/department/*  permitAll(). трябва да е за всички а не работи  /doctors/doctor/* е същото но работи..

                //трябва да е позволено само за логнат Doctor
// http://localhost:8080/doctors/doctor/1/reviews -
                //http://localhost:8080/doctors/doctor/1/examinations


                        and().
                // configure login with HTML form
                        formLogin().
                // our login page will be served by the controller with mapping /users/login
                        loginPage("/users/login").
                // the name of the user name input field in OUR login form is username (optional)
                        usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY).
                // the name of the user password input field in OUR login form is password (optional)
                        passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY).
                // on login success redirect here
                        defaultSuccessUrl("/home").
                // on login failure redirect here
                        failureForwardUrl("/users/login-error").
                and().
                logout().
                // which endpoint performs logout, e.g. http://localhost:8080/logout (!this should be POST request)
                        logoutUrl("/logout").
                // where to land after logout
                        logoutSuccessUrl("/").
                // remove the session from the server
                        invalidateHttpSession(true).
                // delete the session cookie
                        deleteCookies("JSESSIONID")
                .and()
                .rememberMe().key(System.getenv("REMEMBER_ME")).tokenValiditySeconds(86400);//bye! :-)

    }

}
