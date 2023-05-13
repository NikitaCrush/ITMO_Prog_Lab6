import utils.LabWorkReader
import utils.Validator
import client.*
import data.Messages

fun main() {
    // Initialize required instances
    val validator = Validator()
    val labWorkReader = LabWorkReader({ readlnOrNull() ?: throw IllegalStateException("No input provided") }, validator)

    // Initialize the ClientManager
    val clientManager = ClientManager("localhost", 12345) // assuming "localhost" and 12345 are your host and port
    clientManager.connect()

    // Initialize the command interpreter
    val commandInterpreter = CommandInterpreter(labWorkReader, clientManager)
    println(Messages.WELCOME)
    println(Messages.ENTER_HELP)
    while (true) {
        print("> ")
        val input = readlnOrNull() ?: continue
        if (input.lowercase() == "exit") {
            break
        }

        val commandData = commandInterpreter.interpret(input)
        val task = Task(commandData)

        val response = task.execute(clientManager)
        println(response.message)
    }
}
