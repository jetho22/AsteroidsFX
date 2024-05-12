package dk.sdu.mmmi.cbse.scoringservice;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ScoringServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(ScoringServiceApp.class, args);
    }

    private AtomicInteger livescore = new AtomicInteger(0);

    @GetMapping("/getScore")
    public int getScore() {
        return livescore.get();
    }
    @GetMapping("/score")
    public int updateScore(@RequestParam int score) {
        return livescore.addAndGet(score);
    }

}