package commands

/**
 * The InfoCommand class displays information about the lab work collection.
 *
 * @property labWorkCollection The lab work collection to get information about.
 */
class InfoCommand : Command() {
    override fun execute(args: List<Any>): String {
        return labWorkCollection.getInfo()
    }


}
