package axan18.ridetheschedule.repositories;

import axan18.ridetheschedule.bootstrap.BootstrapScheduleData;
import axan18.ridetheschedule.bootstrap.BootstrapSharedScheduleData;
import axan18.ridetheschedule.bootstrap.BootstrapUserData;
import axan18.ridetheschedule.entities.AppUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({BootstrapSharedScheduleData.class, BootstrapUserData.class, BootstrapScheduleData.class})
class SharedScheduleRepositoryTest {
    @Autowired
    SharedScheduleRepository sharedScheduleRepository;
    @Autowired
    AppUserRepository appUserRepository;
    @Test
    void testGettingUsersThatSharedScheduleToUser() {
        List<AppUser> users = appUserRepository.findAll();
        HashMap<UUID, List<AppUser>> result = new HashMap<>();
        for(AppUser u : users){
            UUID userId = u.getId();
            List<AppUser> listOfSharers = sharedScheduleRepository.findUsersWhoSharedSchedules(userId, 1,2022);
            if(!listOfSharers.isEmpty()) result.put(userId,listOfSharers);
        }
        assertFalse(result.isEmpty());
    }
    
    @Test
    void testWrongDate(){
        List<AppUser> users = appUserRepository.findAll();
        HashMap<UUID, List<AppUser>> result = new HashMap<>();
        for(AppUser u : users){
            UUID userId = u.getId();
            List<AppUser> listOfSharers = sharedScheduleRepository.findUsersWhoSharedSchedules(userId, 1,2022);
            if(!listOfSharers.isEmpty()) result.put(userId,listOfSharers);
        }

        assertTrue(result.isEmpty());
    }
}