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
        val labWork = args[0] as LabWork
        labWorkCollection.add(labWork)
        return Messages.LAB_WORK_SUCCESS_ADD
    }

    override fun readArguments(input: () -> String): List<Any> {
        val labWorkReader = LabWorkReader(input, validator)
        val labWork = labWorkReader.readLabWork()
        return listOf(labWork)
    }
}
