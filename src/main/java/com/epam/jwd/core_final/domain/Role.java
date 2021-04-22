package com.epam.jwd.core_final.domain;

import com.epam.jwd.core_final.exception.UnknownEntityException;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Role implements BaseEntity {
    MISSION_SPECIALIST(1L),
    FLIGHT_ENGINEER(2L),
    PILOT(3L),
    COMMANDER(4L);

    private final Long id;

    Role(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    /**
     * todo via java.lang.enum methods!
     */
    @Override
    public String getName() {
        return this.name();
    }

    /**
     * todo via java.lang.enum methods!
     * @throws UnknownEntityException if such id does not exist
     */
    public static Role resolveRoleById(Long id) {
        Stream<Role> roles = Arrays.stream(Role.values()).filter(item -> id.equals(item.getId()));
        if (roles.count() == 0) throw new UnknownEntityException("Entity didn't exist");
        Role role = roles.collect(Collectors.toList()).get(0);
        return role;
    }
}
