package utils

/**
 * A Printer implementation that outputs messages to the console with a prefix "[OUTPUT]: ".
 */
class ConsolePrinter : Printer {

    /**
     * Prints a message without a line break.
     *
     * @param message The message to print.
     */
    override fun print(message: String) {
        kotlin.io.print("[OUTPUT]: $message")
    }

    /**
     * Prints a message followed by a line break.
     *
     * @param message The message to print.
     */
    override fun println(message: String) {
        kotlin.io.println("[OUTPUT]: $message")
    }
}
