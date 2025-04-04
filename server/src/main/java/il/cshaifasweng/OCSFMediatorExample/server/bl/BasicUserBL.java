package il.cshaifasweng.OCSFMediatorExample.server.bl;

import jakarta.data.repository.Repository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import il.cshaifasweng.OCSFMediatorExample.server.dal.UsersRepository;
import il.cshaifasweng.OCSFMediatorExample.entities.UserType;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.User;

import java.util.Optional;
@ApplicationScoped
public class BasicUserBL {
    public BasicUserBL() {}
    @Inject
    UsersRepository usersRepository;

    public void addUser(User user) {
        System.out.println(user.toString());
        usersRepository.save(user);
    }
    public boolean check_password(String name, String password) {
        /*Optional<User> Maybeuser = usersRepository.findById(id);
        if(Maybeuser.isEmpty())
        {
            return false;
        }
        User user= Maybeuser.get();
        return Password.equals(user.password);*/
        return usersRepository.findByNameAndPassword(name, password).isPresent();
                /*.findById(id)
                .map(user -> Password.equals(user.password))
                .orElse(false);*/
    }

    public UserType getUserType(String name, String password) {
        System.out.println("hello from the database");
        return usersRepository
                .findByNameAndPassword(name, password)
                .map(User::getType)
                .orElse(UserType.Empty);
    }
}

