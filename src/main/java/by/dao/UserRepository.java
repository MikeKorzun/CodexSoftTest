package by.dao;

import by.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository extends CrudRepository<User,Integer> {
    User findByUsername(String username);
    User findByFirstNameAndLastNameAllIgnoreCase(String name, String surname);
}
