package op.gg.project.summoner.model.service;

import java.util.List;
import java.util.Map;

import op.gg.project.summoner.model.dto.Summoner;

public interface SummonerService {

	void summonerEncryptedSummonerId(Summoner summoner,String summonerId);

	Map<String, Object> getSummonerId(String puuid);

}
