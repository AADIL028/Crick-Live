package com.example.LiveCricket.Controller;

import com.example.LiveCricket.Service.ScoreService;
import com.example.LiveCricket.entities.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/cricket")
public class CricketController {
    @Autowired
    private ScoreService scoreService;

    @GetMapping("/test")
    public ResponseEntity<?> getCricketLiveScore() throws InterruptedException {
        List<Match> liveScore = scoreService.getLiveScore();
        return new ResponseEntity<>(liveScore, HttpStatus.OK);
    }

    @GetMapping("/live")
    public ModelAndView test(Model model) {
        List<Match> liveScore = scoreService.getLiveScore();
        model.addAttribute("data", liveScore);
        return new ModelAndView("Live_Score");
    }
}
