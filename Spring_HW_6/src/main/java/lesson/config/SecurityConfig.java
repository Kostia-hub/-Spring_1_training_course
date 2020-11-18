package lesson.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import lesson.bootstrap.DataLoader;
import lesson.domain.Role;
import lesson.service.UserService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserService userService;

    @Autowired
    public void setUserDetailsService(UserService userService) {
        this.userService = userService;
    }

/*    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception { //Задается, откуда берутся пользователи
        auth.inMemoryAuthentication()
                .withUser(DataLoader.USER.getName())
                .password(DataLoader.USER.getPassword())
                .roles(DataLoader.USER.getRole().name())
                .and()
                .withUser(DataLoader.ADMIN.getName())
                .password(DataLoader.ADMIN.getPassword())
                .roles(DataLoader.ADMIN.getRole().name());
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests() //Этот медод позволяет нам повесить проверки
                .antMatchers("/").permitAll()//сюда разрешено всем
                .antMatchers("/new","/editProduct").hasAnyAuthority(Role.ADMIN.name(),Role.MANAGER.name())
                .antMatchers("/users","/users/**").hasAuthority(Role.ADMIN.name())//сюда можно тем, у кого роль Админ
                .anyRequest().authenticated()
                .and()
                .formLogin() //добавили форму логина
                .loginPage("/login") //добавили, по какому пути лежит логин
                .loginProcessingUrl("/auth")
                .permitAll() // форма Логин разрешена всем
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // добавили форму "Выход"
                .logoutSuccessUrl("/").deleteCookies("JSESSIONID") //после выхода переход на стартовую страницу, чистятся куки
                .invalidateHttpSession(true)
                .and()
                .csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setPasswordEncoder(passwordEncoder());
        auth.setUserDetailsService(userService);
        return auth;
    }
}