package by.brawl.ws.service;

import by.brawl.entity.Squad;
import by.brawl.util.Exceptions;
import by.brawl.ws.holder.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
class GameServiceImpl implements GameService {

	private static final Logger LOG = LoggerFactory.getLogger(GameServiceImpl.class);
	private static final Integer BATTLEFIELD_HEROES_COUNT = 2;
	@Autowired
	private GameSessionsPool gameSessionsPool;
	@Autowired
	private SpellCastService spellCastService;

	@Override
	public void createTwoPlayersGame(GameSession firstSession, Squad firstSquad,
									 GameSession secondSession, Squad secondSquad) {
		BattlefieldHolder battlefieldHolder = new BattlefieldHolder();

		battlefieldHolder.addSquad(firstSquad);
		battlefieldHolder.addSquad(secondSquad);
		firstSession.setBattlefieldHolder(battlefieldHolder);
		secondSession.setBattlefieldHolder(battlefieldHolder);
		battlefieldHolder.setGameState(GameState.MULLIGAN);
		gameSessionsPool.sendMulliganData(battlefieldHolder);
	}

	@Override
	public void setHeroesPositions(GameSession session, List<String> heroesIds) {
		BattlefieldHolder battlefieldHolder = getBattlefieldHolderAndCheckState(session, GameState.MULLIGAN);

		if (heroesIds.size() != BATTLEFIELD_HEROES_COUNT) {
			throw Exceptions.produceIllegalArgument(LOG, "Wrong heroes in battle count. Expected: {0}, actual: {1}",
					BATTLEFIELD_HEROES_COUNT, heroesIds.size());
		}

		List<HeroHolder> mulliganHeroes = battlefieldHolder.getMulliganHeroes().get(session.getId());
		List<HeroHolder> battleHeroes = new ArrayList<>();

		for (String heroId : heroesIds) {
			HeroHolder battleHero = mulliganHeroes.stream()
					.filter(h -> h.getId().equals(heroId))
					.findFirst()
					.orElseThrow(() -> Exceptions.produceAccessDenied(LOG, "Player {0} trying to choose hero with id {1} from available ids: {2}",
								session.getId(), heroId, Arrays.toString(mulliganHeroes.stream().map(HeroHolder::getId).toArray()))
					);

			battleHeroes.add(battleHero);
		}

		battlefieldHolder.getBattleHeroes().put(session.getId(), battleHeroes);

		if (battlefieldHolder.getBattleHeroes().size() == 2) {
			battlefieldHolder.prepareGame();
			battlefieldHolder.setGameState(GameState.PLAYING);
			gameSessionsPool.sendBattlefieldData(battlefieldHolder);
		} else {
			session.sendInfoMessage("Opponent is still choosing.");
		}
	}

	@Override
	public void castSpell(GameSession session, String spellId, Integer target, Boolean enemy) {
		BattlefieldHolder battlefieldHolder = getBattlefieldHolderAndCheckState(session, GameState.PLAYING);

		String playerKey = session.getId();
		HeroHolder currentHero = battlefieldHolder.getQueue().element();

		Boolean castedSpellBelongsToCurrentHero = currentHero.getAllSpells().stream()
				.filter(spellHolder -> Objects.equals(spellHolder.getId(), spellId))
				.count() > 0;
		if (!castedSpellBelongsToCurrentHero) {
			Object availableSpellsIds[] = currentHero.getAllSpells().stream().map(SpellHolder::getId).toArray();
			throw Exceptions.produceIllegalArgument(LOG, "Player {0} casted spell {1} that does not belong to current hero. Available spells are {2}",
					playerKey, spellId, availableSpellsIds);
		}
		Boolean castedSpellAvailable = currentHero.getAvailableSpells().stream()
				.filter(spellHolder -> Objects.equals(spellHolder.getId(), spellId))
				.count() > 0;
		if (!castedSpellAvailable) {
			List<String> availableSpellsIds = currentHero.getAvailableSpells().stream()
					.map(SpellHolder::getId)
					.collect(Collectors.toList());
			throw Exceptions.produceIllegalArgument(LOG, "Player {0} casted spell {1} that is not available (on cooldown/suspended/etc). Available spells are {2}",
					playerKey, spellId, availableSpellsIds.toString());
		}

		battlefieldHolder = spellCastService.castSpell(spellId, session.getId(), currentHero.getId(), target, enemy, battlefieldHolder);
		battlefieldHolder.incrementStep();
		gameSessionsPool.sendBattlefieldData(battlefieldHolder);
	}

	private BattlefieldHolder getBattlefieldHolderAndCheckState(GameSession session, GameState gameState) {
		if (session.getBattlefieldHolder() == null) {
			throw Exceptions.produceNullPointer(LOG, "User {0} tries to play game without preliminary matchmaking", session.getId());
		}
		if (!gameState.equals(session.getBattlefieldHolder().getGameState())) {
			throw Exceptions.produceIllegalState(LOG, "Illegal game state. Expected: {0}, actual: {1}. Initiator: {2}",
					gameState, session.getBattlefieldHolder().getGameState(), session.getId());
		}
		return session.getBattlefieldHolder();
	}
}