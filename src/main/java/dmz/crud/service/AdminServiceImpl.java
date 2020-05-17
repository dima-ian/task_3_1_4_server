package dmz.crud.service;

import dmz.crud.model.Role;
import dmz.crud.model.User;
import dmz.crud.repo.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping(value = "/api/admin", produces = MediaType.APPLICATION_JSON_VALUE)
@Transactional
public class AdminServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AdminServiceImpl(UserDAO userDAO, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDAO = userDAO;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping
    @Override
    public List<User> allUsers() {
        return userDAO.findAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void delete(@PathVariable Long id) {
        userDAO.delete(getById(id));
    }

    @GetMapping("/{id}")
    @Override
    public User getById(@PathVariable Long id) {
        return userDAO.findById(id).get();
    }

    @Override
    public boolean isByLogin(String login) {
        if (userDAO.findByLogin(login) == null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public User isByName(String name) {
        return userDAO.findByName(name);
    }

    @PostMapping(value = "/{role}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<User> add(@RequestBody User user, @PathVariable String role) {
        User userFromDB = isByName(user.getName());

        if (userFromDB != null) {
            return null;
        }

        if (role.equalsIgnoreCase("admin")) {
            user.setRoles(Collections.singleton(new Role(2L, "ROLE_ADMIN")));
        } else {
            user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User addUser = userDAO.save(user);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("admin/{id}")
                .buildAndExpand(addUser.getId())
                .toUri();
        return ResponseEntity.created(uriOfNewResource).body(addUser);
    }

    @PutMapping(value = "/{role}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @Override
    public void edit(@RequestBody User user, @PathVariable String role) {
        if (role.equalsIgnoreCase("admin")) {
            user.setRoles(Collections.singleton(new Role(2L, "ROLE_ADMIN")));
        } else {
            user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        }
        if (user.getPassword().isEmpty()) {
            user.setPassword(getById(user.getId()).getPassword());
        } else {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        userDAO.saveAndFlush(user);
    }
}