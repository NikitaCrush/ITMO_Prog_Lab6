package commands

import data.Messages

/**
 * The ClearCommand class clears the lab work collection.
 *
 * @property labWorkCollection The lab work collection to be cleared.
 */
class ClearCommand : Command() {
    override fun execute(args: List<Any>): String {
        labWorkCollection.clear()
        return Messages.LAB_WORK_SUCCESS_CLEAR
    }

    override fun readArguments(input: () -> String): List<String> {
        return emptyList()
    }
}
