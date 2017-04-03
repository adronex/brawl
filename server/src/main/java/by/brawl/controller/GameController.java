package by.brawl.controller;

import org.springframework.stereotype.Controller;

/**
 * Default class description.
 *
 * @author P.Sinitsky.
 *         Created on 26.03.2017.
 *
 * Turn depends on speed queue - if your character first then your turn
 * State: mulligan / play / first win / second win / draw
 * Input parameters: spell id + targets array (can be empty)
 * Output parameters: state + queue move + last spell + history + add spell to enemy if triggered + changes
 *
 * 1. Put ticket for playing (input)
 * 2. Find opponent (server)
 * 3. Create room (server)
 * 4. Send data for mulligan (output)
 * 5. Waiting for mulligan decisions (input)
 * 6. Build queue (server)
 * 7. Send data - queue + character positions + character states (output)
 *
 * THE GAME IN PLAY STATE NOW
 */
public class GameController {
}
