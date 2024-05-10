package op.gg.project.summoner.model.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import op.gg.project.account.model.dto.Account;
import op.gg.project.summoner.model.dto.Summoner;

@Service
public class SummonerServiceImpl implements SummonerService{

	@Override
	public void summonerEncryptedSummonerId(Summoner summoner,String summonerId) {

		Account account = new Account();
		String apiUrl = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/"+summonerId+"?api_key="+account.getApiKey();
		StringBuffer response = new StringBuffer();

		Summoner summoner2;
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

			Gson gson = new Gson();
			summoner2 = gson.fromJson(response.toString(), Summoner.class);

			summoner.setProfileIconId(summoner2.getProfileIconId());
			summoner.setPuuid(summoner2.getPuuid());
			summoner.setSummonerLevel(summoner2.getSummonerLevel());
		} catch (Exception e) {
			e.printStackTrace();
		}


	}

	// 소환사 ID 얻어옴
	@Override
	public Map<String, Object> getSummonerId(String puuid) {
		
		Account account = new Account();
		Map<String, Object> map = new HashMap<String, Object>();
		String apiUrl = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-puuid/"+puuid+"?api_key="+account.getApiKey();
		 try {
	            URL url = new URL(apiUrl);
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            conn.setRequestMethod("GET");

	            int responseCode = conn.getResponseCode();
	            System.out.println("Response Code : " + responseCode);
	            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	            String inputLine;
	            StringBuffer response = new StringBuffer();

	            while ((inputLine = in.readLine()) != null) {
	                response.append(inputLine);
	            }
	            in.close();

	            // 출력
	            System.out.println(response.toString());
	            
	            Gson gson = new Gson();
	            JsonObject jsonObject = gson.fromJson(response.toString(), JsonObject.class);
	            
	            String summonerId = jsonObject.get("id").getAsString();
	            int profileIconId = jsonObject.get("profileIconId").getAsInt();
	            int summonerLevel = jsonObject.get("summonerLevel").getAsInt();
	            
	            
	            map.put("summonerId", summonerId);
	            map.put("responseCode", responseCode);
	            map.put("profileIconId", profileIconId);
	            map.put("summonerLevel", summonerLevel);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		
		return map;
	}

}
