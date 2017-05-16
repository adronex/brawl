package by.brawl.ws.service;

import by.brawl.entity.IdEntity;
import by.brawl.entity.Spell;
import by.brawl.entity.Squad;
import by.brawl.util.Exceptions;
import by.brawl.ws.holder.BattlefieldHolder;
import by.brawl.ws.holder.GameSession;
import by.brawl.ws.holder.GameSessionsPool;
import by.brawl.ws.holder.GameState;
import by.brawl.ws.holder.HeroHolder;
import by.brawl.ws.spell.SpellLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
class GameServiceImpl implements GameService {

	private static final Logger LOG = LoggerFactory.getLogger(GameServiceImpl.class);

	//private BattlefieldHolder battlefieldHolder = new BattlefieldHolder();

	@Autowired
	private GameSessionsPool gameSessionsPool;
	private static final Integer BATTLEFIELD_HEROES_COUNT = 2;

	@Override
	public void createTwoPlayersGame(GameSession firstSession, Squad firstSquad,
									 GameSession secondSession, Squad secondSquad) {
		BattlefieldHolder battlefieldHolder = new BattlefieldHolder();

//		if (!GameState.NOT_STARTED.equals(battlefieldHolder.getGameState())) {
//			throw Exceptions.produceIllegalState(LOG, "Illegal game state. Expected: {0}, actual: {1}",
//					GameState.NOT_STARTED, battlefieldHolder.getGameState());
//		}
		battlefieldHolder.addSquad(firstSquad);
		battlefieldHolder.addSquad(secondSquad);
		firstSession.setBattlefieldHolder(battlefieldHolder);
		secondSession.setBattlefieldHolder(battlefieldHolder);
		battlefieldHolder.setGameState(GameState.MULLIGAN);
		gameSessionsPool.sendMulliganData(battlefieldHolder);
	}

	@Override
	public void setHeroesPositions(GameSession session, List<String> heroesIds) {
		BattlefieldHolder battlefieldHolder = getBattlefieldHolder(session);

		if (!GameState.MULLIGAN.equals(battlefieldHolder.getGameState())) {
			throw Exceptions.produceIllegalState(LOG, "Illegal game state. Expected: {0}, actual: {1}",
					GameState.MULLIGAN, battlefieldHolder.getGameState());
		}
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
		BattlefieldHolder battlefieldHolder = getBattlefieldHolder(session);
		String playerKey = session.getId();
		if (!GameState.PLAYING.equals(battlefieldHolder.getGameState())) {
			throw Exceptions.produceIllegalState(LOG, "Illegal game state. Expected: {0}, actual: {1}. Initiator: {2}",
					GameState.PLAYING, battlefieldHolder.getGameState(), playerKey);
		}
		String currentHeroId = battlefieldHolder.getQueue().element();
		// check for your hero or not
		HeroHolder currentHero = battlefieldHolder.getBattleHeroes().get(playerKey)
				.stream()
				.filter(h -> h.getId().equals(currentHeroId))
				.findFirst()
				.orElseThrow(() -> Exceptions.produceIllegalArgument(
						LOG, "Player {0} tries to make own turn in enemy's one.", playerKey)
				);
		// check for spell validity (if hero has it)
		Spell castedSpellId = battlefieldHolder.getHeroSpells().get(currentHeroId)
				.stream()
				.filter(s -> s.getId().equals(spellId))
				.findAny()
				.orElseThrow(() -> {
					List<String> availableSpellsIds = battlefieldHolder.getHeroSpells().get(currentHeroId)
							.stream()
							.map(IdEntity::getId)
							.collect(Collectors.toList());
					return Exceptions.produceIllegalArgument(LOG, "Player {0} casted spell {1} when available spells are {2}",
							playerKey, spellId, Arrays.asList(availableSpellsIds.toArray()));
				});
		SpellLogic castedSpell = battlefieldHolder.getSpellsPool().get(castedSpellId.getId());
		// check target for validity
		if (castedSpell == null) {
			throw Exceptions.produceNullPointer(LOG, "Casted spell is absent in spells pool");
		}
		Boolean cannotBeTargeted = (target == null && !castedSpell.getTargetable());
		Boolean validMyTarget = target != null && enemy != null && !enemy && castedSpell.getMyTargets().contains(target);
		Boolean validEnemyTarget = target != null && enemy != null && enemy && castedSpell.getEnemyTargets().contains(target);
		if (!cannotBeTargeted && !validMyTarget && !validEnemyTarget) {
			throw Exceptions.produceIllegalArgument(LOG, "Targeting error. Can't be targeted: {0}, valid my target: {1}, valid enemy target: {2}",
					cannotBeTargeted, validEnemyTarget, validEnemyTarget);
		}
		// todo: cast spells right here
		battlefieldHolder.moveQueue();
		gameSessionsPool.sendBattlefieldData(battlefieldHolder);
	}

	private BattlefieldHolder getBattlefieldHolder(GameSession session) {
		if (session.getBattlefieldHolder() == null) {
			throw Exceptions.produceNullPointer(LOG, "User {0} tries to play game without preliminary matchmaking", session.getId());
		}
		return session.getBattlefieldHolder();
	}
}