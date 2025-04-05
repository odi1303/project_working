package il.cshaifasweng.OCSFMediatorExample.server.dal;

import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Repository;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.User;

import java.util.Optional;

@Repository
public interface UsersRepository extends CrudRepository<User, Long>
{
    Optional<User> findByNameAndPassword(String name, String password);
}