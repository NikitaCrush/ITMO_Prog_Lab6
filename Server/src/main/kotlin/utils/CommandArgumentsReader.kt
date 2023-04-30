package utils

import commands.Command

class CommandArgumentsReader {

    /**
     * Reads the arguments required for the given command.
     *
     * @param command The command for which to read the arguments.
     * @param input The input function to provide the arguments.
     * @param initialArg The initial argument provided with the command line.
     * @return A list of [Any] containing the parsed arguments.
     */
    fun getRequiredArgs(command: Command, input: (() -> String)?, initialArg: String): List<Any> {
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
