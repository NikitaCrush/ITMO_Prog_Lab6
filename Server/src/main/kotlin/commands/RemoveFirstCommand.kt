package commands

/**
 * The RemoveFirstCommand class removes the first element in the lab work collection.
 *
 * @property labWorkCollection The lab work collection to remove the first element from.
 */
class RemoveFirstCommand : Command() {
    override fun execute(args: List<Any>): String {
        labWorkCollection.removeFirst()
        return "First element removed successfully."
    }

    override fun readArguments(input: () -> String): List<String> = emptyList()
}
