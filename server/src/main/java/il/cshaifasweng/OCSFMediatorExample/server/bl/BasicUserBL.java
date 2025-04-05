package il.cshaifasweng.OCSFMediatorExample.server.bl;

import jakarta.annotation.PostConstruct;
//import jakarta.data.repository.Repository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import il.cshaifasweng.OCSFMediatorExample.server.dal.UsersRepository;
import il.cshaifasweng.OCSFMediatorExample.entities.UserType;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.StreamSupport;

@ApplicationScoped
public class BasicUserBL {
    public BasicUserBL() {}
    @Inject
    UsersRepository usersRepository;

    @PostConstruct
    public void init() {
        System.out.println("BasicUserBL @PostConstruct: usersRepository=" + usersRepository);
        if (usersRepository == null) {
            System.out.println("ERROR: usersRepository is null in BasicUserBL");
        }
    }
    @Transactional
    public void addUser(User user) {
        System.out.println(user.toString());
        usersRepository.save(user);
        usersRepository.findAll().forEach(System.out::println);
        System.out.println(user.toString());
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

    @Transactional
    public UserType getUserType(String name, String password) {
        System.out.println("hello from the database");
        var users = usersRepository.findAll();
        users.forEach(user -> System.out.println(user.toString()));
        return StreamSupport.stream(users.spliterator(), false)

                .filter(user -> user.name.equals(name) && user.password.equals(password))
                //.findByNameAndPassword(name, password)
                .map(User::getType)
                .findFirst()
                .orElse(UserType.Empty);
    }
}

