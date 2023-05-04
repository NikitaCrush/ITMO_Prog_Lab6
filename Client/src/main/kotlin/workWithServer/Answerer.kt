package workWithServer

import java.io.Serializable

/**
 * A data class representing an answerer that can be sent over the network.
 *
 * @property result The result of the command execution.
 * @property listOfNewCommand A list of new commands that should be added to the user's available commands.
 */

data class Answerer(
    var result: String = "Great job\n----------\n",
): Serializable {

    val listOfNewCommand= mutableListOf<String>()

    /**
     * Sets a list of new commands that should be added to the user's available commands.
     *
     * @param list The list of new commands.
     */

    fun setNewCommands(list: List<String>){
        listOfNewCommand.addAll(list)
    }
}