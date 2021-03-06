/**
 * @author Adnan Akbas 17005116
 * @author Karam Es-Sabri 15098796
 * @author Adeel Ahmad Shani Haq 17019060
 */
package nl.quintor.solitaire;

import nl.quintor.solitaire.game.moves.*;
import nl.quintor.solitaire.game.moves.ex.MoveException;
import nl.quintor.solitaire.models.state.GameState;
import nl.quintor.solitaire.ui.PlayField;
import nl.quintor.solitaire.ui.UI;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


/**
 * Application entry point
 */
public class Main {
    /**
     * Application entry point. Consists of three phases: initialization, game loop and game shutdown. During the
     * initialization phase, the UI, game state and possible moves are created. The game loop is entered, which runs as
     * long as the game is not over. The game loop essentially consists of:
     * <p>
     * <p><ul>
     * <li>visualize GameState object
     * <li>request input
     * <li>translate input into a Move
     * <li>apply the Move to the GameState object
     * <li>communicate the result to the player
     * </ul></p>
     * <p>
     * When the game loop exits, the result of the game must be shown to the player and the UI is refreshed one final
     * time.
     */
    public static void main(String... args) {
        // initialize the GameState, UI and all possible moves
        UI                    ui            = new PlayField();
        GameState             gameState     = new GameState();
        List<String>          keys          = Arrays.asList("C", "M", "H", "Q");
        List<Move>            moves         = Arrays.asList(new CycleStock(), new MoveCard(), new Help(), new Quit());
        HashMap<String, Move> possibleMoves = new HashMap<>();
        for (int i = 0; i < keys.size(); i++) possibleMoves.put(keys.get(i), moves.get(i));

        try {
            String instructions = possibleMoves.get("H").createInstance("*").apply(gameState);
            ui.setMessage(instructions);
        } catch (Exception e) {

        }
        // game loop
        while (!gameState.isGameOver()) {
            String playerInput = ui.refreshAndRequestMove(gameState, moves).toUpperCase();
           // try {
                Move move = possibleMoves
                        .getOrDefault(playerInput.substring(0, 1), null)
                        .createInstance(playerInput);
                try {
                    ui.setMessage(move.apply(gameState));
                } catch (MoveException e) {
                    ui.setErrorMessage(e.getMessage());
                }
          /*  } catch (Exception e) {
                ui.setErrorMessage(e.getMessage());

            }*/

        }

        if (gameState.isGameWon()) {
            ui.setMessage("Congratulations, you beat the game!!! " + gameState.toString());
        }
        ui.refresh(gameState);
    }
}
