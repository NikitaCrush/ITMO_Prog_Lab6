package commands

import utils.CommandExecutor
import utils.Printer
import java.util.*

class ExecuteScriptCommand(
    private val commandExecutor: CommandExecutor,
    private val printer: Printer,
    private val nestedLevel: Int = 0
) : Command() {
    private val stack: Stack<String> = Stack()

    fun copy(nestedLevel: Int): ExecuteScriptCommand {
        return ExecuteScriptCommand(commandExecutor, printer, nestedLevel)
    }

    override fun execute(args: List<Any>): String {
        if (args.isEmpty() || args[0] !is String) {
            return "Script is not provided or has an incorrect format."
        }

        val script = args[0].toString()
        val lines = script.lines().iterator()
        val results = StringBuilder()

        if (stack.contains(script)) return "Error: Recursion"

        stack.add(script)

        while (lines.hasNext()) {
            val line = lines.next().trim()
            if (line.isNotBlank()) {
                val commandResult = executeLine(line, lines::next)
                if (commandResult != null) {
                    results.append(commandResult).append("\n")
                }
            }
        }
        stack.remove(script)

        return results.append("Script executed successfully").toString()
    }

    private fun executeLine(line: String, input: () -> String): String? {
        val commandParts = line.split(" ", limit = 2)
        val commandName = commandParts[0]

        val command = commandExecutor.getCommand(commandName)
            ?: throw IllegalArgumentException("Unknown command: $commandName")

        val initialArg = commandParts.getOrElse(1) { "" }
        val args = readArguments(command, input, initialArg)

        return when {
            command is ExecuteScriptCommand -> {
                command.copy(nestedLevel = nestedLevel + 1).execute(args)
            }
            line.isNotBlank() -> {
                command.execute(args)
            }
            else -> null // Return null if the input is empty (ignores empty lines)
        }
    }

    private fun readArguments(command: Command, input: (() -> String)?, initialArg: String): List<Any> {
        val args = mutableListOf<Any>()
        if (initialArg.isNotBlank()) {
            args.add(initialArg)
        }

        // Read additional arguments if necessary
        val requiredArgs = (command as? ExecuteScriptCommand)?.getRequiredArgs() ?: emptyList()
        while (args.size < requiredArgs.size) {
            input?.invoke()?.let { arg -> args.add(arg) }
        }

        return args
    }

    private fun getRequiredArgs(): List<String> {
        // Add the required arguments for each command here
        return emptyList()
    }
}
