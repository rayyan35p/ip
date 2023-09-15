package bob;

import bob.data.exception.DukeException;
import bob.data.task.TaskList;
import bob.parser.Parser;
import bob.parser.Parser.Command;
import bob.storage.Storage;
import bob.ui.Ui;

/**
 * Represents the main chatbot logic.
 */
public class Duke {
    private Ui ui;
    private Parser parser;
    private Storage storage;
    private TaskList list;

    public static void main(String[] args) {
        Ui ui = new Ui();
        Parser parser = new Parser();
        Storage storage = new Storage();
        TaskList list = new TaskList(storage);

        list.open();

        String input = ui.getFirstInput();

        while (true) {
            try {
                Command command = parser.parse(input);
//                execute(storage, list, ui);
                if (command == Parser.Command.BYE) {
                    list.close();
                    ui.end();
                    break;
                } else if (command == Parser.Command.LIST) {
                    ui.print(list.toString());
                } else if (command == Parser.Command.FIND) {
                    ui.print(list.find(input));
                } else {
                    list.executeCommand(command, input);
                }
            } catch (DukeException e) {
                System.out.println(e);
            }
            input = ui.getInput();
        }
    }

    /**
     * Initialise a new Duke object with its class fields.
     */
    public void init() {
        this.ui = new Ui();
        this.parser = new Parser();
        this.storage = new Storage();
        this.list = new TaskList(this.storage);

        list.open();
    }

    public void end() {
        this.list.close();
    }

    public String getResponse(String input) {
        this.init();
        String response;
        try {
            Command command = parser.parse(input);
            if (command == Parser.Command.BYE) {
                response = "Bye. Hope to see you again soon!";
            } else if (command == Parser.Command.LIST) {
                response = this.list.toString();
            } else if (command == Parser.Command.FIND) {
                response = this.list.find(input);
            } else {
                response = this.list.executeCommand(command, input);
            }
        } catch (DukeException e) {
            System.out.println(e);
            return "That didn't work as expected.";
        }
        this.end();
        return response;
    }
}
