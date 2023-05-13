package commands

/**
 * The ShowCommand class displays all lab works in the collection.
 *
 * @property labWorkCollection The lab work collection to be displayed.
 */
class ShowCommand : Command() {
    override fun execute(args: List<Any>): String {
        return labWorkCollection.show().joinToString(separator = "\n")
    }

}
