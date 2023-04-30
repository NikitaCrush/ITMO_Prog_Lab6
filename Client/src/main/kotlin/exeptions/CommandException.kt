package exeptions

/**
 * Exception class for invalid commands.
 *
 * @param message The error message to be displayed.
 */
class CommandException(message: String) : Exception(message)
