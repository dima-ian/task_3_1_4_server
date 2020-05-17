package dmz.crud.repo;


import dmz.crud.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDAO extends JpaRepository<User, Long> {

    List<User> findAll();

    <S extends User> S save(S s);

    Optional<User> findById(Long aLong);

    void deleteById(Long aLong);

    <S extends User> S saveAndFlush(S s);

    User findByName(String name);

    User findByLogin(String email);

}
