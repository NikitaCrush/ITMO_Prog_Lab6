package server

import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin
import org.koin.dsl.module
import utils.*
import java.util.*

val koinModule = module {
    single { Stack<String>() }
    single { Validator() }
    single {
        val fileName = System.getenv("LAB_WORK_FILE") ?: "collection.json"
        LabWorkCollection.fromFile(fileName)
    }
}

fun main() {
    startKoin {
        modules(koinModule)
    }

    val commandExecutor = CommandExecutor(ConsolePrinter())
    val serverManager = ServerManager(12345) // assuming 12345 is your port number

    runBlocking {
        serverManager.startServer()
        while (true) {
            val commandData = serverManager.receiveCommandData()
            val command = commandExecutor.getCommand(commandData.commandName)
            val result = command?.execute(commandData.parameters) ?: "Command not found"
            val response = Response(result.isNotEmpty(), result)
            serverManager.sendResponse(response)
        }
    }
}
