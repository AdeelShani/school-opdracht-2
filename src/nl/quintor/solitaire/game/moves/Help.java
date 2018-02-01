package nl.quintor.solitaire.game.moves;

import nl.quintor.solitaire.game.moves.ex.MoveException;
import nl.quintor.solitaire.models.state.GameState;

public class Help implements Move {
    private final static String name = System.getProperty("os.name").contains("Windows") ? "Help" : "Help";

    @Override
    public String apply(GameState gameState) throws MoveException {
        return null;
    }

    @Override
    public Move createInstance(String playerInput) {
        return null;
    }
}
