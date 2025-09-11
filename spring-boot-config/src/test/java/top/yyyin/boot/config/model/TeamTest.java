package top.yyyin.boot.config.model;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.yyyin.boot.config.moddel.Team;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Slf4j
public class TeamTest {
    @Resource
    private Team team;
    @Test
    public void textTeam1() {
    log.info("team: {}", team);
    assertEquals("xia", team.getLeader());

    assertEquals(team.getPhone().matches("^[0-9]{11}$"), true);

    assertEquals(team.getAge() > 1 && team.getAge() <= 5, true);

    assertEquals(team.getCreateDate().isBefore(LocalDate.now()), true);
}

//    @Test
//    void textTeam1() {
//        log.info("team: {}", team);
//        assertEquals("yyyin", team.getLeader());
//    }
//
//    @Test
//    void textTeam2() {
//        assertEquals("yyyin2", team.getLeader());
//    }


}
