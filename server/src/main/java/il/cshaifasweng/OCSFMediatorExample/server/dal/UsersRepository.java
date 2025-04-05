package il.cshaifasweng.OCSFMediatorExample.server.dal;

import org.springframework.data.repository.CrudRepository;
import jakarta.data.repository.Repository;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.User;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Transactional
//@Repository
public interface UsersRepository extends CrudRepository<User, Long>
{
    Optional<User> findByNameAndPassword(String name, String password);

    List<User> findByName(String name);
}