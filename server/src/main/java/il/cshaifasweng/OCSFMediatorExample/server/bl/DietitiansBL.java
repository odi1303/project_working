package org.example.finalproject.bl;

import jakarta.inject.Inject;
import org.example.finalproject.dal.RequestsRepository;
import org.example.finalproject.dal.UsersRepository;
import org.example.finalproject.dal.models.requests.Request;
import org.example.finalproject.dal.models.User;
import org.example.finalproject.dal.models.requests.UpdateRequest;

import java.util.Optional;

public class DietitiansBL {
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
