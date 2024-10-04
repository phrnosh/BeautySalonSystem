//import com.beautysalon.beautysalonsystem.model.entity.*;
//import com.beautysalon.beautysalonsystem.model.entity.enums.UserState;
//import com.beautysalon.beautysalonsystem.model.service.ManagerTestService;
//import com.beautysalon.beautysalonsystem.model.service.RoleTestService;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.EntityManagerFactory;
//import jakarta.persistence.EntityTransaction;
//import jakarta.persistence.Persistence;
//
////TODO: Wrong Import !
////import javax.xml.registry.infomodel.User;
//import java.util.List;
//
//import static com.beautysalon.beautysalonsystem.model.entity.Customer.builder;
//public class PersonsTest {
//    public static void main(String[] args) throws Exception {
//
//        Role managerRole = Role.builder().role("manager").build();
//        System.out.println(RoleTestService.getService().save(managerRole));
//
//
//        Manager manager =
//                Manager
//                        .builder()
//                        .name("aaaa")
//                        .family("bbbb")
////                    .user().username("cccc")
////                    .password("123456789")
//                        .email("aaa@gmail.com")
//                        .phoneNumber("09121234567")
//                        .status(UserState.Active)
////                        .address(address)
//                        .build();
//
//        System.out.println(ManagerTestService.getService().save(manager));
//    }
//
//    EntityManagerFactory factory = Persistence.createEntityManagerFactory("beautysalon");
//    EntityManager em = factory.createEntityManager();
//    EntityTransaction et = em.getTransaction()                         ;
//
//    public void save() {
//        et.begin();
//
//        Customer customer =
//                builder()
//                        .name("name")
//                        .family("family")
//                        .email("email@gmail.com")
//                        .nationalCode("123456789")
//                        .phoneNumber("09125692673")
////                        .username("aaaa")
////                        .password("aaa123")
//                        .status(UserState.Active)
////                        .address(address)
//                        .build();
//
//
//        em.persist(customer);
//
//        et.commit();
//        em.close();
//    }
//
//    public List<Customer> findAllAdmin(){
//        return em.createQuery("select c from customerEntity c", Customer.class).getResultList();
//    }
//
//    public static void main(String[] args) {
//        PersonsTest test = new PersonsTest();
//        test.save();
//
//    }
//
//
//}
