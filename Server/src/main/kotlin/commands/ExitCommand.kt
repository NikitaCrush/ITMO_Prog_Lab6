package commands

import kotlin.system.exitProcess

/**
 * The ExitCommand class terminates the program.
 */
class ExitCommand : Command() {
    override fun execute(args: List<Any>): String {
        exitProcess(0)
    }

    override fun readArguments(input: () -> String): List<String> {
        return emptyList()
    }
}
