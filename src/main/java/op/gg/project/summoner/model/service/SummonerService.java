package op.gg.project.summoner.model.service;

import java.util.List;

import op.gg.project.summoner.model.dto.Summoner;

public interface SummonerService {

	void summonerEncryptedSummonerId(Summoner summoner,String summonerId);

}
