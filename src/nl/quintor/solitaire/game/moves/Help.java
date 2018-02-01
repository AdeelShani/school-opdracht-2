package nl.quintor.solitaire.game.moves;

import nl.quintor.solitaire.game.moves.ex.MoveException;
import nl.quintor.solitaire.models.state.GameState;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Help implements Move {
    private final static String name = System.getProperty("os.name").contains("Windows") ? "Help" : "Help";

    private Map<String, String> instructions = new HashMap<>();
    private String              key          = "*";

    /**
     * Constructor
     */
    public Help() {
        this.initInstructions();
    }

    /**
     * Constructor
     *
     * @param playerInput String
     */
    public Help(String playerInput) {
        this.initInstructions();
        String[] commandParts = playerInput.split(" ");
        if (commandParts.length >= 2) {
            String keyTemp = commandParts[1].substring(0, 1);
            if (instructions.containsKey(keyTemp)) {
                this.key = keyTemp;
            } else {
                this.key = "?";
            }
        }
    }

    /**
     * intializing variables with instructions
     */
    private void initInstructions() {
        List<String> keys = Arrays.asList("C", "M", "H", "Q", "*", "?");
        List<String> instructionsText = Arrays.asList(
                ("This command will cycle through stock cards." +
                        "\nThere are no special parameters required for this command"),
                ("This command is used for moving a card to specifick location." +
                        "\n The move command required paramters: source and destination." +
                        "\n Move <source> <destination>"),
                ("This command is used to more information about other commands." +
                        "\n use it in following matter: Help <command>"),
                ("This command is used to finish this game"),
                ("To navigate through this game you can use commands." +
                        "\nType first letter of command in capital and then press enter button." +
                        "\nIf you need more detailed information about commands then use follow command:" +
                        "\n H <command> for Example: H Move"),
                ("We couldn't find any instructions for your command")
        );

        for (int i = 0; i < keys.size(); i++) instructions.put(keys.get(i), instructionsText.get(i));
    }

    @Override
    public String apply(GameState gameState)  {
        return this.instructions.get(this.key);
    }

    @Override
    public Move createInstance(String playerInput) {
        return new Help(playerInput);
    }
}
