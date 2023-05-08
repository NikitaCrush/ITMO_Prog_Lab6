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
    fun getRequiredArgs(command: Command, input: (() -> String)?, initialArgs: List<String>): List<Any> {
        val mutableInitialArgs = initialArgs.toMutableList()

        val inputWithInitialArgs: () -> String = {
            if (mutableInitialArgs.isNotEmpty()) {
                mutableInitialArgs.removeAt(0)
            } else {
                input?.invoke() ?: ""
            }
        }

        return when (command) {
            is commands.AddCommand, is commands.AddIfMaxCommand -> {
                command.readArguments(inputWithInitialArgs)
            }

            is commands.UpdateCommand -> {
                command.readArguments(inputWithInitialArgs)
            }

            else -> {
                command.readArguments(inputWithInitialArgs)
            }
        }
    }
}
