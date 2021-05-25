package spring1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import spring1.entities.Role;
import spring1.entities.User;
import spring1.repositories.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {


    @Autowired
    UserRepository userR;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @PersistenceContext
    private EntityManager em;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userR.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Use not found");
        }

        return user;
    }

    public User findUserById(Long userId) {
        Optional<User> userFromDb = userR.findById(userId);
        return userFromDb.orElse(new User());
    }

    public List<User> allUsers() {
        return userR.findAll();
    }

    public boolean createUser(User user) {
        User userFromDB = userR.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }

        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));

        user.setPassword(bCryptPasswordEncoder.encode(
                user.getPassword()
        ));

        userR.save(user);

        return true;
    }

    public boolean deleteUser(Long userId) {
        if (userR.findById(userId).isPresent()) {
            userR.deleteById(userId);
            return true;
        }
        return false;
    }

    public List<User> usergtList(Long idMain) {
        return em
                .createQuery(
                        "SELECT u FROM User u WHERE u.id > :paramId",
                        User.class)
                .setParameter("paramId", idMain)

                .getResultList();
    }


}
