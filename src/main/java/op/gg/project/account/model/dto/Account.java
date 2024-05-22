package op.gg.project.account.model.dto;

import java.util.List;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Account {

	private String apiKey = "RGAPI-956baa76-3815-4961-adcf-7e0afb37b550"; // 라이엇 기본제공 개발용 키 24시간 마다 바꿔야함
//	private String apiKey = "RGAPI-34d74300-b044-4e5d-b177-cd9bdd775bfa"; 기존 내가 받은 mini op.gg 개발 앱키
	private String url = "https://asia.api.riotgames.com";
}
