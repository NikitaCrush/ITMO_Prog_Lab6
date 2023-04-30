package commands

/**
 * The RemoveByIdCommand class removes a lab work from the collection by providing a valid ID.
 *
 * @property labWorkCollection The lab work collection to remove the lab work from.
 */
class RemoveByIdCommand : Command() {
    override fun execute(args: List<Any>): String {
        val id = args[0] as Long
        val removed = labWorkCollection.removeById(id)
        return if (removed) {
            "Lab work removed successfully."
        } else {
            "No lab work found with the provided id."
        }
    }


}
