package client

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import utils.LabWorkReader
import java.io.File

class CommandInterpreter(private val labWorkReader: LabWorkReader, private val clientManager: ClientManager) {
    private var supportedCommands: List<CommandData> = emptyList()

    init {
        updateSupportedCommands()
    }

    private fun updateSupportedCommands() {
        val response = clientManager.sendCommandAndGetResponse(CommandData("getCommands"))
        supportedCommands = response.message.split(";")
            .map { it.split(",") }
            .map { CommandData(it[0], arguments = it.drop(1).chunked(2).map { arg -> CommandArgument(arg[0], arg[1]) }) }
    }


    private fun requestArguments(arguments: List<CommandArgument>) {
        arguments.forEach { argument ->
            print("Enter ${argument.name} (${argument.type}): ")
            argument.value = readlnOrNull()
        }
    }

    fun interpret(input: String): CommandData {
        val commandParts = input.split(" ")
        val commandName = commandParts[0]
        val parameters = commandParts.drop(1)

        if (commandName == "add" || commandName == "add_if_max") {
            val serializedLabWork = getSerializedLabWork()
            return CommandData(commandName, listOf(CommandArgument("labWork", "LabWork", serializedLabWork)))
        } else if (commandName == "update") {
            if (parameters.isEmpty()) {
                throw IllegalArgumentException("ID is required for the update command.")
            }
            val id = parameters[0]
            val serializedLabWork = getSerializedLabWork()
            return CommandData(commandName, listOf(CommandArgument("id", "Long", id), CommandArgument("labWork", "LabWork", serializedLabWork)))
        }
        if (commandName == "remove_by_id") {
            if (parameters.isEmpty()) {
                throw IllegalArgumentException("ID is required for the remove_by_id command.")
            }
            val id = parameters[0]
            return CommandData(commandName, listOf(CommandArgument("id", "Long", id)))
        }
        if (commandName == "execute_script") {
            if (parameters.isEmpty()) {
                throw IllegalArgumentException("File name is required for the execute_script command.")
            }
            val fileName = parameters[0]
            val script = File(fileName).readText()
            return CommandData(commandName, listOf(CommandArgument("script", "String", script)))
        }

        return CommandData(commandName, parameters.map { CommandArgument(it, it, it) })
    }


    private fun getSerializedLabWork(): String {
        val labWork = labWorkReader.readLabWork()
        return Json.encodeToString(labWork)
    }

}



