package ch.noseryoung.websockets.domain.user;

import ch.noseryoung.websockets.core.execption.ResourceAlreadyExistsException;
import ch.noseryoung.websockets.core.generic.AbstractEntityServiceImpl;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserServiceImpl extends AbstractEntityServiceImpl<User> implements UserService {

    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository repository, Logger logger, BCryptPasswordEncoder passwordEncoder) {
        super(repository, logger);
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected User preCreate(User user) {
        if(!existsByUsername(user.getUsername())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            logger.debug("Encoded new {}'s password", singleEntity());
            return user;
        } else {
            throw new ResourceAlreadyExistsException();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("Attempting to load {} by username '{}'", singleEntity(), username);

        Optional<User> optional = ((UserRepository) repository).findByUsernameAndDeletedFalse(username);

        if(optional.isPresent()) {
            logger.debug("Loaded user");
            return new UserDetailsImpl(optional.get());
        } else {
            throw new NoSuchElementException();
        }
    }

    private boolean existsByUsername(String username) {
        return ((UserRepository) repository).existsByUsernameAndDeletedFalse(username);
    }
}
