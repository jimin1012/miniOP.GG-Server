package op.gg.project.summoner.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import op.gg.project.league.model.dto.LeagueInfo;
import op.gg.project.league.model.service.LeagueService;

@RestController
public class SummonerController {

	
	@Autowired
	private LeagueService leagueService;
	
	@CrossOrigin(origins = "http://localhost")
	@GetMapping("/findLeageInfo")
	@ResponseBody
	public List<LeagueInfo> getLeagueInfoList(String summonerId) {
		
		List<LeagueInfo> leagueInfoList = leagueService.getLeagueInfo(summonerId);
		
		
		return leagueInfoList;
	}
}
