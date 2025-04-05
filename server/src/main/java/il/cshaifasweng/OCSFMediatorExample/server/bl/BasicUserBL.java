package il.cshaifasweng.OCSFMediatorExample.server.bl;

import il.cshaifasweng.OCSFMediatorExample.server.dal.UserType;
import jakarta.inject.Inject;
import il.cshaifasweng.OCSFMediatorExample.server.dal.UsersRepository;
l.cshaifasweng.OCSFMediatorExample.server.dal.UserType
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.User;

import java.util.Optional;

public class BasicUserBL {
    @Inject
    UsersRepository usersRepository;

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
        return usersRepository
                .findByNameAndPassword(name, password)
                .map(User::getType)
                .orElse(UserType.Empty);
    }
}

