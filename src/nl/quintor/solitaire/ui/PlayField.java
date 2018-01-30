package nl.quintor.solitaire.ui;

import nl.quintor.solitaire.game.moves.Move;
import nl.quintor.solitaire.models.state.GameState;

import java.util.Collection;
import java.util.Scanner;

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
        Scanner input = new Scanner(System.in);

        this.refresh(gameState);
        System.out.print("What action do you want to take: ");

        return input.nextLine();
    }
}
