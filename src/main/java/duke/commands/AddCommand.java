package duke.commands;

import duke.commons.DukeException;
import duke.parsers.Parser;
import duke.storage.Storage;
import duke.tasks.Deadline;
import duke.tasks.Task;
import duke.tasks.TaskWithDates;
import duke.ui.Ui;

/**
 * Class representing a command to add a new task.
 */
public class AddCommand extends Command {
    private final Task task;

    /**
     * Creates a new AddCommand with the given task.
     *
     * @param task The task to add.
     */
    public AddCommand(Task task) {
        this.task = task;
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param ui The user interface displaying events on the task list.
     * @param storage The duke.storage object containing task list.
     */
    @Override
    public void execute(Ui ui, Storage storage) throws DukeException {
        storage.getTasks().add(task);
        if (task instanceof TaskWithDates) {
            storage.getTasksWithDate().add(task);
        }
        ui.setResponse(ui.getTaskDesc(task));
        storage.write();
    }

}
