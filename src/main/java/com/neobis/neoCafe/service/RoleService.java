package com.neobis.neoCafe.service;

import com.neobis.neoCafe.entity.Role;

import javax.management.relation.RoleNotFoundException;
import java.util.Optional;

public interface RoleService {

    Optional<Role> findByName(String name) throws RoleNotFoundException;
}
