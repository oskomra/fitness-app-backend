package osk.sko.FitnessApp.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import osk.sko.FitnessApp.exception.ResourceNotFoundException;
import osk.sko.FitnessApp.exception.UnauthorizedAccessException;
import osk.sko.FitnessApp.user.model.User;
import osk.sko.FitnessApp.user.model.UserPrinciple;
import osk.sko.FitnessApp.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository
                .findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Not found"));

        return new UserPrinciple(user) {
        };
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {
            throw new UnauthorizedAccessException("User is not authenticated");
        }

        String email = authentication.getName();
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
