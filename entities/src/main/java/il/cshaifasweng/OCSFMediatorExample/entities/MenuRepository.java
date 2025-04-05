package il.cshaifasweng.OCSFMediatorExample.entities;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;
public class MenuRepository {}
/*

public class MenuRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public MenuItem findMenuItem(int id) {
        return entityManager.find(MenuItem.class, id);
    }

    public List<MenuItem> FindMenuItems() {
        return FindMenuItems(null);
    }

    public List<MenuItem> FindMenuItems(Boolean isEnabled) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<MenuItem> query = builder.createQuery(MenuItem.class);
        Root<MenuItem> root = query.from(MenuItem.class);
        if (isEnabled != null) {
            query.where(builder.equal(root.get(MenuItem_.isEnabled), isEnabled));
        }
        return entityManager.createQuery(query).getResultList();
    }

    public void UpsertMenuItem(MenuItem menuItem) {
        entityManager.persist(menuItem);
    }
}
*/
