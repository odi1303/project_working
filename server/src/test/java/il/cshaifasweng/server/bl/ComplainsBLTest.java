<<<<<<<< HEAD:server/src/main/java/ComplainsBLTest.java
package org.example.finalproject.bl;

import org.finalproject.bl.ComplainsRepository;
import org.example.finalproject.dal.DeliveriesRepository;
import org.example.finalproject.dal.RestaurantsRepositroy;
import org.example.finalproject.dal.UsersRepository;
import org.example.finalproject.dal.models.Delivery;
import org.example.finalproject.dal.models.Restaurant;
import org.example.finalproject.dal.models.User;
import org.example.finalproject.dal.models.complains.Complain;
import org.example.finalproject.dal.models.complains.DeliveryComplain;
import org.example.finalproject.dal.models.complains.RestaurantComplain;
========
package il.cshaifasweng.server.bl;
/*
import finalproject.dal.ComplainsRepository;
import finalproject.dal.DeliveriesRepository;
import finalproject.dal.RestaurantsRepositroy;
import finalproject.dal.UsersRepository;
import finalproject.dal.models.Delivery;
import finalproject.dal.models.Restaurant;
import finalproject.dal.models.User;
import finalproject.dal.models.complains.Complain;
import finalproject.dal.models.complains.DeliveryComplain;
import finalproject.dal.models.complains.RestaurantComplain;
>>>>>>>> 21281f543c56e50a61a44dd3e672f8622162f13f:server/src/test/java/il/cshaifasweng/server/bl/ComplainsBLTest.java
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ComplainsBLTest {
    private static final int EXISTING_USER_ID = 1;
    private static final long EXISTING_DELIVERY_ID = 1;
    private static final long EXISTING_RESTURANT_ID = 1;

    @Captor
    ArgumentCaptor<Complain> complaindCapture;
    private ComplainsBL tested;

    @BeforeEach
    public void beforeEach() {
        tested = new ComplainsBL();
        tested.complainsRepository = mock(ComplainsRepository.class);
        tested.usersRepository = mock(UsersRepository.class);
        tested.restaurantsRepository = mock(RestaurantsRepositroy.class);
        tested.deliveriesRepository = mock(DeliveriesRepository.class);
    }

    @Test
    void createDeliveryComplain() {
        // Arrange
        String description = "example";
        User user = new User();
        Delivery delivery = new Delivery();
        when(tested.usersRepository.findById(EXISTING_USER_ID)).thenReturn(Optional.of(user));
        when(tested.deliveriesRepository.findById(EXISTING_DELIVERY_ID)).thenReturn(Optional.of(delivery));

        // Act
        tested.createDeliveryComplain(EXISTING_USER_ID, EXISTING_DELIVERY_ID, description);

        // Assert
        verify(tested.complainsRepository).insert(complaindCapture.capture());
        DeliveryComplain inserted = (DeliveryComplain) complaindCapture.getValue();
        assertEquals(user, inserted.getComplainer());
        assertEquals(description, inserted.getDescription());
        assertEquals(delivery, inserted.getDelivery());
    }

    @Test
    void createDeliveryComplain_notExistingUser() {
        // Arrange
        String description = "example";
        when(tested.usersRepository.findById(EXISTING_USER_ID)).thenReturn(Optional.empty());

        // Act
        tested.createDeliveryComplain(EXISTING_USER_ID, EXISTING_DELIVERY_ID, description);

        // Assert
        verify(tested.complainsRepository, never()).insert(complaindCapture.capture());
    }

    @Test
    void createDeliveryComplain_notExistingDelivery() {
        // Arrange
        String description = "example";
        User user = new User();
        when(tested.usersRepository.findById(EXISTING_USER_ID)).thenReturn(Optional.of(user));
        when(tested.deliveriesRepository.findById(EXISTING_DELIVERY_ID)).thenReturn(Optional.empty());

        // Act
        tested.createDeliveryComplain(EXISTING_USER_ID, EXISTING_DELIVERY_ID, description);

        // Assert
        verify(tested.complainsRepository, never()).insert(complaindCapture.capture());
    }

    @Test
    void createRestaurantComplain() {
        // Arrange
        String description = "example";
        User user = new User();
        Restaurant restaurant = new Restaurant();
        when(tested.usersRepository.findById(EXISTING_USER_ID)).thenReturn(Optional.of(user));
        when(tested.restaurantsRepository.findById(EXISTING_RESTURANT_ID)).thenReturn(Optional.of(restaurant));

        // Act
        tested.createRestaurantComplain(EXISTING_USER_ID, EXISTING_DELIVERY_ID, description);

        // Assert
        verify(tested.complainsRepository).insert(complaindCapture.capture());
        RestaurantComplain inserted = (RestaurantComplain) complaindCapture.getValue();
        assertEquals(user, inserted.getComplainer());
        assertEquals(description, inserted.getDescription());
        assertEquals(restaurant, inserted.getRestaurant());
    }

    @Test
    void createRestaurantComplain_notExistingUser() {
        // Arrange
        String description = "example";
        when(tested.usersRepository.findById(EXISTING_USER_ID)).thenReturn(Optional.empty());

        // Act
        tested.createRestaurantComplain(EXISTING_USER_ID, EXISTING_DELIVERY_ID, description);

        // Assert
        verify(tested.complainsRepository, never()).insert(complaindCapture.capture());
    }

    @Test
    void createRestaurantComplain_notExistingRestaurant() {
        // Arrange
        String description = "example";
        User user = new User();
        when(tested.usersRepository.findById(EXISTING_USER_ID)).thenReturn(Optional.of(user));
        when(tested.restaurantsRepository.findById(EXISTING_RESTURANT_ID)).thenReturn(Optional.empty());

        // Act
        tested.createRestaurantComplain(EXISTING_USER_ID, EXISTING_DELIVERY_ID, description);

        // Assert
        verify(tested.complainsRepository, never()).insert(complaindCapture.capture());
    }

    @Test
    void closeComplain() {
    }

    @Test
    void compensateComplain() {
    }
}*/