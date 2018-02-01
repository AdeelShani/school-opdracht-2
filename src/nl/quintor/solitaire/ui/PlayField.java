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
        System.out.println("");
        this.printStockAndStackPiles(gameState);
        System.out.println("");
        this.printColumns(gameState);
    }

    private void printStockAndStackPiles(GameState gameState) {
        String            firstLine   = "\t", SecondLine = "\t";
        Deck              stockDeck   = gameState.getStock();
        int               stockCycles = gameState.getStockCycles();
        Map<String, Deck> stackPiles  = gameState.getStackPiles();

        firstLine += stockCycles + "(" + stockDeck.size() + ")";
        SecondLine += stockDeck.size() > stockCycles ? stockDeck.get(stockCycles).toShortString() : "--";

        //adding tabs and spaces for ui
        firstLine += this.multiplyString(6 - firstLine.length(), " ")
                + this.multiplyString(5, "\t");

        SecondLine += this.multiplyString(6 - SecondLine.length(), " ")
                + this.multiplyString(4, "\t");

        //preparing stackPiles
        firstLine += String.join("\t\t", GameState.stackPilesNames);
        //values
        for (int i = 0; i < GameState.stackPilesNames.length; i++) {
            Deck   stackPile = stackPiles.get(GameState.stackPilesNames[i]);
            String stackCard = stackPile.size() > 0 ? stackPile.get((stackPile.size() - 1)).toShortString() : "--";
            SecondLine += "\t" + stackCard + "\t";
        }

        System.out.println(firstLine);
        System.out.println(SecondLine);
    }

    /**
     * This method multiples string
     *
     * @param amount        int
     * @param toReplaceWith String
     * @return String
     * @source https://stackoverflow.com/questions/2255500/can-i-multiply-strings-in-java-to-repeat-sequences
     */
    private String multiplyString(int amount, String toReplaceWith) {
        amount = amount < 0 ? 0 : amount;
        return new String(new char[amount]).replace("\0", toReplaceWith);
    }

    private void printColumns(GameState gameState) {
        Map<String, Deck> columns     = gameState.getColumns();
        String[]          columnNames = GameState.columnNames;
        int               maxRows     = 0;
        //@todo find better alternative for this code to count maxNumber
        for (int i = 0; i < columnNames.length; i++) {
            Deck deck = columns.get(columnNames[i]);
            if (deck.size() > maxRows) {
                maxRows = deck.size();
            }
        }
        //maybe replace code above with this
        /*for (Map.Entry<String,Deck> entry: columns.entrySet()) {
            Deck deck = entry.getValue();
            maxRows = deck.size() > maxRows ? deck.size() : maxRows;
        }*/
        maxRows += 1;
        if (maxRows > 22) {
            maxRows = 22;
        }

        //starting to print columns and cards
        System.out.print("\t" + String.join("\t\t", columnNames));
        System.out.println("");

        for (int i = 0; i < maxRows; i++) {
            StringBuilder rowToPrint = new StringBuilder(32);
            rowToPrint.append(i).append("\t");
            for (int columnNumber = 0; columnNumber < columnNames.length; columnNumber++) {
                String key        = GameState.columnNames[columnNumber];
                Deck   columnDeck = columns.get(key);
                if (columnDeck.size() > i) {
                    Card   card     = columnDeck.get(i);
                    String cardText = i >= columnDeck.getInvisibleCards() ? card.toShortString() : "??";
                    rowToPrint.append(cardText);
                }
                rowToPrint.append("\t\t");
            }
            System.out.println(rowToPrint.toString());
        }
    }


    @Override
    public String refreshAndRequestMove(GameState gameState, Collection<Move> moves) {
        Scanner input = new Scanner(System.in);

        this.refresh(gameState);
        System.out.println("Available moves: ");
        System.out.println("Cycle stock, Move, Revert, Help, Quit");
        System.out.print("What action do you want to take: ");

        return input.nextLine();
    }
}
