package utils

import commands.Command
import exeptions.CommandException


/**
 * Class responsible for parsing and executing commands.
 *
 * @property commandExecutor The [CommandExecutor] instance responsible for managing command execution.
 */
class CommandParser(private val commandExecutor: CommandExecutor) {

    /**
     * Parses and executes a command line.
     *
     * @param commandLine The command line string to parse and execute.
     * @param nestedLevel The nested level of the script execution, default value is 0.
     * @param input An optional input function to provide input to the command, if needed.
     * @return The result of the command execution as a [String] or null if the input is empty.
     * @throws CommandException If an unknown command is encountered.
     */
    fun parseAndExecute(commandLine: String, nestedLevel: Int = 0, input: (() -> String)? = null): String? {
        val commandParts = commandLine.split(" ", limit = 2)
        val commandName = commandParts[0]

        val command = commandExecutor.getCommand(commandName)
            ?: throw CommandException("Unknown command: $commandName")

        if (command is commands.ExecuteScriptCommand && commandParts.size == 1) {
            return "There is no file name"
        }

        val initialArg = commandParts.getOrElse(1) { "" }
        val args = readArguments(command, input, initialArg)
        return when {
            command is commands.ExecuteScriptCommand -> {
                command.copy(nestedLevel = nestedLevel).execute(args)
            }
            commandLine.isNotBlank() -> {
                command.execute(args)
            }
            else -> null // Return null if the input is empty (ignores empty lines)
        }
    }

    /**
     * Reads the arguments required for the given command.
     *
     * @param command The command for which to read the arguments.
     * @param input The input function to provide the arguments.
     * @param initialArg The initial argument provided with the command line.
     * @return A list of [Any] containing the parsed arguments.
     */
    private fun readArguments(command: Command, input: (() -> String)?, initialArg: String): List<Any> {
        return when (command) {
            is commands.AddCommand, is commands.AddIfMaxCommand -> {
                command.readArguments(input ?: { readlnOrNull() ?: "" })
            }
            is commands.UpdateCommand -> {
                command.readArguments { initialArg }
            }
            else -> {
                command.readArguments { initialArg }
            }
        }
    }

}
