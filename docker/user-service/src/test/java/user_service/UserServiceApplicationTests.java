package user_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import user_service.model.User;
import user_service.repository.UserRepository;
import user_service.services.UserService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceApplicationTests {

	@Mock private UserRepository userRepository;
	@InjectMocks private UserService userService;

	@Test
	void testSaveUser() {
		User user = new User();
		user.setName("Liam");

		when(userRepository.save(any(User.class))).thenReturn(user);

		User savedUser = userService.saveUser(user);
		assertEquals("Liam", savedUser.getName());
	}

	@Test
	void testGetUserById() {
        User user = new User();
        user.setId(1L);
        user.setName("Bob");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User foundUser = userService.getUserById(1L);
        assertNotNull(foundUser);
        assertEquals(1L, foundUser.getId());
        assertEquals("Bob", foundUser.getName());
    }

	@Test
	void testUpdateUserWithOrder_Success() {
        // Given
        User user = new User();
        user.setId(1L);
        user.setName("Alice");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        User updatedUser = userService.updateUserWithOrder(1L, 8080L);

        // Then
        assertNotNull(updatedUser);
        assertEquals(1, updatedUser.getOrders().size());
        assertTrue(updatedUser.getOrders().contains(8080L));
        verify(userRepository, times(1)).save(any(User.class));
    }
}
