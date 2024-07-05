package com.example.LiveCricket.Service;

import com.example.LiveCricket.entities.Match;

import java.util.List;

public interface ScoreService {
    List<Match> getLiveScore();
}
