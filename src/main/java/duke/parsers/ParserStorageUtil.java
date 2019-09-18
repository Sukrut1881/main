package duke.parsers;

import duke.commons.DukeDateTimeParseException;
import duke.commons.DukeException;
import duke.commons.MessageUtil;
import duke.tasks.RecurringTask;
import duke.tasks.Task;
import duke.tasks.Event;
import duke.tasks.Deadline;
import duke.tasks.Todo;
import duke.tasks.DoWithin;

import java.time.LocalDateTime;

/**
 * Parser for Storage related operations.
 */
public class ParserStorageUtil {
    /**
     * Parses a task from String format back to task.
     *
     * @param line The String description of a task.
     * @return The corresponding task object.
     */
    public static Task createTaskFromStorage(String line) throws DukeException {
        String[] taskParts = line.split("\\|");
        try {
            String type = taskParts[0].strip();
            String status = taskParts[1].strip();
            String description = taskParts[2].strip();
            Task task;
            if ("D".equals(type)) {
                try {
                    task = new Deadline(description, ParserTimeUtil.parseStringToDate(taskParts[3].strip()));
                } catch (DukeDateTimeParseException e) {
                    task = new Deadline(description, taskParts[3].strip());
                }
            } else if ("E".equals(type)) {
                try {
                    task = new Event(description, ParserTimeUtil.parseStringToDate(taskParts[3].strip()));
                } catch (DukeDateTimeParseException e) {
                    task = new Event(description, taskParts[3].strip());
                }
            } else if ("W".equals(type)) {
                LocalDateTime start = ParserTimeUtil.parseStringToDate(taskParts[3].strip());
                LocalDateTime end = ParserTimeUtil.parseStringToDate(taskParts[4].strip());
                task = new DoWithin(description, start, end);
            } else if ("R".equals(type)) {
                task = new RecurringTask(description, ParserTimeUtil.parseStringToDate(taskParts[3].strip()),
                        Integer.parseInt(taskParts[4].strip()));
            } else {
                task = new Todo(description);
            }
            task.setDone("true".equals(status));
            return task;
        } catch (Exception e) {
            throw new DukeException(MessageUtil.CORRUPTED_TASK);
        }
    }

    /**
     * Parses a task from task to String format.
     *
     * @param task The task.
     * @return The corresponding String format of the task object.
     */
    public static String toStorageString(Task task) throws DukeException {
        if (task instanceof Deadline) {
            return "D | " + task.isDone() + " | " + task.getDescription() + " | " + ((Deadline) task).getDeadline();
        } else if (task instanceof Todo) {
            return  "T | " + task.isDone() + " | " + task.getDescription();
        } else if (task instanceof Event) {
            return "E | " + task.isDone() + " | " + task.getDescription() + " | " + ((Event) task).getEvent();
        } else if (task instanceof DoWithin) {
            return "W | " + task.isDone() + " | " + task.getDescription() + " | " + ((DoWithin) task).getWithin();
        } else if (task instanceof RecurringTask) {
            return ("R | " + task.isDone() + " | " + task.getDescription() + " | "
                    + ((RecurringTask) task).getStartDate() + " | " +  ((RecurringTask) task).getRepeatInterval());
        }
        throw new DukeException(MessageUtil.CORRUPTED_TASK);
    }
}
