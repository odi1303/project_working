package il.cshaifasweng.OCSFMediatorExample.server.bl;

import jakarta.inject.Inject;
import il.cshaifasweng.OCSFMediatorExample.server.dal.MenuRepository;
import il.cshaifasweng.OCSFMediatorExample.server.dal.RequestsRepository;
import il.cshaifasweng.OCSFMediatorExample.server.dal.UsersRepository;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.MenuItem;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.User;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.requests.DeleteRequest;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.requests.InsertRequest;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.requests.Request;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.requests.UpdateRequest;

import java.util.List;
import java.util.Optional;

public class AdminBL {
    MenuRepository menuRepository;

    RequestsRepository requestsRepository;

    UsersRepository usersRepository;

    public AdminBL(MenuRepository menuRepository, RequestsRepository requestsRepository, UsersRepository usersRepository) {
        this.menuRepository = menuRepository;
        this.requestsRepository = requestsRepository;
        this.usersRepository = usersRepository;
    }
    public void deleteMenuItem(Long menuId, Long userId)
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

    public void markRequestAsApproved(Long requestId, Long userId) {
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
    public void markRequestAsRejected(Long requestId, Long userId) {
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
