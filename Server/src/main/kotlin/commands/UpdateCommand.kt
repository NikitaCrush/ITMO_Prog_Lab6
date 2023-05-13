package commands

import data.LabWork
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

/**
 * The UpdateCommand class is responsible for updating a specific lab work in the collection
 * by providing a valid ID.
 *
 * @property labWorkCollection The lab work collection to be updated.
 * @property validator The validator used for input validation.
 */


class UpdateCommand : Command() {
    override fun execute(args: List<Any>): String {
        if (args.size < 2 || args[0] !is String) {
            return "ID and/or LabWork object is not provided or has an incorrect format."
        }

        val id: Long = try {
            args[0].toString().toLong()
        } catch (e: NumberFormatException) {
            return "Invalid ID format. Please enter a valid number."
        }

        val labWorkJson = args[1] as String
        val updatedLabWork = Json.decodeFromString<LabWork>(labWorkJson)

        val labWorkToUpdate = labWorkCollection.show().find { it.id == id }

        return if (labWorkToUpdate != null) {
            labWorkCollection.update(id, updatedLabWork)
            "Lab work with ID: $id has been updated."
        } else {
            "No lab work found with ID: $id."
        }
    }
}
