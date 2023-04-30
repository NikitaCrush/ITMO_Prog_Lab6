package utils

/**
 * An interface for printing messages.
 */
interface Printer {

    /**
     * Prints a message without a line break.
     *
     * @param message The message to print.
     */
    fun print(message: String)

    /**
     * Prints a message followed by a line break.
     *
     * @param message The message to print.
     */
    fun println(message: String)
}
