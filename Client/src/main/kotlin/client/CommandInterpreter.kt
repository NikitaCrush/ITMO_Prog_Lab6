package client

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import utils.LabWorkReader

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
            argument.value = readLine()
        }
    }

    fun interpret(input: String): CommandData {
        val commandParts = input.split(" ")
        val commandName = commandParts[0]
        val parameters = commandParts.drop(1)

        if (commandName == "add") {
            // Get a serialized LabWork instance from user input
            val serializedLabWork = getSerializedLabWork()
            return CommandData(commandName, listOf(CommandArgument("labWork", "LabWork", serializedLabWork)))
        }

        return CommandData(commandName, parameters.map { CommandArgument(it, it, it) })
    }


    private fun getSerializedLabWork(): String {
        val labWork = labWorkReader.readLabWork()
        return Json.encodeToString(labWork)
    }

}



