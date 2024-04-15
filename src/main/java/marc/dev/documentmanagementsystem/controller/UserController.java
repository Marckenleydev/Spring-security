package marc.dev.documentmanagementsystem.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import marc.dev.documentmanagementsystem.domain.Response;
import marc.dev.documentmanagementsystem.dto.UserRequest;
import marc.dev.documentmanagementsystem.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


import static java.util.Collections.emptyMap;
import static marc.dev.documentmanagementsystem.utils.RequestUtils.getResponse;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/user")
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<Response> saveUser(@RequestBody @Valid UserRequest user, HttpServletRequest request){

        userService.createUser(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());
        return ResponseEntity.created(getUri( )).body(getResponse(request, emptyMap(), "Account created Check your email address to enable your account", CREATED));

    }

    @GetMapping("/verify/account")
    public ResponseEntity<Response> verifyUser(@RequestParam("token") String token, HttpServletRequest request){

        userService.verifyUserAccount(token);
        return ResponseEntity.ok().body(getResponse(request, emptyMap(), "Account verified", OK));


    }

//    @PostMapping("/login")
//    public ResponseEntity<?> testUser(@RequestBody  UserRequest user){
//        UsernamePasswordAuthenticationToken unauthenticated = UsernamePasswordAuthenticationToken.unauthenticated(user.getEmail(), user.getPassword());
//        Authentication authentication = authenticationManager.authenticate(unauthenticated);
//
//        return ResponseEntity.ok().body(Map.of(user, authentication));
//
//    }

    private URI getUri() {
        return URI.create("");
    }
}
