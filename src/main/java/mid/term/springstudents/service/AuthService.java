package mid.term.springstudents.service;

import mid.term.springstudents.model.User;
import mid.term.springstudents.repository.UserRepository;
import mid.term.springstudents.security.Request.RegisterRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String register(RegisterRequest registerRequest) {

        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new RuntimeException("Username is already taken");
        }
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email is already in use");
        }

        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());


        User user = new User(registerRequest.getUsername(), registerRequest.getEmail(), encodedPassword, "USER");        userRepository.save(user);

        return "User registered successfully";
    }
}