/**
 * @author Adnan Akbas 17005116
 * @author Karam Es-Sabri 15098796
 * @author Adeel Ahmad Shani Haq 17019060
 */
package nl.quintor.solitaire.ui;

import nl.quintor.solitaire.Helpers.Data;
import nl.quintor.solitaire.game.moves.Move;
import nl.quintor.solitaire.models.card.Card;
import nl.quintor.solitaire.models.deck.Deck;
import nl.quintor.solitaire.models.state.GameState;

import java.util.Collection;
import java.util.Map;
import java.util.Scanner;

public class PlayField implements nl.quintor.solitaire.ui.UI {

    private String msg      = "";
    private String errorMsg = "";

    @Override
    public void setMessage(String message) {
        this.msg = message;
    }

    @Override
    public void setErrorMessage(String message) {
        this.errorMsg = "[Error]: " + message;
    }

    @Override
    public void refresh(GameState gameState) {
        this.clearScreen();
        System.out.println(gameState.toString());
        System.out.println("");
        this.printStockAndStackPiles(gameState);
        System.out.println("");
        this.printColumns(gameState);
        System.out.println("");
        if (!this.msg.isEmpty()) {
            System.out.println(this.msg);
            this.msg = "";
        }
        if (!this.errorMsg.isEmpty()) {
            System.out.println(this.errorMsg);
            this.errorMsg = "";
        }
    }

    /**
     * source: https://stackoverflow.com/questions/2979383/java-clear-the-console
     */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * @param gameState {@link GameState}
     */
    private void printStockAndStackPiles(GameState gameState) {
        String            firstLine   = "\t", SecondLine = "\t";
        Deck              stockDeck   = gameState.getStock();
        int               stockCycles = gameState.getStockCycles();
        Map<String, Deck> stackPiles  = gameState.getStackPiles();
        Data              dataHelper  = new Data();

        firstLine += stockCycles + "(" + stockDeck.size() + ")";
        SecondLine += stockDeck.size() > stockCycles ? stockDeck.get(stockCycles).toShortString() : "--";

        //adding tabs and spaces for ui
        firstLine += dataHelper.multiplyString(6 - firstLine.length(), " ")
                + dataHelper.multiplyString(5, "\t");

        SecondLine += dataHelper.multiplyString(6 - SecondLine.length(), " ")
                + dataHelper.multiplyString(4, "\t");

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
     * @param gameState {@link GameState}
     */
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

    /***
     *
     * @param gameState the game state to be visualized by the UI
     * @param moves     the moves that are possible in this game state
     * @return
     */
    @Override
    public String refreshAndRequestMove(GameState gameState, Collection<Move> moves) {
        Scanner input = new Scanner(System.in);

        this.refresh(gameState);
        System.out.println("Available moves: ");
        System.out.println("Cycle stock, Move, Help, Quit");
        System.out.print("What action do you want to take: ");

        return input.nextLine();
    }
}
