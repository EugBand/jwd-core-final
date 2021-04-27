package com.epam.jwd.core_final.service;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.service.impl.CrewServiceImpl;
import com.epam.jwd.core_final.util.PropertyReaderUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;


@RunWith(JUnit4.class)
public class CrewServiceTest {
    NassaContext nassa;
    List<CrewMember> members;
    CrewService crewService;

    @Before
    public void setUp() {
        PropertyReaderUtil.loadProperties();
        nassa = NassaContext.getInstance();
        try {
            nassa.init();
        } catch (InvalidStateException e) {
            e.printStackTrace();
        }
        members = (List<CrewMember>) nassa.retrieveBaseEntityList(CrewMember.class);
        crewService = new CrewServiceImpl();
    }

    @Test(expected = IllegalStateException.class)
    public void testCreateCrewMember_returnSameCollection_whenEntityPresent() {

        int membersSizeBefore = members.size();
        CrewMember member = crewService.createCrewMember(members.get(1));
        int membersSizeAfter = members.size();
        assertNull(member);
        assertEquals(membersSizeBefore, membersSizeAfter);
    }

    @Test
    public void testCreateCrewMember_throwsIllegalStateException_whenEntityPresent() {
        assertThrows(
                "Should have thrown IllegalStateException",
                IllegalStateException.class,
                () -> crewService.createCrewMember(members.get(1))
        );
    }
}