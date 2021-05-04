package com.hibernate.example

import com.hibernate.example.model.Address
import com.hibernate.example.model.City
import com.hibernate.example.model.User
import com.hibernate.example.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import spock.lang.Specification

import javax.validation.ConstraintViolationException

import static com.hibernate.example.model.User.Type.ADMIN
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT /*To enable h2 console*/)
@TestPropertySource(locations = "classpath:test.properties")
class UserRepositorySpecification extends Specification {

    @Autowired
    private final UserRepository userRepository

    def cleanup() {
        userRepository.deleteAll()
    }

    def "should correctly store enums"() {
        given:
        def user = getUser()

        when:
        userRepository.save(user)
        def retrieved = userRepository.getByUserNameLike("Nam")

        then:
        retrieved.getType() == ADMIN
    }

    def "should store both addresses"() {
        given:
        def user = getUser()
        user.setHomeAddress(new Address("home", new City("zip", "city", "country")))
        user.setBillingAddress(new Address("billing", new City("zip", "b_city", "country")))

        when:
        userRepository.save(user)
        def retrieved = userRepository.getByUserNameLike("userName")

        then:
        retrieved.getHomeAddress().getStreet() == "home"
        retrieved.getHomeAddress().getCity().getName() == "city"
        retrieved.getBillingAddress().getStreet() == "billing"
        retrieved.getBillingAddress().getCity().getName() == "b_city"
    }

    def "should reject partial address and city"() {
        given:
        def user = getUser()
        user.setBillingAddress(address)

        when:
        userRepository.save(user)

        then:
        thrown(ConstraintViolationException)

        where:
        address << [new Address("test", null),
                    new Address(null, new City("z", "n", "c")),
                    new Address("test", new City("z", null, "c"))]
    }

    private static def getUser() {
        new User("userName", "first", "last", "xxxxxx", ADMIN)
    }
}
