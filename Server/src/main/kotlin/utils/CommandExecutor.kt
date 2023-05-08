package utils

import commands.*
import java.util.*

/**
 * Class responsible for managing and executing commands.
 *
 * @property labWorkCollection The [LabWorkCollection] instance to be manipulated by the commands.
 * @property printer The [Printer] instance to output the command results.
 */
class CommandExecutor(printer: Printer) {
    private val commandMap: MutableMap<String, Command> = mutableMapOf()

    init {
        commandMap["help"] = HelpCommand(this)
        commandMap["info"] = InfoCommand()
        commandMap["show"] = ShowCommand()
        commandMap["add"] = AddCommand()
        commandMap["update"] = UpdateCommand()
        commandMap["remove_by_id"] = RemoveByIdCommand()
        commandMap["clear"] = ClearCommand()
        commandMap["save"] = SaveCommand()
        commandMap["execute_script"] = ExecuteScriptCommand(this, printer)
        commandMap["remove_first"] = RemoveFirstCommand()
        commandMap["remove_head"] = RemoveHeadCommand()
        commandMap["add_if_max"] = AddIfMaxCommand()
        commandMap["sum_of_minimal_point"] = SumOfMinimalPointCommand()
        commandMap["min_by_difficulty"] = MinByDifficultyCommand()
        commandMap["print_unique_minimal_point"] = PrintUniqueMinimalPointCommand()
    }


    /**
     * Retrieves a command instance by its name.
     *
     * @param name The name of the command to retrieve.
     * @return The command instance if found, null otherwise.
     */
    fun getCommand(name: String): Command? {
        return commandMap[name.lowercase(Locale.getDefault())]
    }

    /**
     * Retrieves the available commands.
     *
     * @return A map of command names to their corresponding [Command] instances.
     */
    fun getAvailableCommands(): Map<String, Command> {
        return commandMap
    }
}
