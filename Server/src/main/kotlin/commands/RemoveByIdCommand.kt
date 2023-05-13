package commands

/**
 * The RemoveByIdCommand class removes a lab work from the collection by providing a valid ID.
 *
 * @property labWorkCollection The lab work collection to remove the lab work from.
 */
class RemoveByIdCommand : Command() {
    override fun execute(args: List<Any>): String {
        if (args.isEmpty() || args[0] !is String) {
            return "ID is not provided or has an incorrect format."
        }

        val id: Long = try {
            args[0].toString().toLong()
        } catch (e: NumberFormatException) {
            return "Invalid ID format. Please enter a valid number."
        }

        val removed = labWorkCollection.removeById(id)
        return if (removed) {
            "Lab work removed successfully."
        } else {
            "No lab work found with the provided id."
        }
    }
}


