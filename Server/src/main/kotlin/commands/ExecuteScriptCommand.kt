package commands

import data.Messages
import exeptions.CommandException
import org.koin.core.component.inject
import utils.CommandExecutor
import utils.Printer
import java.io.File
import java.io.FileNotFoundException
import java.util.*

class ExecuteScriptCommand(
    private val commandExecutor: CommandExecutor,
    private val printer: Printer,
    private val nestedLevel: Int = 0
) : Command() {
    val stack: Stack<String> by inject()
    // Define the required arguments for this command
    private val requiredArgs = listOf("fileName")

    fun copy(nestedLevel: Int): ExecuteScriptCommand {
        return ExecuteScriptCommand(commandExecutor, printer, nestedLevel)
    }

    override fun execute(args: List<Any>): String {
        val fileName = args[0] as String
        val file = File(fileName)
        if (stack.contains(fileName)) return "Error: Recursion"

        return try {
            if (!file.exists()) {
                throw FileNotFoundException(Messages.FILE_NOT_FOUND + fileName)
            }
            stack.add(fileName)
            val lines = file.readLines().iterator()
            while (lines.hasNext()) {
                val line = lines.next().trim()
                if (line.isNotBlank()) {
                    val commandResult = executeLine(line, lines::next)
                    if (commandResult != null) {
                        printer.println(commandResult)
                    }
                }
            }
            stack.remove(fileName)
            Messages.SCRIPT_EXECUTED_SUCCESS
        } catch (e: FileNotFoundException) {
            "Error: ${e.message}"
        }
    }

    private fun executeLine(line: String, input: () -> String): String? {
        val commandParts = line.split(" ", limit = 2)
        val commandName = commandParts[0]

        val command = commandExecutor.getCommand(commandName)
            ?: throw CommandException("Unknown command: $commandName")

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
        val requiredArgs = (command as? ExecuteScriptCommand)?.requiredArgs ?: emptyList()
        while (args.size < requiredArgs.size) {
            input?.invoke()?.let { arg -> args.add(arg) }
        }

        return args
    }
}