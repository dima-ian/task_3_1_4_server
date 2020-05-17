package dmz.crud.service;


import dmz.crud.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    List<User> allUsers();

    ResponseEntity<User> add(User user, String role);

    void delete(Long id);

    void edit(User user, String role);

    User getById(Long id);

    boolean isByLogin(String email);

    User isByName(String name);
}
