package il.cshaifasweng.OCSFMediatorExample.entities;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class UsersRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public static void main(String[] args) {
        User manager = new User("1234", UserType.Admin);

    }

    public User FindUser(int id) {
        return entityManager.find(User.class, id);
    }

    public List<User> FindUsers() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        return entityManager.createQuery(query).getResultList();
    }

    public void UpsertMenuItem(MenuItem menuItem) {
        entityManager.persist(menuItem);
    }

    //checks if the user exist
    public boolean userExists(int username) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.where(builder.equal(root.get("id"), username));
        List<User> users = entityManager.createQuery(query).getResultList();
        if (!users.isEmpty())
            return true;
        return false;
    }

    //checks if the password is correct
    public boolean correctPassword(int id, String password) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.where(builder.equal(root.get("id"), id));
        List<User> users = entityManager.createQuery(query).getResultList();
        for (User user : users) {
            if (user.correctPassword(password))
                return true;
        }
        return false;
    }

    //returns the type of the user
    public UserType getUserType(int id, String password) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.where(builder.equal(root.get("id"), id));
        List<User> users = entityManager.createQuery(query).getResultList();
        for (User user : users) {
            if (user.correctPassword(password))
                return user.getType();
        }
        return null;
    }

}