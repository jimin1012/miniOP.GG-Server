package op.gg.project.league.model.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import op.gg.project.account.model.dto.Account;
import op.gg.project.league.model.dto.LeagueInfo;

@Service
public class LeagueServiceImpl implements LeagueService{

	@Override
	public List<LeagueInfo> getLeagueInfo(String summonerId) {

		Account account = new Account();

		List<LeagueInfo> leagueInfos = new ArrayList<>();
		String apiUrl = "https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/"+summonerId+"?api_key="+account.getApiKey();
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
			JsonArray jsonArray = gson.fromJson(response.toString(), JsonArray.class);

			

			for (JsonElement element : jsonArray) {
				JsonObject jsonObject = element.getAsJsonObject();

				LeagueInfo info = new LeagueInfo();

				String leagueId = jsonObject.get("leagueId").getAsString();
				String queueType = jsonObject.get("queueType").getAsString();
				String tier = jsonObject.get("tier").getAsString();
				String rank = jsonObject.get("rank").getAsString();
				int leaguePoints = jsonObject.get("leaguePoints").getAsInt();
				int wins = jsonObject.get("wins").getAsInt();
				int losses = jsonObject.get("losses").getAsInt();
				boolean veteran = jsonObject.get("veteran").getAsBoolean();
				boolean inactive = jsonObject.get("inactive").getAsBoolean();
				boolean freshBlood = jsonObject.get("freshBlood").getAsBoolean();
				boolean hotStreak = jsonObject.get("hotStreak").getAsBoolean();

				info.setLeagueId(leagueId);
				info.setQueueType(queueType);
				info.setTier(tier);
				info.setRank(rank);
				info.setLeaguePoints(leaguePoints);
				info.setWins(wins);
				info.setLosses(losses);
				info.setVeteran(veteran);
				info.setInactive(inactive);
				info.setFreshBlood(freshBlood);
				info.setHotStreak(hotStreak);

				leagueInfos.add(info);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return leagueInfos;
	}

}
