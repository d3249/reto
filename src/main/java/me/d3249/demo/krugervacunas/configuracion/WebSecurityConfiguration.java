package me.d3249.demo.krugervacunas.configuracion;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final String usuario;
    private final String password;

    public WebSecurityConfiguration(@Value("${kruger.vacunas.usuario}") String usuario, @Value("${kruger.vacunas.password}") String password) {
        this.usuario = usuario;
        this.password = password;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //En producción se usaría un servidor de autorización, por ejemplo Keycloak
        auth.inMemoryAuthentication()
                .withUser(usuario).password(passwordEncoder().encode(password)).authorities("ADMINISTRADOR");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/api/inventario/**").hasAuthority("ADMINISTRADOR")
                // Suponiendo que este es un servicio público, los ciudadanos no necesitarán autenticarse para registrarse
                .antMatchers("/api/**").permitAll()
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
    }
}
