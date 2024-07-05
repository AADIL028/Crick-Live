package com.example.LiveCricket.Service.impl;

import com.example.LiveCricket.Service.ScoreService;
import com.example.LiveCricket.entities.Match;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class ScoreServiceImpl implements ScoreService {
    @Override
    public List<Match> getLiveScore() {
        List<Match> matches = new ArrayList<>();
        try {
            String url = "https://www.cricbuzz.com/cricket-match/live-scores";
            Document document = Jsoup.connect(url).get();
            Elements elements = document.select("div.cb-mtch-lst.cb-tms-itm");
            for (Element match : elements) {
                HashMap<String, String> liveMatchInfo = new LinkedHashMap<>();
                String teamsHeading = match.select("h3.cb-lv-scr-mtch-hdr").select("a").text();
                String matchNumberVenue = match.select("span").text();
                Elements matchBatTeamInfo = match.select("div.cb-hmscg-bat-txt");
                String battingTeam = matchBatTeamInfo.select("div.cb-hmscg-tm-nm").text();
                String score = matchBatTeamInfo.select("div.cb-hmscg-tm-nm+div").text();
                Elements bowlTeamInfo = match.select("div.cb-hmscg-bwl-txt");
                String bowlTeam = bowlTeamInfo.select("div.cb-hmscg-tm-nm").text();
                String bowlTeamScore = bowlTeamInfo.select("div.cb-hmscg-tm-nm+div").text();
                String textLive = match.select("div.cb-text-live").text();
                String textComplete = match.select("div.cb-text-complete").text();
                String matchLink = match.select("a.cb-lv-scrs-well.cb-lv-scrs-well-live").attr("href").toString();
                String finalLink = "https://www.cricbuzz.com/" + matchLink;
                Match match1 = new Match();

                match1.setTeamHeading(teamsHeading);
                match1.setMatchNumberVenue(matchNumberVenue);

                match1.setLiveText(textLive);
                match1.setMatchLink(finalLink);
                match1.setTextComplete(textComplete);
                if (textComplete.isEmpty()) {
                    match1.setBattingTeam(battingTeam);
                    match1.setBattingTeamScore(score);
                    match1.setBowlTeam(bowlTeam);
                    match1.setBowlTeamScore(bowlTeamScore);
                } else {
                    match1.setBattingTeam(bowlTeam);
                    match1.setBattingTeamScore(bowlTeamScore);
                    match1.setBowlTeam(battingTeam);
                    match1.setBowlTeamScore(score);
                }

                if(!battingTeam.isEmpty()) {
                    matches.add(match1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return matches;
    }
}
