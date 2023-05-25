package client

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import utils.LabWorkReader
import java.io.File
/**
 * This class is responsible for interpreting the user's command.
 *
 * @property labWorkReader An instance of [LabWorkReader] to read LabWork objects when required.
 */
class CommandInterpreter(private val labWorkReader: LabWorkReader) {
    /**
     * Interprets a string command.
     *
     * @param input The string input representing the command.
     * @return A pair of [CommandData] and a list of [CommandArgument]s required for this command.
     */
    fun interpret(input: String): Pair<CommandData, List<CommandArgument>> {
        val commandParts = input.split(" ")
        val commandName = commandParts[0]
        val parameters = commandParts.drop(1)

        val arguments = when (commandName) {
            "add", "add_if_max" -> {
                val serializedLabWork = getSerializedLabWork()
                listOf(CommandArgument("labWork", "LabWork", serializedLabWork))
            }
            "update" -> {
                if (parameters.isEmpty()) {
                    throw IllegalArgumentException("ID is required for the update command.")
                }
                val id = parameters[0]
                val serializedLabWork = getSerializedLabWork()
                listOf(CommandArgument("id", "Long", id), CommandArgument("labWork", "LabWork", serializedLabWork))
            }
            "remove_by_id" -> {
                if (parameters.isEmpty()) {
                    throw IllegalArgumentException("ID is required for the remove_by_id command.")
                }
                val id = parameters[0]
                listOf(CommandArgument("id", "Long", id))
            }
            "execute_script" -> {
                if (parameters.isEmpty()) {
                    throw IllegalArgumentException("File name is required for the execute_script command.")
                }
                val fileName = parameters[0]
                val script = File(fileName).readText()
                listOf(CommandArgument("script", "String", script))
            }
            else -> {
                parameters.map { CommandArgument(it, it, it) }
            }
        }

        return Pair(CommandData(commandName, arguments), arguments)
    }

    /**
     * Gets serialized lab work
     */
    private fun getSerializedLabWork(): String {
        val labWork = labWorkReader.readLabWork()
        return Json.encodeToString(labWork)
    }
}



