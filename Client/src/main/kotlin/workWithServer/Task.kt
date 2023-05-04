package workWithServer

import data.LabWork
import java.io.Serializable

data class Task(
    val definition: MutableList<String>, //описание таски
    var labWork: LabWork? = null,
    val listOfCommands: MutableList<String>? = null,
) : Serializable {
}