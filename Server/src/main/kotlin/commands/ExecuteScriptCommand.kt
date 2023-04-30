package commands

import data.Messages
import org.koin.core.component.inject
import utils.CommandParser
import utils.Printer
import java.io.File
import java.io.FileNotFoundException
import java.util.*

/**
 * The ExecuteScriptCommand class reads and executes commands from the specified file.
 *
 * @property commandParser The command parser used to parse and execute commands.
 * @property printer The printer used to display command results.
 * @property nestedLevel The current script nesting level (default 0).
 */
class ExecuteScriptCommand(
    private val commandParser: CommandParser,
    private val printer: Printer,
    private val nestedLevel: Int = 0
) : Command() {
    val stack: Stack<String> by inject()

    fun copy(nestedLevel: Int): ExecuteScriptCommand {
        return ExecuteScriptCommand(commandParser, printer, nestedLevel)
    }

    override fun execute(args: List<Any>): String {

        val fileName = args[0] as String
        val file = File(fileName)
        if(stack.contains(fileName)) return "Error: Recursion"
        return try {
            if (!file.exists()) {
                throw FileNotFoundException(Messages.FILE_NOT_FOUND + fileName)
            }
            stack.add(fileName)
            val lines = file.readLines().iterator()
            while (lines.hasNext()) {
                val line = lines.next().trim()
                if (line.isNotBlank()) {
                    val commandResult = commandParser.parseAndExecute(line, nestedLevel + 1, lines::next)
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

    override fun readArguments(input: () -> String): List<Any> {
        val fileName = input()
        if (fileName.isBlank()) {
            throw FileNotFoundException("There is no file name")
        }
        val file = File(fileName)
        if (!file.exists()) {
            throw FileNotFoundException(Messages.FILE_NOT_FOUND + fileName)
        }

        val fileParts = fileName.split(" ", limit = 2)
        if (fileParts.size > 1) {
            throw IllegalArgumentException("Unexpected argument after the file name: '${fileParts[1]}'")
        }

        return listOf(fileName)
    }

}
