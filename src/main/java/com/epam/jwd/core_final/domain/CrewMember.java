package com.epam.jwd.core_final.domain;

import lombok.Getter;

/**
 * Expected fields:
 * <p>
 * role {@link Role} - member role
 * rank {@link Rank} - member rank
 * isReadyForNextMissions {@link Boolean} - true by default. Set to false, after first failed mission
 */

@Getter
public class CrewMember extends AbstractBaseEntity {
    boolean isReadyForNextMissions = true;
    boolean isMissing = false;
    // todo
    private Role role;
    private Rank rank;

    public CrewMember(long id, String name, Role role, Rank rank) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.rank = rank;
    }

    public void setReadyForNextMissions(boolean readyForNextMissions) {
        isReadyForNextMissions = readyForNextMissions;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setMissing(boolean missing) {
        this.isMissing = missing;
    }
}
