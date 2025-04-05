package il.cshaifasweng.OCSFMediatorExample.server.bl;

import jakarta.inject.Inject;
import il.cshaifasweng.OCSFMediatorExample.server.dal.TableOrderRepository;
import il.cshaifasweng.OCSFMediatorExample.server.dal.UsersRepository;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.TableOrder;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.User;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TableOrdersBL {
    @Inject
    UsersRepository usersRepository;

    @Inject
    TableOrderRepository tableOrderRepository;

    public List<TableOrder> getTableOrders(Long userId) {
        Optional<User> Maybeuser = usersRepository.findById(userId);
        if(Maybeuser.isEmpty())
            return null;

        User user = Maybeuser.get();
        return user.getTableOrders();
    }

    public void cancelTableOrder(Long userId, Long tableOrderId) {
        Optional<User> Maybeuser = usersRepository.findById(userId);
        if(Maybeuser.isEmpty())
            return;

        User user = Maybeuser.get();
        TableOrder tableOrder = user.getTableOrders().stream().filter(to -> Objects.equals(to.getId(), tableOrderId)).findFirst().get();

        Date now = new Date();

        if (now.after(tableOrder.getStartDate())) {
            // Can't cancel
            return;
        }

        tableOrderRepository.delete(tableOrder);

        Date nowBeforeHour = Date.from(now.toInstant().minus(1, ChronoUnit.HOURS));
        if (nowBeforeHour.after(tableOrder.getStartDate())) {
            // return need to get money
        }
    }
}
