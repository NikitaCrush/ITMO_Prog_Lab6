package workWithServer

import client.Client
import kotlin.system.exitProcess

/**
 * A class representing a command reader that reads user commands and sends them to the server.
 * It also handles script execution and special commands such as "exit", "update id" and "add".
 *
 * @property listOfCommands The list of available commands for the user.
 * @property asker An instance of the Asker class which is used to ask the user for input.
 * @property client An instance of the Client class, used to send commands to the server.
 */

class CommandReader {

    val listOfCommands = mutableListOf("show", "info", "help", "save", "clear", "exit")
    val asker = Asker()
    val client = Client()

    /**
     * Reads user commands and sends them to the server. Handles script execution and special commands such as "exit",
     * "update id" and "add".
     */

    fun readCommand() {
        val tokens = Tokenizator()
        val readerOfScripts = ScriptReader()
        while (true) {
            val command = asker.askCommand()
            val commandComponents = tokens.tokenizateCommand(command)
            if (commandComponents[0] == "execute_script") {
                val listOfTasks =
                    readerOfScripts.readScript(commandComponents[1], tokens, mutableListOf())
                for (i in listOfTasks) {
                    addSpecialComponents(i)
                    client.outputStreamHandler(i)
                }
            } else {
                if (listOfCommands.contains(commandComponents[0])) {
                    val task =Task(commandComponents, listOfCommands = listOfCommands)
                    addSpecialComponents(task)
                    client.outputStreamHandler(task)
                    listOfCommands.addAll(client.returnNewCommands())
                    client.resetNewCommands()
                } else {
                    println("Command ${commandComponents[0]} does not exist")
                }
            }
        }
    }

    /**
     * Adds special components to the task before it is sent to the server. Handles "exit", "update id" and "add"
     * commands.
     *
     * @param task The task to add special components to.
     */

    fun addSpecialComponents(task: Task) {
        if (task.definition[0] == "exit") {
            exitProcess(0)
        }
        if (task.definition[0] == "update id") {
            println("Enter new ID")
            task.definition.add(asker.askLong().toString())
        }
        if (task.definition[0] == "add") {
            task.labWork = asker.askLabWork()
        }
    }
}