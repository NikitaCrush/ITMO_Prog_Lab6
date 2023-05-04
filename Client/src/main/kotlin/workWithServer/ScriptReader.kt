package workWithServer

import java.io.BufferedReader
import java.io.FileReader

class ScriptReader{
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