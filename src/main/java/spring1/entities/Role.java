package spring1.entities;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "t_role")
@Data
public class Role implements GrantedAuthority {

    @Id
    private Long id;

    @Size(min = 2, message = "No less than 2 chars")
    private String name;

    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public Role() {

    }

    public Role(long id, String role_name) {
        this.id = id;
        this.name = role_name;
    }

    @Override
    public String getAuthority() {
        return getName();
    }
}

