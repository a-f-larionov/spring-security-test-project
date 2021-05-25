package spring1.entities;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "t_user")
@Data
public class User implements UserDetails {

    /**
     * GenerationType.IDENTITY means
     * that the identifier will be generate by the database
     * AUTO - hibernate choose one of: TABLE, SEQUENCE, IDENTITY
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, message = "Not less then 5 symbols")
    private String username;

    @Size(min = 2, message = "Not less then 5 symbols")
    private String password;

    @Transient
    private String passwordConfirm;

    /**
     * FetchType.EAGER - means load force with user. no wating, no lazy loading
     */
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    public User() {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
