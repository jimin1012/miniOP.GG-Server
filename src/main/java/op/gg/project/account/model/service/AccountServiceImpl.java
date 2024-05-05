package op.gg.project.account.model.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import op.gg.project.account.model.dto.Account;
import op.gg.project.summoner.model.dto.Summoner;

@Service
public class AccountServiceImpl implements AccountService{

	@Override
	public void accountPuuid(Summoner summoner, String puuid) {
		Account account = new Account();
		String apiUrl = "https://asia.api.riotgames.com/riot/account/v1/accounts/by-puuid/"+puuid+"?api_key="+account.getApiKey();
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

			summoner.setGameName(summoner2.getGameName());
			summoner.setTagLine(summoner2.getTagLine());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
