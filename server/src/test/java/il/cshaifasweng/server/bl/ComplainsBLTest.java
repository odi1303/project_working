package il.cshaifasweng.server.bl;

import il.cshaifasweng.server.dal.ComplainsRepository;
import il.cshaifasweng.server.dal.DeliveriesRepository;
import il.cshaifasweng.server.dal.RestaurantsRepository; // Fixed typo: "Repositroy" -> "Repository"
import il.cshaifasweng.server.dal.UsersRepository;
import il.cshaifasweng.server.dal.models.Delivery;
import il.cshaifasweng.server.dal.models.Restaurant;
import il.cshaifasweng.server.dal.models.User;
import il.cshaifasweng.server.dal.models.complains.Complain;
import il.cshaifasweng.server.dal.models.complains.DeliveryComplain;
import il.cshaifasweng.server.dal.models.complains.RestaurantComplain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    private static final long EXISTING_RESTAURANT_ID = 1; // Fixed typo: "RESTURANT" -> "RESTAURANT"

    @Captor
    ArgumentCaptor<Complain> complainCapture; // Fixed typo: "complaindCapture" -> "complainCapture"
    private ComplainsBL tested;

    @BeforeEach
    public void beforeEach() {
        tested = new ComplainsBL();
        tested.complainsRepository = mock(ComplainsRepository.class);
        tested.usersRepository = mock(UsersRepository.class);
        tested.restaurantsRepository = mock(RestaurantsRepository.class);
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
        verify(tested.complainsRepository).insert(complainCapture.capture());
        DeliveryComplain inserted = (DeliveryComplain) complainCapture.getValue();
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
        verify(tested.complainsRepository, never()).insert(complainCapture.capture());
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
        verify(tested.complainsRepository, never()).insert(complainCapture.capture());
    }

    @Test
    void createRestaurantComplain() {
        // Arrange
        String description = "example";
        User user = new User();
        Restaurant restaurant = new Restaurant();
        when(tested.usersRepository.findById(EXISTING_USER_ID)).thenReturn(Optional.of(user));
        when(tested.restaurantsRepository.findById(EXISTING_RESTAURANT_ID)).thenReturn(Optional.of(restaurant));

        // Act
        tested.createRestaurantComplain(EXISTING_USER_ID, EXISTING_RESTAURANT_ID, description); // Fixed argument to use RESTAURANT_ID

        // Assert
        verify(tested.complainsRepository).insert(complainCapture.capture());
        RestaurantComplain inserted = (RestaurantComplain) complainCapture.getValue();
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
        tested.createRestaurantComplain(EXISTING_USER_ID, EXISTING_RESTAURANT_ID, description); // Fixed argument to use RESTAURANT_ID

        // Assert
        verify(tested.complainsRepository, never()).insert(complainCapture.capture());
    }

    @Test
    void createRestaurantComplain_notExistingRestaurant() {
        // Arrange
        String description = "example";
        User user = new User();
        when(tested.usersRepository.findById(EXISTING_USER_ID)).thenReturn(Optional.of(user));
        when(tested.restaurantsRepository.findById(EXISTING_RESTAURANT_ID)).thenReturn(Optional.empty());

        // Act
        tested.createRestaurantComplain(EXISTING_USER_ID, EXISTING_RESTAURANT_ID, description); // Fixed argument to use RESTAURANT_ID

        // Assert
        verify(tested.complainsRepository, never()).insert(complainCapture.capture());
    }

    @Test
    void closeComplain() {
        // TODO: Implement test
    }

    @Test
    void compensateComplain() {
        // TODO: Implement test
    }
}