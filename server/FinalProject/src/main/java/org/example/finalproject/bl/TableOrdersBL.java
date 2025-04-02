package org.example.finalproject.bl;

import jakarta.inject.Inject;
import org.example.finalproject.dal.TableOrderRepository;
import org.example.finalproject.dal.UsersRepository;
import org.example.finalproject.dal.models.TableOrder;
import org.example.finalproject.dal.models.User;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

public class TableOrdersBL {
    @Inject
    UsersRepository usersRepository;

    @Inject
    TableOrderRepository tableOrderRepository;

    public List<TableOrder> getTableOrders(int userId) {
        User user = usersRepository.findById(userId).get();
        return user.getTableOrders();
    }

    public void cancelTableOrder(int userId, int tableOrderId) {
        User user = usersRepository.findById(userId).get();
        TableOrder tableOrder = user.getTableOrders().stream().filter(to -> to.getId() == tableOrderId).findFirst().get();

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
