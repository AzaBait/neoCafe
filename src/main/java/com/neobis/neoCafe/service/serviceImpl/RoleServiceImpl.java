package com.neobis.neoCafe.service.serviceImpl;

import com.neobis.neoCafe.entity.Role;
import com.neobis.neoCafe.repository.RoleRepo;
import com.neobis.neoCafe.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepo roleRepo;

    @Override
    public Optional<Role> findByName(String name) throws RoleNotFoundException {
        Optional<Role> customerRole = roleRepo.findByName(name);
        if (customerRole.isEmpty()) {
            throw new RoleNotFoundException("Role " + name + " not found");
        }
        return customerRole;
    }
}
