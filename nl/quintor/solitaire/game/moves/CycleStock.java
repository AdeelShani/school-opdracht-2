/**
 * @author Adnan Akbas 17005116
 * @author Karam Es-Sabri 15098796
 * @author Adeel Ahmad Shani Haq 17019060
 */
package nl.quintor.solitaire.game.moves;

import nl.quintor.solitaire.game.moves.ex.MoveException;
import nl.quintor.solitaire.models.deck.Deck;
import nl.quintor.solitaire.models.state.GameState;

/**
 * Class that represents a player action to Cycle the Deck.
 */
public class CycleStock implements Move {
    private final static String name = System.getProperty("os.name").contains("Windows") ? "Cycle Stock" : "Cycle Stock";

    @Override
    public String apply(GameState gameState)  {
        Deck stock      = gameState.getStock();
        int  stockCycle = gameState.getStockCycles() + 1;
        stockCycle = stock.size() < stockCycle ? 0 : stockCycle;
        gameState.setStockCycles(stockCycle);
        return "Stock is cycled";
    }

    @Override
    public Move createInstance(String playerInput) {
        return new CycleStock();
    }
}
