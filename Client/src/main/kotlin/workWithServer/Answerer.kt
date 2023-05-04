package workWithServer

import java.io.Serializable


data class Answerer(
    var result: String = "Great job\n----------\n",
): Serializable {

    val listOfNewCommand= mutableListOf<String>()

    fun setNewCommands(list: List<String>){
        listOfNewCommand.addAll(list)
    }
}