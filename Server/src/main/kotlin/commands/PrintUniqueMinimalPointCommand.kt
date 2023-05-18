package commands

/**
 * The PrintUniqueMinimalPointCommand class prints the unique minimalPoint values of all
 * lab works in the collection.
 *
 * @property labWorkCollection The lab work collection to be used for printing unique minimalPoint values.
 */
class PrintUniqueMinimalPointCommand : Command() {
    override fun execute(args: List<Any>): String {
        return labWorkCollection.printUniqueMinimalPoint().joinToString("\n")
    }
}
