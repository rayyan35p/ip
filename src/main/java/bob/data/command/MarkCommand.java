package bob.data.command;

import bob.data.task.TaskList;
import bob.storage.Storage;
import bob.ui.Ui;

public class MarkCommand extends Command {
    private String input;
    public MarkCommand(String input) {
        super();
        this.input = input;
    }

    @Override
    public String execute(Storage storage, TaskList list, Ui ui) {
        return list.setTaskComplete(input);
    }
}
