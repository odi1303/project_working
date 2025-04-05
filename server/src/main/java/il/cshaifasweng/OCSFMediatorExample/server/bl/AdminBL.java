/*
package il.cshaifasweng.OCSFMediatorExample.server.bl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.MenuItem;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.User;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.requests.DeleteRequest;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.requests.InsertRequest;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.requests.Request;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.requests.UpdateRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class AdminBL {
    public AdminBL() {}
    @Inject
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
        var retval = new ArrayList<MenuItem>();
        menuRepository.findAll().forEach(retval::add);
        return retval;
    }
    public List<Request> getRequests() {
        var retval = new ArrayList<Request>();
        requestsRepository.findAll().forEach(retval::add);
        return retval;
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
                        menuRepository.save(new MenuItem(insertRequest.getMenuItemDescription(), insertRequest.getMenuItemPrice()));
                case UpdateRequest updateRequest ->
                        menuRepository.findById(updateRequest.getMenuItem()).ifPresent(menu -> {
                            if (updateRequest.getMenuItemPrice() != null) {
                                menu.setPrice(updateRequest.getMenuItemPrice());
                            }
                            if (updateRequest.getMenuItemDescription() != null) {
                                menu.setDescription(updateRequest.getMenuItemDescription());
                            }
                            menuRepository.save(menu);
                        });
                default -> {
                    // Unknown request
                }
            }
            request.approve();
            requestsRepository.save(request);


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
            requestsRepository.save(request);
        });
    }
}
*/
