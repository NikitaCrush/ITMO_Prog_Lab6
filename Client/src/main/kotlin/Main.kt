
import utils.LabWorkReader
import utils.Validator
import client.*

fun main() {
    // Initialize required instances
    val validator = Validator()
    val labWorkReader = LabWorkReader({ readLine() ?: throw IllegalStateException("No input provided") }, validator)

    // Initialize the command interpreter
    val commandInterpreter = CommandInterpreter(labWorkReader)

    val clientManager = ClientManager("localhost", 12345) // assuming "localhost" and 12345 are your host and port
    clientManager.connect()
    while (true) {
        print("Enter command: ")
        val input = readLine() ?: continue
        if (input.lowercase() == "exit") {
            break
        }

        val commandData = commandInterpreter.interpret(input)
        val task = Task(commandData)

        val response = task.execute(clientManager)
        println(response.message)
    }
}
