package op.gg.project.league.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import op.gg.project.account.model.dto.Account;
import op.gg.project.account.model.service.AccountService;
import op.gg.project.league.model.dto.LeagueInfo;
import op.gg.project.league.model.service.LeagueService;
import op.gg.project.summoner.model.dto.Match;
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
	
	
	// 메인화면에서 10명의 랭킹 정보만 가져옴
	@CrossOrigin(origins = "http://localhost")
	@GetMapping("/LeagueRanking")
	public List<Summoner> LeagueRanking() {

		System.out.println("asdad");
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
		
		System.out.println(leagueList);
		
		return leagueList;
	}
	

	//소환사 게임 목록
	@CrossOrigin(origins = "http://localhost")
	@GetMapping("/matchList")
	public String getMatchList(String puuid, int page, int count) {

		Account account = new Account();

		System.out.println("page : "+page+"count : "+count);
		System.out.println("puuid : "+puuid);
		
//		List<Match> matchs= new ArrayList<>();
		
		String apiUrl = "https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/"+puuid+"/ids?start="+page * count+"&count="+count+"&api_key="+account.getApiKey();
		StringBuffer response = new StringBuffer();
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

			// 출력
			System.out.println(response.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}


		return response.toString();

	}
	
	// 소환사가 한 경기의 디테일정보
	@CrossOrigin(origins = "http://localhost")
	@GetMapping("/matchDetail")
	public ResponseEntity<String> getMatchDetail(String matchId) {
		
		Account account = new Account();
		
		String apiUrl = "https://asia.api.riotgames.com/lol/match/v5/matches/"+matchId+"?api_key="+account.getApiKey();
		StringBuffer response = new StringBuffer();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8); // 또는 APPLICATION_JSON으로 설정하고 별도로 charset=UTF-8을 명시
		try {
			URL url = new URL(apiUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			int responseCode = conn.getResponseCode();
			if (responseCode == 429) {
			    String retryAfter = conn.getHeaderField("Retry-After");
			    long retryAfterSeconds = Long.parseLong(retryAfter);
			    // retryAfterSeconds 만큼 대기 후 재시도 로직
			}
			System.out.println("Response Code : " + responseCode);
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			
			if (responseCode != HttpURLConnection.HTTP_OK) {
			    // 에러 로그 찍기 또는 에러 메시지 반환
			    System.out.println("Request Failed. HTTP Error Code: " + responseCode);
			    return null; // 또는 적절한 에러 메시지 반환
			}

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// 출력
		

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
//		return response.toString();
		return new ResponseEntity<>(response.toString(), headers, HttpStatus.OK);
	}
	
	
	// 랭킹페이지에서 랭킹 목록 가져옴
	@CrossOrigin(origins = "http://localhost")
	@GetMapping("/getSoloRankInfo")
	public  Summoner[] getSoloRankInfo(String tier, String division, int page) {
		
		
		System.out.println("데헷입니다");
		
		System.out.println(tier);
		System.out.println(division);
		System.out.println(page);
		Account account = new Account();
		String url = String.format("https://kr.api.riotgames.com/lol/league-exp/v4/entries/RANKED_SOLO_5x5/%s/%s?page=%d&api_key=%s", 
				tier, division, page, account.getApiKey());
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Summoner[]> response = restTemplate.getForEntity(url, Summoner[].class);

		Summoner[] challengers = response.getBody();
//		System.out.println(Arrays.copyOfRange(challengers, (page - 1) * 100, page * 100));
		
		/*
		 * Account account = new Account(); Summoner summoner = new Summoner();
		 * 
		 * String apiUrl =
		 * "https://kr.api.riotgames.com/lol/league-exp/v4/entries/RANKED_SOLO_5x5/CHALLENGER/I?page="
		 * +page+"&api_key="+account.getApiKey(); StringBuilder response = new
		 * StringBuilder();
		 * 
		 * List<Summoner> leagueList = new ArrayList<Summoner>(); try { URL url = new
		 * URL(apiUrl); HttpURLConnection conn = (HttpURLConnection)
		 * url.openConnection(); conn.setRequestMethod("GET");
		 * 
		 * int responseCode = conn.getResponseCode();
		 * System.out.println("Response Code : " + responseCode); BufferedReader in =
		 * new BufferedReader(new InputStreamReader(conn.getInputStream())); String
		 * inputLine;
		 * 
		 * 
		 * while ((inputLine = in.readLine()) != null) { response.append(inputLine); }
		 * in.close();
		 * 
		 * // Gson 라이브러리를 사용하여 JSON 응답에서 소환사 ID 추출 Gson gson = new Gson(); Type listType
		 * = new TypeToken<List<Summoner>>(){}.getType(); List<Summoner>
		 * leagueInfoAllList = gson.fromJson(response.toString(), listType);
		 * 
		 * 
		 * // 처음 10명의 소환사 ID만 출력 for (int i = 0; i < Math.min(leagueInfoAllList.size(),
		 * 100); i++) { summoner = leagueInfoAllList.get(i);
		 * summonerService.summonerEncryptedSummonerId(summoner,summoner.getSummonerId()
		 * ); accountService.accountPuuid(summoner,summoner.getPuuid());
		 * leagueList.add(summoner); }
		 * 
		 * } catch (Exception e) { e.printStackTrace(); }
		 */
		int start = Math.max((page - 1) * 100, 0); // 시작 인덱스가 음수가 되지 않도록 보장
	    int end = Math.min(page * 100, challengers.length); // 종료 인덱스가 배열 길이를 초과하지 않도록 보장
	    
	    // start가 end보다 크면, 올바르지 않은 범위이므로 빈 배열 반환
	    if (start >= end) {
	        return new Summoner[0];
	    }
//		return Arrays.copyOfRange(challengers, start, end);
	    return response.getBody();
	}
	
}

