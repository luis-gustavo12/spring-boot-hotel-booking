package com.github.projects.hotel_system.repositories;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import com.github.projects.hotel_system.models.User;
import com.github.projects.hotel_system.repositories.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    @DisplayName("Given")
    public void setUp() {
        user = new User();
        user.setPassword("pwd123");
        user.setName("John Doe");
        user.setPreferredName("John");
        user.setPhoneNumber("123123123");
        user.setEmail("user@example.com");
        user.setBirthDate(LocalDate.of(1980, 2, 15));
        user.setRole("USER");
        user.setAddress("Random Avenue 125");
        user.setZipCode("2131232132131");
        user.setActive(true);

        testEntityManager.persist(user);
        testEntityManager.flush();
    }

    @Test
    @DisplayName("Test if user is available")
    public void testUser() {

        Optional<User> opt = userRepository.findByEmail(user.getEmail());
        assertThat(opt).isPresent();
        assertThat(opt.get().getName()).isEqualTo("John Doe");

    }

    @Test
    public void whenFindByUsername_withNonExistentUser_thenReturnEmpty() {
        Optional<User> foundUser = userRepository.findByEmail("nonexistent");
        assertThat(foundUser).isNotPresent();
    }


}
