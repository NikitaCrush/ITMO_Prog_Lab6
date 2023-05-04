package workWithServer

class Tokenizator{

    /**
     * Метод для токенизации команд
     * @param command
     * @param path
     * @param history
     * @return MutableList<String>
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