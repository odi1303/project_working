package il.cshaifasweng.OCSFMediatorExample.entities;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class RequestsRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public Request FindRequest(int id) {
        return entityManager.find(Request.class, id);
    }

    public List<Request> FindRequests() {
        return FindRequests(null);
    }

    public List<Request> FindRequests(RequestStatus status) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Request> query = builder.createQuery(Request.class);
        Root<Request> root = query.from(Request.class);
        if (status == null) {
            query.where(builder.equal(root.get(Request_.status), status));
        }
        return entityManager.createQuery(query).getResultList();
    }

    public void UpsertRequest(Request request) {
        entityManager.persist(request);
    }
}
