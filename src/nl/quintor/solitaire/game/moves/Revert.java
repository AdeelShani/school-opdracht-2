package nl.quintor.solitaire.game.moves;

import nl.quintor.solitaire.game.moves.ex.MoveException;
import nl.quintor.solitaire.models.state.GameState;

public class Revert implements RevertibleMove {
    private final static String name = System.getProperty("os.name").contains("Windows") ? "Revert move" : "Revert move";
    @Override
    public String revert(GameState gameState) {
        return null;
    }

    @Override
    public String apply(GameState gameState) throws MoveException {
        return null;
    }

    @Override
    public Move createInstance(String playerInput) {
        return null;
    }
}
