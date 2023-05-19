package client

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import utils.LabWorkReader
import java.io.File

class CommandInterpreter(private val labWorkReader: LabWorkReader) {
    private var supportedCommands: List<CommandData> = emptyList()

    private fun requestArguments(arguments: List<CommandArgument>) {
        arguments.forEach { argument ->
            print("Enter ${argument.name} (${argument.type}): ")
            argument.value = readlnOrNull()
        }
    }

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

    private fun getSerializedLabWork(): String {
        val labWork = labWorkReader.readLabWork()
        return Json.encodeToString(labWork)
    }
}



