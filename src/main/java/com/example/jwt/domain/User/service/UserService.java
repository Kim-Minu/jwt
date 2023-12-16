package com.example.jwt.domain.User.service;

import com.example.jwt.domain.User.domain.User;
import com.example.jwt.domain.User.domain.UserRepository;
import com.example.jwt.domain.User.dto.SignUpRequestDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    @Transactional
    public void signUp(SignUpRequestDto requestDto) {

        User user = SignUpRequestDto.toEntity(requestDto);
        userRepository.save(user);
    }

    public void signIn() {

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException(email));

    }
}
