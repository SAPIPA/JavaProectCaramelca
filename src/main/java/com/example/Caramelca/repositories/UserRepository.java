package com.example.Caramelca.repositories;

import com.example.Caramelca.models.Employee;
import com.example.Caramelca.models.Employee_Service;
import com.example.Caramelca.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Iterable<User> findByNumber(String number);
    User findByUsername(String username);
}
