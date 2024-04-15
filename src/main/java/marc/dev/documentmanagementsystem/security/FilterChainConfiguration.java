package marc.dev.documentmanagementsystem.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class FilterChainConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request->
                        request.requestMatchers("api/v1/user/login").permitAll()
                                .anyRequest().authenticated())
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService){
        var apiAuthenticationProvider = new ApiAuthenticationProvider(userDetailsService);
        return new ProviderManager(apiAuthenticationProvider);
    }

//    @Bean
//    public UserDetailsService userDetailsService(){
//        var marckenley = User.withDefaultPasswordEncoder()
//                .username("marckenley")
//                .password("{noop}petitphat1")
//                .roles("USER")
//                .build();
//
//        var hanna = User.withDefaultPasswordEncoder()
//                .username("hanna")
//                .password("{noop}petitphat1")
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(List.of(marckenley,hanna));
//    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        return  new InMemoryUserDetailsManager(
                User.withUsername("marckenley")
                .password("petitphat1")
                .roles("USER")
                .build(),


                User.withUsername("hanna")
                        .password("petitphat1")
                        .roles("USER")
                        .build()
        );
    }
}
