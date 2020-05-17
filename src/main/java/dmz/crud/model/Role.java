package dmz.crud.model;


import com.fasterxml.jackson.annotation.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ROLES")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String role;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<User> users = new HashSet<>();

    public Role() {
    }

    public Role(Long id) {
        this.id = id;
    }

    public Role(String role) {
        this.role = role;
    }


    public Role(Long id, String nameRole) {
        this.id = id;
        this.role = nameRole;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return role;
    }

    @Override
    public String getAuthority() {
        return getRole();
    }
}