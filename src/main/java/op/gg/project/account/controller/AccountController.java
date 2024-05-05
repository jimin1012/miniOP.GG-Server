package op.gg.project.account.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import op.gg.project.account.model.dto.Account;

@RestController
public class AccountController {
	

	/** 유저검색 컨트롤러
	 * @param nickName
	 * @param tag
	 * @return
	 */
	//지금 cors 해결방법 제대로 못찾아서 이렇게 일일히 하나하나 넣는 중
	@CrossOrigin(origins = "http://localhost")
	@GetMapping("/profile")
	public String getPuuid(String nickName, String tag) {
		
		System.out.println("ㅎㅇ");
		System.out.println(nickName);
		System.out.println(tag);
		
		Account account = new Account();
		
		String apiUrl = account.getUrl()+"/riot/account/v1/accounts/by-riot-id/"+nickName+"/"+tag+"?api_key="+account.getApiKey();
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

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		
		return "a";
	}
	
	

	/** 챔피언로테이션 컨트롤러
	 * @return
	 */
	//지금 cors 해결방법 제대로 못찾아서 이렇게 일일히 하나하나 넣는 중
	@CrossOrigin(origins = "http://localhost")
	@GetMapping("/championRotationList")
	public String getChampionRotationList() {

		Account account = new Account();
		
		String apiUrl = "https://kr.api.riotgames.com/lol/platform/v3/champion-rotations?api_key="+account.getApiKey();
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
	
}
