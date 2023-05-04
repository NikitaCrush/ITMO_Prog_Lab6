package workWithServer

/**
 * A class representing a tokenizer that splits a command string into separate components.
 *
 */

class Tokenizator{

    /**
     * Splits a command string into separate components and returns them as a mutable list of strings.
     *
     * @param command The command to tokenize.
     * @return A mutable list of strings containing the individual components of the command.
     */

    fun tokenizateCommand(command: String): MutableList<String> {
        val commandComponent1 = command.split(" ").toMutableList()
        val commandComponent2: MutableList<String> = listOf<String>().toMutableList()
        for (i in commandComponent1) {
            if (!(i.equals(""))) commandComponent2.add(i)
        }
        if (commandComponent2.size == 3) {
            commandComponent2[0] = commandComponent2[0] + " " + commandComponent2[1]
            commandComponent2[1] = commandComponent2[2]
            commandComponent2.removeAt(2)
        }
        return commandComponent2
    }
}