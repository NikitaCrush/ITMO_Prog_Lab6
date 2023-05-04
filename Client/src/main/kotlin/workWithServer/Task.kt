package workWithServer

import data.LabWork
import java.io.Serializable

/**
 * A data class representing a task that can be sent over the network.
 *
 * @property definition The definition of the task as a mutable list of strings.
 * @property labWork An optional `LabWork` object associated with the task.
 * @property listOfCommands A list of available commands for the user, used to update the command reader's list of commands.
 */

data class Task(
    val definition: MutableList<String>, //описание таски
    var labWork: LabWork? = null,
    val listOfCommands: MutableList<String>? = null,
) : Serializable {
}