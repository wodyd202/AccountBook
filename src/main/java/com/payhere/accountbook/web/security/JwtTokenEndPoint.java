package com.payhere.accountbook.web.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class JwtTokenEndPoint {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenFactory jwtTokenFactory;

    @PostMapping("/oauth/token")
    public ResponseEntity<?> getToken(@Valid GetTokenReqeuest reqeuest, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(reqeuest.getUsername());
            if (notMatchPassword(reqeuest, userDetails)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            JwtToken jwtToken = jwtTokenFactory.createToken(userDetails);
            return ResponseEntity.ok(jwtToken.get());
        }catch (UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    private boolean notMatchPassword(GetTokenReqeuest reqeuest, UserDetails userDetails) {
        return !passwordEncoder.matches(reqeuest.getPassword(), userDetails.getPassword());
    }
}
