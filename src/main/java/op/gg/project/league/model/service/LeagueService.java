package op.gg.project.league.model.service;

import java.util.List;
import java.util.Map;

import op.gg.project.league.model.dto.LeagueInfo;

public interface LeagueService {

	List<LeagueInfo> getLeagueInfo(String summonerId);

}
