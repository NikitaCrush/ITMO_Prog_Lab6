package client

import utils.LabWorkReader

class CommandInterpreter(private val labWorkReader: LabWorkReader) {
    fun interpret(input: String): CommandData {
        val parts = input.split(" ")
        val commandName = parts[0]
        val parameters = parts.drop(1)
        return CommandData(commandName, parameters)
    }
}
