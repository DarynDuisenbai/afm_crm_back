package test.afm_crm.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import test.afm_crm.common.exception.NotFoundException;
import test.afm_crm.user.dto.UserResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserResponse::from)
                .toList();
    }

    public UserResponse getCurrentUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found: " + username));
        return UserResponse.from(user);
    }
}
