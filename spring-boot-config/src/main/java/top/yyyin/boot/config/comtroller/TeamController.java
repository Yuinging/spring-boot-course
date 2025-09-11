package top.yyyin.boot.config.comtroller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.yyyin.boot.config.moddel.Team;


@RestController
public class TeamController {
    @PostMapping
    public ResponseEntity<Team> addTeam(@Valid @RequestBody Team team){
        return ResponseEntity.ok(team);
    }
}
