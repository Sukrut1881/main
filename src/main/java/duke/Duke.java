package duke;

import duke.commands.Command;
import duke.commands.ExitCommand;
import duke.commons.DukeException;
import duke.parsers.Parser;
import duke.storage.Storage;
import duke.ui.Ui;

/**
 * The duke.Duke program is a simple Personal Assistant Chatbot
 * that helps a person to keep track of various things.
 *
 * @author  Jefferson111
 */
public class Duke {
    private static  final String FILE_PATH = "data/tasks.txt";
    private boolean exitFlag = false;
    private String dukeMessage = "";
    private Ui ui = new Ui();
    private Storage storage = new Storage(FILE_PATH, ui);

    /**
     * Entry point.
     */
    public static void main(String[] args) throws DukeException {
        new Duke();
    }

    /**
     * Creates duke.Duke instance.
     */
    Duke() throws DukeException {
        /*
        //enables terminal output
        Ui ui = new Ui();
        Parser parser = new Parser();
        ui.showWelcome();
        Storage storage = new Storage(FILE_PATH, ui);



        while (true) {
            String userInput = ui.readCommand();
            try {
                Command command = Parser.parse(userInput);
                command.execute(parser, ui, storage);
                dukeMessage = parser.getResponse();
                if (command instanceof ExitCommand) {
                    break;
                }
            } catch (DukeException e) {
                ui.showError(e.getMessage());
            }
        }
        */
    }

    /**
     * Send a request to Duke, who interprets and saves response
     * in dukeMessage.
     * @param input The user input
     * @throws DukeException Exception that Duke throws
     */
    public void parseRequest(String input) throws DukeException {
        try {
            Command command = Parser.parse(input.toLowerCase());
            command.execute(ui, storage);
            dukeMessage = ui.getResponse();
            if (command instanceof ExitCommand) {
                exitFlag = true;
            }
        } catch (DukeException e) {
            ui.setResponse(ui.getError(e.getMessage()));
            dukeMessage = ui.getResponse();
        }
    }

    /**
     * Parse request and get response.
     * @param input the input string
     * @return dukeMessage the message from Duke
     * @throws DukeException the exception thrown
     */
    public String getResponse(String input) throws DukeException {
        parseRequest(input);
        System.out.println("fetching response from command: " + input);
        System.out.println(dukeMessage);
        return dukeMessage;
    }

    public Boolean getExitStatus() {
        return exitFlag;
    }

    public String getWelcome() {
        return ui.getWelcome();
    }
}
