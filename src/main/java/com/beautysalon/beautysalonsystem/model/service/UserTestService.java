package com.beautysalon.beautysalonsystem.model.service;

import com.beautysalon.beautysalonsystem.model.repository.CrudRepository;
import com.beautysalon.beautysalonsystem.model.entity.User;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserTestService {
    @Getter
    private static UserTestService service = new UserTestService();

    private UserTestService() {
    }

//    public User save(User user) throws Exception {
//            try (CrudRepository<User, Long> repository = new CrudRepository<>()) {
//                user.setRole(RoleTestService.getService().FindByRole("user"));
//                return repository.save(user);
//            }
//        }

        public User edit(User user) throws Exception {
            try (CrudRepository<User, Long> repository = new CrudRepository<>()) {
                return repository.edit(user);
            }
        }

        public User remove(Long id) throws Exception {
            try (CrudRepository<User, Long> repository = new CrudRepository<>()) {
                return repository.remove(id, User.class);
            }
        }

        public List<User> findAll() throws Exception {
            try (CrudRepository<User, Long> repository = new CrudRepository<>()) {
                return repository.findAll(User.class);
            }
        }

        public User findById(Long id) throws Exception {
            try (CrudRepository<User, Long> repository = new CrudRepository<>()) {
                return repository.findById(id, User.class);
            }
        }

        public List<User> findByUsername(String username) throws Exception {
            try (CrudRepository<User, Long> repository = new CrudRepository<>()) {
                Map<String, Object> params = new HashMap<>();
                params.put("username", username+"%");
                return repository.executeQuery("Manager.FindByUsername", params, User.class);
            }
        }

        public List<User> findByPassword(String password) throws Exception {
            try (CrudRepository<User, Long> repository = new CrudRepository<>()) {
                Map<String, Object> params = new HashMap<>();
                params.put("password", password+"%");
                return repository.executeQuery("User.FindByPassword", params, User.class);
            }
        }

        public List<User> findByUsernameAndPassword(String username, String password) throws Exception {
            try (CrudRepository<User, Long> repository = new CrudRepository<>()) {
                Map<String, Object> params = new HashMap<>();
                params.put("username", username+"%");
                params.put("password", password+"%");
                return repository.executeQuery("Manager.FindByUsernameAndPassword", params, User.class);
            }
        }

}
