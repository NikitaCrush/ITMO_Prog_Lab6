package utils

// CommandReader.kt
import java.util.Scanner

class CommandReader {
    private val scanner = Scanner(System.`in`)

    fun readCommand(): String {
        print("Enter command: ")
        return scanner.nextLine()
    }
}
