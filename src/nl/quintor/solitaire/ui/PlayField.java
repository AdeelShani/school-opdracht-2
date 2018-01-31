package nl.quintor.solitaire.ui;

import nl.quintor.solitaire.game.moves.Move;
import nl.quintor.solitaire.models.card.Card;
import nl.quintor.solitaire.models.deck.Deck;
import nl.quintor.solitaire.models.state.GameState;

import java.sql.Array;
import java.util.Collection;
import java.util.Map;
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
        this.printColumns(gameState);


    }

    private void printStockAndStackPiles(){
        String[] linesToPrint = {};
    }

    private void printColumns(GameState gameState) {
        Map<String, Deck> columns = gameState.getColumns();
        String[] columnNames = GameState.columnNames;
        int maxRows = 0;
        //@todo try to find a solution without for loop for this
        for (int i = 0; i < columnNames.length; i++) {
            System.out.print("\t" + columnNames[i] + "\t");
        }
        System.out.println("");
        //@todo find better alternative for this code to count maxNumber
        for (int i = 0; i < columnNames.length; i++) {
            String key = columnNames[i];
            Deck deck = columns.get(key);
            if (deck.size() > maxRows) {
                maxRows = deck.size();
            }
        }

        for (int i = 0; i < maxRows; i++) {
            String rowToPrint = "" + i + "\t";
            for (int columnNumber = 0; columnNumber < columnNames.length; columnNumber++) {
                String key = GameState.columnNames[columnNumber];
                Deck columnDeck = columns.get(key);
                if (columnDeck.size() > i) {
                    Card card = columnDeck.get(i);
                    if (i >= columnDeck.getInvisibleCards()) {
                        rowToPrint += card.toShortString();
                    } else {
                        rowToPrint += "??";
                    }
                }
                rowToPrint += "\t\t";
            }
            System.out.println(rowToPrint);
        }
    }


    @Override
    public String refreshAndRequestMove(GameState gameState, Collection<Move> moves) {
        Scanner input = new Scanner(System.in);

        this.refresh(gameState);
        System.out.print("What action do you want to take: ");

        return input.nextLine();
    }
}
