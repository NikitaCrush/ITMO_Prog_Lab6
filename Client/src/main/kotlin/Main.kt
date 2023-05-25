import utils.LabWorkReader
import utils.Validator
import client.*
import data.Messages

fun main() {
    // Initialize required instances
    val validator = Validator()
    val labWorkReader = LabWorkReader({ readlnOrNull() ?: throw IllegalStateException("No input provided") }, validator)

    // Initialize the command interpreter
    val commandInterpreter = CommandInterpreter(labWorkReader)
    println(Messages.WELCOME)
    println(Messages.ENTER_HELP)
    while (true) {
        print("> ")
        val input = readlnOrNull() ?: continue
        if (input.lowercase() == "exit") {
            break
        }

        val (commandData, _) = commandInterpreter.interpret(input)
        val task = Task(commandData)

        // Initialize the ClientManager
        val clientManager = ClientManager("localhost", 12345) // assuming "localhost" and 12345 are your host and port
        clientManager.connect()

        commandData.arguments.forEach { argument ->
            if (argument.value.isNullOrEmpty()) {
                print("Enter ${argument.name} (${argument.type}): ")
                argument.value = readlnOrNull()
            }
        }

        val response = task.execute(clientManager)
        println(response.message)

        clientManager.disconnect()
    }
}
