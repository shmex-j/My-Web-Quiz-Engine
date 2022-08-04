package engine.business;

import engine.business.exception.UserExistException;
import engine.business.exception.UserNotFound;
import engine.persistance.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = repository.findById(username).orElseThrow(UserNotFound::new);
        return new User(user.getEmail(), user.getPassword(), List.of(new SimpleGrantedAuthority("USER")));
    }

    public void addUser(engine.business.User user) {
        if (repository.existsById(user.getEmail())) {
            throw new UserExistException();
        } else {
            repository.save(user);
        }
    }
}
