package dmz.crud.service;

import dmz.crud.model.User;
import dmz.crud.repo.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Transactional
public class UserServiceImpl {

    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) { //@Qualifier("encoder")
        this.userDAO = userDAO;
    }

    @GetMapping("/{id}")
    public User isByName(@PathVariable Long id) {
        return userDAO.findById(id).get();
    }


}