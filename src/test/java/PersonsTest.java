import com.beautysalon.beautysalonsystem.model.entity.Admin;
import com.beautysalon.beautysalonsystem.model.entity.Customer;
import com.beautysalon.beautysalonsystem.model.entity.enums.UserState;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

import static com.beautysalon.beautysalonsystem.model.entity.Customer.builder;
public class PersonsTest {
    EntityManagerFactory factory = Persistence.createEntityManagerFactory("beautysalon");
    EntityManager em = factory.createEntityManager();
    EntityTransaction et = em.getTransaction();

    public void save() {
        et.begin();

        Customer customer =
                builder()
                        .name("name")
                        .family("family")
                        .email("email@gmail.com")
                        .nationalCode("123456789")
                        .phoneNumber("09125692673")
//                        .username("aaaa")
//                        .password("aaa123")
                        .status(UserState.Active)
//                        .address(address)
                        .build();


        em.persist(customer);

        et.commit();
        em.close();
    }

    public List<Customer> findAllAdmin(){
        return em.createQuery("select c from customerEntity c", Customer.class).getResultList();
    }

    public static void main(String[] args) {
        PersonsTest test = new PersonsTest();
        test.save();

    }
}
