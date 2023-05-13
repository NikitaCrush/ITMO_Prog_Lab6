package server

import data.LabWork
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import utils.CommandExecutor
import java.io.*
import java.net.ServerSocket
import java.net.Socket

class ServerManager(private val port: Int) {
    private var serverSocket: ServerSocket? = null
    private var clientSocket: Socket? = null
    private var reader: BufferedReader? = null
    private var writer: PrintWriter? = null
    private var commandExecutor: CommandExecutor? = null

    fun startServer(commandExecutor: CommandExecutor) {
        this.commandExecutor = commandExecutor

        serverSocket = ServerSocket(port)
        println("Server started on port $port")

        clientSocket = serverSocket!!.accept()
        println("Client connected: ${clientSocket!!.inetAddress.hostAddress}")

        reader = BufferedReader(InputStreamReader(clientSocket!!.getInputStream()))
        writer = PrintWriter(BufferedWriter(OutputStreamWriter(clientSocket!!.getOutputStream())))

        handleCommands()
    }


    fun stopServer() {
        reader?.close()
        writer?.close()
        clientSocket?.close()
        serverSocket?.close()
    }

    private fun receiveCommandData(): CommandData {
        println("Receiving command data...")
        val serializedCommand = reader?.readLine()
        return Json.decodeFromString(serializedCommand ?: "")
    }

    private fun sendResponse(response: Response) {
        println("Sending response...")
        val serializedResponse = Json.encodeToString(response)
        writer?.println(serializedResponse)
        writer?.flush()
    }


    private fun handleCommands() {
        while (true) {
            val commandData = receiveCommandData()
            if (commandData.commandName == "getCommands") {
                val commands = commandExecutor?.getAvailableCommands()?.keys
                if (commands != null) {
                    // Join the commands with ";" instead of ","
                    sendResponse(Response(true, commands.joinToString(";")))
                }
            } else {
                val command = commandExecutor?.getCommand(commandData.commandName)
                if (command != null) {
                    if (commandData.commandName == "add") {
                        // Deserialize the LabWork instance from JSON
                        val labWork = Json.decodeFromString<LabWork>(commandData.arguments[0].value!!)
                        val responseMessage = command.execute(listOf(labWork))  // Pass the deserialized LabWork instance to the execute() method.
                        sendResponse(Response(true, responseMessage))
                    } else {
                        val responseMessage = command.execute(commandData.arguments.map { it.value!! })  // Assuming the execute() method returns a response message.
                        sendResponse(Response(true, responseMessage))
                    }
                } else {
                    sendResponse(Response(false, "Command not found: ${commandData.commandName}"))
                }
            }
        }
    }





}
