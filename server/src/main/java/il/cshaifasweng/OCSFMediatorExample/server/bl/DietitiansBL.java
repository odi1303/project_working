package il.cshaifasweng.OCSFMediatorExample.server.bl;

import jakarta.data.repository.Repository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import il.cshaifasweng.OCSFMediatorExample.server.dal.RequestsRepository;
import il.cshaifasweng.OCSFMediatorExample.server.dal.UsersRepository;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.requests.Request;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.User;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.requests.UpdateRequest;

import java.util.Optional;
@ApplicationScoped
public class DietitiansBL {
    public DietitiansBL() {}
    @Inject
    UsersRepository usersRepository;

    @Inject
    RequestsRepository requestsRepository;

    public void createRequest(Long userId, Request request)
    {
        Optional<User> user = usersRepository.findById(userId);

        if (user.map(User::isDietitian).orElse(false)) {
            return;
        }

        if (request instanceof UpdateRequest updateRequest
                && updateRequest.getMenuItemDescription() == null
                && updateRequest.getMenuItemPrice() == null) {
            // Nothing to update
            return;
        }

        requestsRepository.insert(request);
    }
}
