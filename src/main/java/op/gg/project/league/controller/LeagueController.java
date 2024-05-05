package op.gg.project.league.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import op.gg.project.account.model.dto.Account;
import op.gg.project.account.model.service.AccountService;
import op.gg.project.league.model.dto.LeagueInfo;
import op.gg.project.league.model.service.LeagueService;
import op.gg.project.summoner.model.dto.Summoner;
import op.gg.project.summoner.model.service.SummonerService;

@RestController
public class LeagueController {

	@Autowired
	private LeagueService legueService;
	
	@Autowired 
	private SummonerService summonerService;
	
	@Autowired
	private AccountService accountService;
	
	@CrossOrigin(origins = "http://localhost")
	@GetMapping("/LeagueRanking")
	public List<Summoner> LeagueRanking() {


		Account account = new Account();
		Summoner summoner = new Summoner();
		
		String apiUrl = "https://kr.api.riotgames.com/lol/league-exp/v4/entries/RANKED_SOLO_5x5/CHALLENGER/I?page=1&api_key="+account.getApiKey();
		StringBuilder response = new StringBuilder();
		
		List<Summoner> leagueList = new ArrayList<Summoner>();
		try {
			URL url = new URL(apiUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			int responseCode = conn.getResponseCode();
			System.out.println("Response Code : " + responseCode);
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;


			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// Gson 라이브러리를 사용하여 JSON 응답에서 소환사 ID 추출
			Gson gson = new Gson();
			Type listType = new TypeToken<List<Summoner>>(){}.getType();
			List<Summoner> leagueInfoAllList = gson.fromJson(response.toString(), listType);

			
			// 처음 10명의 소환사 ID만 출력
			for (int i = 0; i < Math.min(leagueInfoAllList.size(), 10); i++) {
				summoner = leagueInfoAllList.get(i);
				summonerService.summonerEncryptedSummonerId(summoner,summoner.getSummonerId());
				accountService.accountPuuid(summoner,summoner.getPuuid());
				leagueList.add(summoner);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return leagueList;
	}
}
