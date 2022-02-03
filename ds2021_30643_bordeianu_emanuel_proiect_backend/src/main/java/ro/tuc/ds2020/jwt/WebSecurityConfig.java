package ro.tuc.ds2020.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    final JwtRequestFilter requestFilter;
    final JwtAuthenticationEntryPoint entryPoint;
    final JwtUserDetailsService userDetailsService;

    public WebSecurityConfig(JwtRequestFilter requestFilter, JwtAuthenticationEntryPoint entryPoint,
                             JwtUserDetailsService userDetailsService) {
        this.requestFilter = requestFilter;
        this.entryPoint = entryPoint;
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());

    }
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().and().csrf().disable().httpBasic().and().authorizeRequests()
                .antMatchers("/loginHessian/**", "/loginHessian", "/websocket/**/**", "/enablebroker/**/**/**",
                        "/authenticate", "/login/", "/logout/**", "/clients/**/**", "/admin/**/**",
                        "/devices/**/**/**", "/sensors/**/**/**", "/measurements/**/**/**",
                        "/", "/**", "/**/**",
                        "amqps://gdpinvdm:6CLno9oiZy3R0sLxCtXdczqg8lWugcyy@cow.rmq2.cloudamqp.com/gdpinvdm",
                        "measurementassignment2_queue", "measurementassignment2_exchange", "measurementassignment2_routingKey"
                ).permitAll()
                //.antMatchers("/clients/**/**").hasAnyRole()
                //.antMatchers("/admin/**/**").hasAnyRole()
                .anyRequest().denyAll()
                .and().exceptionHandling()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder(8);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

}
