package org.example.finalproject.bl;

import jakarta.inject.Inject;
import org.example.finalproject.dal.MenuRepository;
import org.example.finalproject.dal.RequestsRepository;
import org.example.finalproject.dal.UsersRepository;
import org.example.finalproject.dal.models.MenuItem;
import org.example.finalproject.dal.models.User;
import org.example.finalproject.dal.models.requests.DeleteRequest;
import org.example.finalproject.dal.models.requests.InsertRequest;
import org.example.finalproject.dal.models.requests.Request;
import org.example.finalproject.dal.models.requests.UpdateRequest;

import java.util.List;
import java.util.Optional;

public class AdminBL {
    @Inject
    MenuRepository menuRepository;

    @Inject
    RequestsRepository requestsRepository;

    @Inject
    UsersRepository usersRepository;

    public AdminBL(MenuRepository menuRepository, RequestsRepository requestsRepository, UsersRepository usersRepository) {
        this.menuRepository = menuRepository;
        this.requestsRepository = requestsRepository;
        this.usersRepository = usersRepository;
    }
    public void deleteMenuItem(int menuId, int userId)
    {
        Optional<User> user = usersRepository.findById(userId);

        if (user.map(User::isAdmin).orElse(false)) {
            return;
        }

        menuRepository.deleteById(menuId);
    }
    public List<MenuItem> getAllMenuItems() {
        return menuRepository.findAll().toList();
    }
    public List<Request> getRequests() {
        return requestsRepository.findAll().toList();
    }

    public void markRequestAsApproved(int requestId, int userId) {
        Optional<User> user = usersRepository.findById(userId);

        if (user.map(User::isAdmin).orElse(false)) {
            return;
        }

        Optional<Request> maybeRequest = requestsRepository.findById(requestId);
        maybeRequest.ifPresent(request -> {
            switch (request) {
                case DeleteRequest deleteRequest -> menuRepository.deleteById(deleteRequest.getMenuItem());
                case InsertRequest insertRequest ->
                        menuRepository.insert(new MenuItem(insertRequest.getMenuItemDescription(), insertRequest.getMenuItemPrice()));
                case UpdateRequest updateRequest ->
                        menuRepository.findById(updateRequest.getMenuItem()).ifPresent(menu -> {
                            if (updateRequest.getMenuItemPrice() != null) {
                                menu.setPrice(updateRequest.getMenuItemPrice());
                            }
                            if (updateRequest.getMenuItemDescription() != null) {
                                menu.setDescription(updateRequest.getMenuItemDescription());
                            }
                            menuRepository.update(menu);
                        });
                default -> {
                    // Unknown request
                }
            }
            request.approve();
            requestsRepository.update(request);


        });
    }
    public void markRequestAsRejected(int requestId, int userId) {
        Optional<User> user = usersRepository.findById(userId);

        if (user.map(User::isAdmin).orElse(false)) {
            return;
        }

        Optional<Request> maybeRequest = requestsRepository.findById(requestId);
        maybeRequest.ifPresent(request -> {
            request.reject();
            requestsRepository.update(request);
        });
    }
}
