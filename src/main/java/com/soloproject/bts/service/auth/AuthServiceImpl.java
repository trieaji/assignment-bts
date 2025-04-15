package com.soloproject.bts.service.auth;

import com.soloproject.bts.dto.request.users.LoginRequest;
import com.soloproject.bts.dto.request.users.RegisterRequest;
import com.soloproject.bts.dto.response.users.RegisterResponse;
import com.soloproject.bts.dto.response.users.SignResponse;
import com.soloproject.bts.entity.Role;
import com.soloproject.bts.entity.Users;
import com.soloproject.bts.exception.ResponseHandler;
import com.soloproject.bts.repository.UsersRepository;
import com.soloproject.bts.security.jwt.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtService jwtService;


    @Override
    public ResponseEntity<Object> login(LoginRequest request) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        var data  = usersRepository.findByEmail(request.getEmail()).orElseThrow();
        String token = jwtService.generateToken(data);
        String refreshToken = jwtService.generateRefreshToken(data.getId());

        SignResponse response = new SignResponse(data.getEmail(),token,refreshToken);

        return ResponseHandler.generateResponseSuccess(response);
    }

    @Override
    public ResponseEntity<Object> register(RegisterRequest request) {
        ModelMapper modelMapper = new ModelMapper();
        Users usersMapper = modelMapper.map(request, Users.class);
        usersMapper.setId(UUID.randomUUID().toString());
        usersMapper.setRole(Role.USER);
        usersMapper.setFullName(request.getFullName());
        usersMapper.setPassword(passwordEncoder.encode(request.getPassword()));
        var data = usersRepository.save(usersMapper);

        String token = jwtService.generateToken(data);

        RegisterResponse response = new RegisterResponse();
        response.setEmail(data.getEmail());
        response.setToken(token);

        return ResponseHandler.generateResponseSuccess(response);

    }
}
