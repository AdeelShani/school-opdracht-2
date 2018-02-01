package nl.quintor.solitaire.game.moves;

import nl.quintor.solitaire.game.moves.ex.MoveException;
import nl.quintor.solitaire.models.deck.Deck;
import nl.quintor.solitaire.models.state.GameState;

/**
 * Class that represents a player action to Cycle the Deck.
 */
public class CycleStock implements Move {
    @Override
    public String apply(GameState gameState) throws MoveException {
        Deck stock      = gameState.getStock();
        int  stockCycle = gameState.getStockCycles() + 1;
        stockCycle = stock.size() < stockCycle ? 0 : stockCycle;
        gameState.setStockCycles(stockCycle);
        return null;
    }

    @Override
    public Move createInstance(String playerInput) {
        return null;
    }
}
