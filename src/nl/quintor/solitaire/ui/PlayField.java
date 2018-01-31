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
        this.printStockAndStackPiles(gameState);
        System.out.println("");
        this.printColumns(gameState);
    }

    private void printStockAndStackPiles(GameState gameState) {
        String[] linesToPrint = {"\t", "\t"};
        Deck stockDeck = gameState.getStock();
        int stockCycles = gameState.getStockCycles();
        Map<String, Deck> stackPiles = gameState.getStackPiles();
        //@todo if size() under 0 add space
        linesToPrint[0] += stockCycles + "(" + stockDeck.size() + ")";
        if (stockDeck.size() > stockCycles) {
            linesToPrint[1] += stockDeck.get(stockCycles).toShortString();
        } else {
            linesToPrint[1] += "--";
        }

        linesToPrint[0] += this.multiplyString(6 - linesToPrint[0].length(), " ")
                + this.multiplyString(4, "\t");
        linesToPrint[1] += this.multiplyString(6 - linesToPrint[1].length(), " ")
                + this.multiplyString(4, "\t");

        //preparing stackPiles
        for (int i = 0; i < GameState.stackPilesNames.length; i++) {
            linesToPrint[0] += "\t" + GameState.stackPilesNames[i] + "\t";
        }
        //values
        for (int i = 0; i < GameState.stackPilesNames.length; i++) {
            String stackCard = "--";
            Deck stackPile = stackPiles.get(GameState.stackPilesNames[i]);
            if (stackPile.size() > 0) {
                stackCard = stackPile.get((stackPiles.size() - 1)).toShortString();
            }
            linesToPrint[1] += "\t" + stackCard + "\t";
        }
        System.out.println(linesToPrint[0]);
        System.out.println(linesToPrint[1]);
    }

    private String multiplyString(int amount, String toReplaceWith) {
        return new String(new char[amount]).replace("\0", toReplaceWith);

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
        maxRows += 1;
        if (maxRows > 22) {
            maxRows = 22;
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
