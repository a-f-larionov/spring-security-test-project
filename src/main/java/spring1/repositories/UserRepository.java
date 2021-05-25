package spring1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring1.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
