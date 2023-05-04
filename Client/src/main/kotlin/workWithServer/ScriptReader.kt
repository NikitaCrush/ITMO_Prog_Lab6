package workWithServer

import java.io.BufferedReader
import java.io.FileReader

/**
 * A class representing a script reader that reads commands from a script file and generates tasks based on them.
 *
 */

class ScriptReader{

    /**
     * Reads commands from a script file and generates tasks based on them.
     *
     * @param path The path to the script file.
     * @param tokenizator An instance of the Tokenizator class which is used to tokenize the commands in the script.
     * @param historyOfPaths A list of paths that have already been processed to prevent infinite recursion in case of loops.
     * @return A mutable list of tasks generated from the commands in the script.
     */

    fun readScript(
        path: String,
        tokenizator: Tokenizator,
        historyOfPaths: MutableList<String>,
    ): MutableList<Task> {
        try {
            if (!historyOfPaths.contains(path)) {
                historyOfPaths.add(path)
                val bufferedReader = BufferedReader(FileReader(path))
                val listOfTasks = mutableListOf<Task>()
                while (true) {
                    if (bufferedReader.ready()) {
                        val components = tokenizator.tokenizateCommand(bufferedReader.readLine())
                        if (components[0] == "execute_script") {
                            val extensionListOfTask = readScript(components[1], tokenizator, historyOfPaths)
                            listOfTasks.addAll(extensionListOfTask)
                        } else {
                            listOfTasks.add(Task(components))
                        }
                    } else {
                        break
                    }
                }
                return listOfTasks
            } else {
                println("Данный файл был использован ${path}")
                return mutableListOf()
            }
        } catch (e: Exception) {
            println("Problem with script")
            return mutableListOf()
        }
    }


}