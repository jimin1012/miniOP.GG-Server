package op.gg.project.summoner.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Summoner {

	private String puuid;
	private int profileIconId; //프로필id
	private int summonerLevel; // 레벨
	private String gameName;// 닉네임
	private String tagLine; // 태그네임 ex) #jimin
	
	private int responseCode;// api 요청상태 // 200이면 정상작동
	
	// 랭킹에 드갈꺼
	private String tier; // 롤 티어
	private int wins; // 승리 수
	private int losses; // 패배 수
	private int leaguePoints; //LP
	
	
	// 리그 정보
	private String leagueId;
	private String queueType;
	private String rank;
	private String summonerId;
	private boolean veteran;
	private boolean inactive;
	private boolean freshBlood;
	private boolean hotStreak;
}
