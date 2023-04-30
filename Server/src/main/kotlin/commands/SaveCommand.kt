package commands

import data.Messages

/**
 * The SaveCommand class saves the lab work collection to a file.
 *
 * @property labWorkCollection The lab work collection to be saved.
 */
class SaveCommand : Command() {
    override fun execute(args: List<Any>): String {
        labWorkCollection.saveToFile()
        return Messages.LAB_WORK_SUCCESS_SAVE
    }

    override fun readArguments(input: () -> String): List<String> {
        return emptyList()
    }
}
