package nl.quintor.solitaire.game.moves;

import nl.quintor.solitaire.Helpers.Data;
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

    private final static String name = System.getProperty("os.name").contains("Windows") ? "Move Card" : "Move Card";
    private String moveFrom;
    private String moveTo;

    /**
     * Constructor
     */
    public MoveCard() {

    }

    /**
     * Constructor
     *
     * @param playerInput String
     */
    public MoveCard(String playerInput) {
        String[] commandParts = playerInput.split(" ");
        if (commandParts.length >= 3) {
            this.moveFrom = commandParts[1];
            this.moveTo = commandParts[2];
        }
    }

    @Override
    public String apply(GameState gameState) throws MoveException {
        if (this.moveFrom == null || this.moveTo == null) {
            throw new MoveException("The move command is missing key parameters ");
        }
        DeckType fromDeckType = this.getTypeDeck(this.moveFrom, gameState);
        DeckType toDeckType   = this.getTypeDeck(this.moveTo, gameState);

        if (toDeckType == DeckType.STOCK) {
            throw new MoveException("You can't move a card to the Deck");
        } else if (fromDeckType == null || toDeckType == null) {
            throw new MoveException("Pleas make sure that coordinates are correct." +
                    "\n for example: Move A1 B3");
        }

        Deck fromDeck = this.getDeck(fromDeckType, gameState, this.moveFrom);
        if (fromDeck == null)
            throw new MoveException("There request source deck for the coordinate: " + this.moveFrom + "doesn't exist. Pleas make sure it's valid.");

        Deck toDeck = this.getDeck(toDeckType, gameState, this.moveTo);

        if (toDeck == null)
            throw new MoveException("There request destination deck for the coordinate: " + this.moveTo + "doesn't exist. Pleas make sure it's valid.");


        List<Card> fromCards = this.getCard(fromDeckType, fromDeck, gameState, this.moveFrom);
        if (fromCards == null)
            throw new MoveException("You can't move the card you requested or it doesn't exist");

        // i don't like this code -_-
        int fromPosition = Integer.parseInt(this.moveFrom.split("")[1]);
        if (fromDeck.getInvisibleCards() <= fromPosition) {
            if (fromDeck.size() - 2 <= fromDeck.getInvisibleCards() && fromDeck.size() - 2 >= 0) {
                fromDeck.setInvisibleCards(fromDeck.size() - 2);
            }
            //@todo  finish revert code
            gameState.getMoves().add(new Revert());
            toDeck.addAll(fromCards);
            fromDeck.removeAll(fromCards);
        } else {
            throw new MoveException("You can't move invisible card. Thats against rules");
        }
        return "Moved card ";
    }

    /**
     * Determining what type of deck it is using coordinate
     *
     * @param coordinate String
     * @param gameState  {@link GameState}
     * @return String
     */
    public DeckType getTypeDeck(String coordinate, GameState gameState) {
        String[] coordinateParts = coordinate.split("");
        Data     dataHelper      = new Data();
        if (Arrays.asList(GameState.stackPilesNames).contains(coordinate)) {
            return DeckType.STACK;
        } else if (coordinateParts.length == 2 &&
                Arrays.asList(GameState.columnNames).contains(coordinateParts[0]) &&
                dataHelper.isInteger(coordinateParts[1])) {
            return DeckType.COLUMN;
        } else if (coordinate.equals("o") || coordinate.equals("O")) {
            return DeckType.STOCK;
        }
        return null;
    }

    /**
     * This method get's the right deck based on deckType
     *
     * @param deckType   {@link DeckType}
     * @param gameState  {@link GameState}
     * @param coordinate String
     * @return Deck
     */
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

    /**
     * This method get's card from a deck.
     *
     * @param deckType   {@link DeckType}
     * @param deck       {@link Deck}
     * @param gameState  {@link GameState}
     * @param coordinate String
     * @return List<Card>
     */
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
                int pos = Integer.parseInt(coordinate.split("")[1]);
                if (deck.size() > 0 && deck.size() > pos) {
                    return deck.subList(pos, deck.size());
                }
                break;
        }
        return null;

    }

    /**
     * @param playerInput the input of the player when requesting the action that this Move represents
     * @return Move
     */
    @Override
    public Move createInstance(String playerInput) {
        return new MoveCard(playerInput);
    }
}
