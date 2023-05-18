package commands

import data.LabWork
import data.Messages
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

/**
 * The AddIfMaxCommand class adds a new lab work to the collection if its value is greater than the largest
 * element in the collection.
 *
 * @property labWorkCollection The lab work collection to add the lab work to.
 */
class AddIfMaxCommand : Command() {

    override fun execute(args: List<Any>): String {
        val labWorkJson = args[0] as String
        val labWork = Json.decodeFromString<LabWork>(labWorkJson)
        val added = labWorkCollection.addIfMax(labWork)
        return if (added) Messages.LAB_WORK_SUCCESS_ADD else Messages.LAB_WORK_NOT_MAX
    }



}