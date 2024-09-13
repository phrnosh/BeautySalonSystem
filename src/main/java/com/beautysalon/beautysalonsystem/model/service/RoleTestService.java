package com.beautysalon.beautysalonsystem.model.service;

import com.beautysalon.beautysalonsystem.model.entity.Role;
import com.beautysalon.beautysalonsystem.model.repository.CrudRepository;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoleTestService {
    @Getter
    private static RoleTestService service = new RoleTestService();

    private RoleTestService() {
    }

    public Role save(Role role) throws Exception {
        try (CrudRepository<Role, Long> repository = new CrudRepository<>()) {
            return repository.save(role);
        }
    }

    public Role edit(Role role) throws Exception {
        try (CrudRepository<Role, Long> repository = new CrudRepository<>()) {
            return repository.save(role);
        }
    }

    public Role remove(Long id) throws Exception {
        try (CrudRepository<Role, Long> repository = new CrudRepository<>()) {
            return repository.remove(id, Role.class);
        }
    }

    public List<Role> findAll() throws Exception {
        try (CrudRepository<Role, Long> repository = new CrudRepository<>()) {
            return repository.findAll(Role.class);
        }
    }

    public Role findById(Long id) throws Exception {
        try (CrudRepository<Role, Long> repository = new CrudRepository<>()) {
            return repository.findById(id, Role.class);
        }
    }

    public Role FindByRole(String role) throws Exception {
        try (CrudRepository<Role, String> repository = new CrudRepository<>()) {
            Map<String, Object> params = new HashMap<>();
            params.put("role", role);
            List<Role> result = repository.executeQuery( "Role.FindByRole", params, Role.class);
            if (result.isEmpty()) {
                return null;
            } else {
                return result.get(0);
            }
        }
    }
}
