package spring1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring1.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
