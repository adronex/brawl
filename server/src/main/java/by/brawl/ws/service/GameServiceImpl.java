package by.brawl.ws.service;

import by.brawl.util.Exceptions;
import by.brawl.entity.IdEntity;
import by.brawl.entity.Spell;
import by.brawl.entity.Squad;
import by.brawl.ws.dto.JsonDto;
import by.brawl.ws.dto.MessageDto;
import by.brawl.ws.newdto.BattlefieldDto;
import by.brawl.ws.newdto.MulliganDto;
import by.brawl.ws.pojo.BattlefieldHolder;
import by.brawl.ws.pojo.GameState;
import by.brawl.ws.pojo.HeroHolder;
import by.brawl.ws.spell.SpellLogic;
import by.brawl.ws.spell.SuckerPunch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

	private static final Logger LOG = LoggerFactory.getLogger(GameServiceImpl.class);

	private BattlefieldHolder battlefieldHolder = new BattlefieldHolder();
	private GameState gameState = GameState.NOT_STARTED;

	private Map<String, SpellLogic> spellsPool = new HashMap<>();

	private static final Integer BATTLEFIELD_HEROES_COUNT = 2;

	@PostConstruct
	public void init() {
		SpellLogic suckerPunch = new SuckerPunch();
		spellsPool.put(suckerPunch.getId(), suckerPunch);
	}

	@Override
	public void createTwoPlayersGame(WebSocketSession firstSession, Squad firstSquad,
									 WebSocketSession secondSession, Squad secondSquad) {

		if (!GameState.NOT_STARTED.equals(gameState)) {
			String message = MessageFormat.format("Illegal game state. Expected: {0}, actual: {1}",
					GameState.NOT_STARTED, gameState);
			throw Exceptions.produceIllegalState(message, LOG);
		}
		battlefieldHolder.addSquad(firstSession, firstSquad);
		battlefieldHolder.addSquad(secondSession, secondSquad);
		gameState = GameState.MULLIGAN;
		sendMulliganData();
	}

	@Override
	public void setHeroesPositions(WebSocketSession session, List<String> heroesIds) {
		if (!GameState.MULLIGAN.equals(gameState)) {
			String message = MessageFormat.format("Illegal game state. Expected: {0}, actual: {1}",
					GameState.MULLIGAN, gameState);
			throw Exceptions.produceIllegalState(message, LOG);
		}
		if (heroesIds.size() != BATTLEFIELD_HEROES_COUNT) {
			String message = MessageFormat.format("Wrong heroes in battle count. Expected: {0}, actual: {1}",
					BATTLEFIELD_HEROES_COUNT, heroesIds.size());
			throw Exceptions.produceIllegalArgument(message, LOG);
		}
		String playerKey = session.getPrincipal().getName();

		List<HeroHolder> mulliganHeroes = battlefieldHolder.getMulliganHeroes().get(playerKey);
		List<HeroHolder> battleHeroes = new ArrayList<>();

		for (String heroId : heroesIds) {
			HeroHolder battleHero = mulliganHeroes.stream()
					.filter(h -> h.getId().equals(heroId))
					.findFirst()
					.orElseThrow(() -> {
						String message = MessageFormat.format("Player {0} trying to choose hero with id {1} from available ids: {2}",
								session.getPrincipal().getName(), heroId, Arrays.toString(mulliganHeroes.stream().map(HeroHolder::getId).toArray()));
						return Exceptions.produceAccessDenied(message, LOG);
					});

			battleHeroes.add(battleHero);
		}

		battlefieldHolder.getBattleHeroes().put(playerKey, battleHeroes);

		if (battlefieldHolder.getBattleHeroes().size() == 2) {
			battlefieldHolder.prepareGame();
			gameState = GameState.PLAYING;
			sendBattlefieldData();
		} else {
			sendDto(session, new MessageDto("Opponent is still choosing."));
		}
	}

	@Override
	public void castSpell(WebSocketSession session, String spellId, Integer target, Boolean enemy) {
		if (!GameState.PLAYING.equals(gameState)) {
			String message = MessageFormat.format("Illegal game state. Expected: {0}, actual: {1}",
					GameState.PLAYING, gameState);
			throw Exceptions.produceIllegalState(message, LOG);
		}
		String playerKey = session.getPrincipal().getName();
		String currentHeroId = battlefieldHolder.getQueue().element();
		// check for your hero or not
		HeroHolder currentHero = battlefieldHolder.getBattleHeroes().get(playerKey)
				.stream()
				.filter(h -> h.getId().equals(currentHeroId))
				.findFirst()
				.orElseThrow(() -> {
					String message = MessageFormat.format("Player {0} tries to make own turn in enemy's one.",
							playerKey);
					return Exceptions.produceIllegalArgument(message, LOG);
				});
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
					String message = MessageFormat.format("Player {0} casted spell {1} when available spells are {2}.",
							playerKey, spellId, Arrays.asList(availableSpellsIds.toArray()));
					return Exceptions.produceIllegalArgument(message, LOG);
				});
		SpellLogic castedSpell = spellsPool.get(castedSpellId.getId());
		// check target for validity
		Boolean cannotBeTargeted = (target == null && !castedSpell.getTargetable());
		Boolean validMyTarget = target != null && enemy != null && !enemy && castedSpell.getMyTargets().contains(target);
		Boolean validEnemyTarget = target != null && enemy != null && enemy && castedSpell.getEnemyTargets().contains(target);
		if (!cannotBeTargeted && !validMyTarget && !validEnemyTarget) {
			String message = MessageFormat.format("Targeting error. Can't be targeted: {0}, valid my target: {1}, valid enemy target: {2}",
					cannotBeTargeted, validEnemyTarget, validEnemyTarget);
			throw Exceptions.produceIllegalArgument(message, LOG);
		}
		// todo: cast spells right here
		battlefieldHolder.moveQueue();
		sendBattlefieldData();
	}

	private void sendMulliganData() {
		battlefieldHolder.getSessions().forEach((key, value) -> {
			MulliganDto dto = new MulliganDto(
					battlefieldHolder.getMulliganHeroes(),
					battlefieldHolder.getHeroSpells(),
					key
			);
			sendDto(value, dto);
		});
	}

	private void sendBattlefieldData() {
		battlefieldHolder.getSessions().forEach((key, value) -> {
			BattlefieldDto dto = new BattlefieldDto(battlefieldHolder, key);
			sendDto(value, dto);
		});
	}

	private void sendDto(WebSocketSession session, JsonDto dto) {
		try {
			session.sendMessage(new TextMessage(dto.asJson()));
		} catch (IOException e) {
			Exceptions.logError("Web socket message sending threw error.", e, LOG);
		}
	}
}