package br.com.alphacoach.app.config;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public final SecurityFilter securityFilter;

    public SecurityConfig(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        return http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configure(http))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auhtorize -> auhtorize
                        .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/alunos").hasRole(UserTypes.PROFESSOR.toString())
//                        .requestMatchers(HttpMethod.GET,"/alunos/{id}").hasRole(UserTypes.PROFESSOR.toString())
//                        .requestMatchers(HttpMethod.GET, "/alunos").hasRole(UserTypes.PROFESSOR.toString())
//                        .requestMatchers(HttpMethod.PATCH,"/alunos/{id}").hasRole(UserTypes.PROFESSOR.toString())
//                        .requestMatchers(HttpMethod.PATCH, "/alunos/{id}/remover").hasRole(UserTypes.PROFESSOR.toString())
//                        .requestMatchers(HttpMethod.POST, "/agendatreino").hasRole(UserTypes.PROFESSOR.toString())
//                        .requestMatchers(HttpMethod.GET,"/agendatreino/{id}").hasRole(UserTypes.PROFESSOR.toString())
//                        .requestMatchers(HttpMethod.GET, "/agendatreino").hasRole(UserTypes.PROFESSOR.toString())
//                        .requestMatchers(HttpMethod.PATCH,"/agendatreino/{id}").hasRole(UserTypes.PROFESSOR.toString())
//                        .requestMatchers(HttpMethod.PATCH, "/agendatreino/{id}/checkin").hasRole(UserTypes.PROFESSOR.toString())
//                        .requestMatchers(HttpMethod.POST, "/exercicios").hasRole(UserTypes.PROFESSOR.toString())
//                        .requestMatchers(HttpMethod.GET,"/exercicios/{id}").hasRole(UserTypes.PROFESSOR.toString())
//                        .requestMatchers(HttpMethod.GET, "/exercicios").hasRole(UserTypes.PROFESSOR.toString())
//                        .requestMatchers(HttpMethod.PATCH,"/exercicios/{id}").hasRole(UserTypes.PROFESSOR.toString())
//                        .requestMatchers(HttpMethod.PATCH, "/exercicios/{id}/remover").hasRole(UserTypes.PROFESSOR.toString())
//                        .requestMatchers(HttpMethod.POST, "/planos").hasRole(UserTypes.PROFESSOR.toString())
//                        .requestMatchers(HttpMethod.GET,"/planos/{id}").hasRole(UserTypes.PROFESSOR.toString())
//                        .requestMatchers(HttpMethod.GET, "/planos").hasRole(UserTypes.PROFESSOR.toString())
//                        .requestMatchers(HttpMethod.PATCH,"/planos/{id}").hasRole(UserTypes.PROFESSOR.toString())
//                        .requestMatchers(HttpMethod.DELETE, "/planos/{id}").hasRole(UserTypes.PROFESSOR.toString())
//                        .requestMatchers(HttpMethod.POST, "/pagamento").hasRole(UserTypes.PROFESSOR.toString())
//                        .requestMatchers(HttpMethod.GET,"/pagamento/{id}").hasRole(UserTypes.PROFESSOR.toString())
//                        .requestMatchers(HttpMethod.GET, "/pagamento").hasRole(UserTypes.PROFESSOR.toString())
//                        .requestMatchers(HttpMethod.PATCH,"/pagamento/{id}").hasRole(UserTypes.PROFESSOR.toString())
//                        .requestMatchers(HttpMethod.DELETE, "/pagamento/{id}").hasRole(UserTypes.PROFESSOR.toString())
//                        .requestMatchers(HttpMethod.POST, "/treinos").hasRole(UserTypes.PROFESSOR.toString())
//                        .requestMatchers(HttpMethod.GET,"/treinos/{id}").hasRole(UserTypes.PROFESSOR.toString())
//                        .requestMatchers(HttpMethod.GET, "/treinos").hasRole(UserTypes.PROFESSOR.toString())
//                        .requestMatchers(HttpMethod.PATCH,"/treinos/{id}").hasRole(UserTypes.PROFESSOR.toString())
//                        .requestMatchers(HttpMethod.DELETE, "/treinos/{id}").hasRole(UserTypes.PROFESSOR.toString())
//                        .requestMatchers(HttpMethod.GET,"/exerciciotreino/{id}").hasRole(UserTypes.PROFESSOR.toString())
//                        .requestMatchers(HttpMethod.GET, "/exerciciotreino").hasRole(UserTypes.PROFESSOR.toString())
//                        .requestMatchers(HttpMethod.PATCH,"/exerciciotreino/{id}").hasRole(UserTypes.PROFESSOR.toString())
//                        .requestMatchers(HttpMethod.DELETE, "/exerciciotreino/{id}").hasRole(UserTypes.PROFESSOR.toString())
                        .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
