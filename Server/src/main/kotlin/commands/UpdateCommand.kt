package commands

import exeptions.ValidationException
import utils.LabWorkReader

/**
 * The UpdateCommand class is responsible for updating a specific lab work in the collection
 * by providing a valid ID.
 *
 * @property labWorkCollection The lab work collection to be updated.
 * @property validator The validator used for input validation.
 */
class UpdateCommand : Command() {

    override fun execute(args: List<Any>): String {
        if (args.isEmpty() || args[0] !is String) {
            return "ID is not provided or has an incorrect format."
        }

        val id: Long = try {
            args[0].toString().toLong()
        } catch (e: NumberFormatException) {
            return "Invalid ID format. Please enter a valid number."
        }

        val labWorkToUpdate = labWorkCollection.show().find { it.id == id }

        return if (labWorkToUpdate != null) {
            try {
                val labWorkReader = LabWorkReader({ readlnOrNull() ?: "" }, validator)
                val updatedLabWork = labWorkReader.readLabWork(id, labWorkToUpdate.creationDate)
                labWorkCollection.update(id, updatedLabWork)
                "Lab work with ID: $id has been updated."
            } catch (e: ValidationException) {
                "Failed to update lab work due to invalid input: ${e.message}"
            }
        } else {
            "No lab work found with ID: $id."
        }
    }


}
