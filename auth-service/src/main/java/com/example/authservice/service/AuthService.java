package com.example.authservice.service;

import com.example.authservice.dto.LoginInput;
import com.example.authservice.dto.LoginResponseDTO;
import com.example.authservice.dto.RegisterInput;
import com.example.authservice.exception.EmailAlreadyExistsException;
import com.example.authservice.exception.UserNotFoundException;
import com.example.authservice.exception.WrongDataException;
import com.example.authservice.messaging.event.UserCreatedEvent;
import com.example.authservice.messaging.publisher.UserEventPublisher;
import com.example.authservice.model.User;
import com.example.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserEventPublisher eventPublisher;


    public LoginResponseDTO login(LoginInput dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new WrongDataException("Contraseña incorrecta");
        }

        String token = jwtService.generateToken(user);

        return new LoginResponseDTO(user, token);
    }

    public LoginResponseDTO register(RegisterInput dto) {

        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email ya existe");
        }

        String hashedPassword = passwordEncoder.encode(dto.getPassword());

        User user = userRepository.save(new User(dto.getName(), dto.getEmail(), hashedPassword));

        eventPublisher.publishUserCreated(
                new UserCreatedEvent(user.getId(), user.getEmail(), user.getName())
        );

        String token = jwtService.generateToken(user);

        return new LoginResponseDTO(user, token);
    }

}