package nl.quintor.solitaire.game.moves;

import nl.quintor.solitaire.game.moves.ex.MoveException;
import nl.quintor.solitaire.models.card.Card;
import nl.quintor.solitaire.models.deck.Deck;
import nl.quintor.solitaire.models.deck.DeckType;
import nl.quintor.solitaire.models.state.GameState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MoveCard implements Move {
    private final static String name = System.getProperty("os.name").contains("Windows") ? "Move Card": "Move Card";

    private String moveFrom;
    private String moveTo;

    public MoveCard() {

    }

    public MoveCard(String playerInput) {
        String[] commandParts = playerInput.split(" ");
        this.moveFrom = commandParts[1];
        this.moveTo = commandParts[2];
    }

    @Override
    public String apply(GameState gameState) throws MoveException {
        String[]          moveFromParts = this.moveFrom.split("");
        String[]          moveToParts   = this.moveFrom.split("");
        Map<String, Deck> columns       = gameState.getColumns();
        Map<String, Deck> stackPiles    = gameState.getStackPiles();
        Deck              stock         = gameState.getStock();


        DeckType fromDeckType = this.getTypeDeck(this.moveFrom, gameState);
        DeckType toDeckType   = this.getTypeDeck(this.moveTo, gameState);

        Deck fromDeck = this.getDeck(fromDeckType, gameState, this.moveFrom);
        Deck toDeck   = this.getDeck(toDeckType, gameState, this.moveTo);

        List<Card> fromCards = this.getCard(fromDeckType, fromDeck, gameState, this.moveFrom);
        // List<Card> toCards = this.getCard(toDeckType,toDeck,gameState,this.moveTo);
        toDeck.addAll(fromCards);
        fromDeck.removeAll(fromCards);


        return "Moved card ";
    }

    /**
     * Determining what type of deck it is using coordinate
     *
     * @param coordinate String
     * @param gameState  GameState
     * @return String
     */
    public DeckType getTypeDeck(String coordinate, GameState gameState) {
        String[] coordinateParts = coordinate.split("");
        if (Arrays.asList(GameState.stackPilesNames).contains(coordinate)) {
            return DeckType.STACK;
        } else if (coordinateParts.length == 2 &&
                Arrays.asList(GameState.columnNames).contains(coordinateParts[0]) &&
                this.isInteger(coordinateParts[1])) {
            return DeckType.COLUMN;
        } else if (coordinate.equals("o") || coordinate.equals("O")) {
            return DeckType.STOCK;
        }
        return null;
    }

    public Deck getDeck(DeckType deckType, GameState gameState, String coordinate) {
        Deck deck = null;
        switch (deckType) {
            case STOCK:
                deck = gameState.getStock();
                break;
            case STACK:
                deck = gameState.getStackPiles().get(coordinate);
                break;
            case COLUMN:
                deck = gameState.getColumns().get(coordinate.split("")[0]);
                break;
        }
        return deck;
    }

    public List<Card> getCard(DeckType deckType, Deck deck, GameState gameState, String coordinate) {
        switch (deckType) {
            case STOCK:
                if (deck.size() > gameState.getStockCycles()) {
                    int        pos   = gameState.getStockCycles();
                    List<Card> cards = new ArrayList<>();
                    cards.add(deck.get(pos));
                    return cards;
                }
                break;
            case STACK:
                if (deck.size() > 0) {
                    int        pos   = deck.size() - 1;
                    List<Card> cards = new ArrayList<>();
                    cards.add(deck.get(pos));
                    return cards;
                }
                break;
            case COLUMN:
                if (deck.size() > 0) {
                    int pos = Integer.parseInt(coordinate.split("")[1]);
                    return deck.subList(pos, deck.size());
                }
                break;
        }
        return null;

    }

    public boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Move createInstance(String playerInput) {
        return new MoveCard(playerInput);
    }
}
