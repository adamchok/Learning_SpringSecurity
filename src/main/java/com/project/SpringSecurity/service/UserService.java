package com.project.SpringSecurity.service;

import com.project.SpringSecurity.model.User;
import com.project.SpringSecurity.model.UserPrincipal;
import com.project.SpringSecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2A, 12);

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        System.out.println(username);
        System.out.println(userRepository.count());
        if (user == null) {
            System.out.println("User Not Found");
            throw new UsernameNotFoundException("User Not Found");
        }
        return new UserPrincipal(user);
    }

    public UUID createUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user).getId();
    }

    public List<User> getUsers() {
        //Sort sort = Sort.by(Sort.Order.asc("username"));
        //return userRepository.findAll(sort);
        return userRepository.findAllSortedByUsername();
    }


    public User getUserById(String id) {
        Optional<User> opt = userRepository.findById(UUID.fromString(id));
        return opt.orElse(null);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
