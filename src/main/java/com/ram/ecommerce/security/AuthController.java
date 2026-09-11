package com.ram.ecommerce.security;

import jakarta.validation.constraints.NotBlank;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
 private final AuthenticationManager manager; private final JwtService jwt;
 public AuthController(AuthenticationManager manager,JwtService jwt){this.manager=manager;this.jwt=jwt;}
 public record LoginRequest(@NotBlank String username,@NotBlank String password){}
 public record TokenResponse(String accessToken){ }
 @PostMapping("/login") public TokenResponse login(@RequestBody LoginRequest request){manager.authenticate(new UsernamePasswordAuthenticationToken(request.username(),request.password()));return new TokenResponse(jwt.generate(request.username()));}
}
