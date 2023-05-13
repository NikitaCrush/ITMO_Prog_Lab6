package commands

import data.*
import utils.LabWorkReader
/**
 * The AddCommand class adds a new lab work to the collection.
 *
 * @property labWorkCollection The lab work collection to add the lab work to.
 * @property validator The validator used for input validation.
 */
class AddCommand : Command() {

    override fun execute(args: List<Any>): String {
        if (args.isEmpty()) {
            throw IllegalArgumentException("Add command expects 1 argument, but got none.")
        }

        val labWork = args[0] as? LabWork
            ?: throw IllegalArgumentException("Argument for add command is not of type LabWork.")

        labWorkCollection.add(labWork)
        return Messages.LAB_WORK_SUCCESS_ADD
    }
}
