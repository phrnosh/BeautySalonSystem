import com.beautysalon.beautysalonsystem.model.entity.Admin;
import com.beautysalon.beautysalonsystem.model.entity.enums.UserState;

import static com.beautysalon.beautysalonsystem.model.entity.Admin.builder;
public class PersonsTest {
    public static void main(String[] args) {
        Admin admin =
                builder()
                        .name("leva")
                        .family("ziaei")
//                        .username("Levaziaeii")
//                        .password("admin123")
                        .status(UserState.Active)
//                        .address(address)
                        .build();

    }
}
