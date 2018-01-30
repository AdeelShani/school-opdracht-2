package nl.quintor.solitaire.ui;

import nl.quintor.solitaire.game.moves.Move;
import nl.quintor.solitaire.models.state.GameState;

import java.util.Collection;

public class PlayField implements nl.quintor.solitaire.ui.UI {

    @Override
    public void setMessage(String message) {

    }

    @Override
    public void setErrorMessage(String message) {

    }

    @Override
    public void refresh(GameState gameState) {
        System.out.println(gameState.toString());
    }

    @Override
    public String refreshAndRequestMove(GameState gameState, Collection<Move> moves) {
        this.refresh(gameState);
        return null;
    }
}
