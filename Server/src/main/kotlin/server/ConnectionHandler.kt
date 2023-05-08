package server

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import utils.CommandArgumentsReader
import utils.CommandExecutor
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.nio.channels.SocketChannel

class ConnectionHandler(private val socketChannel: SocketChannel, private val commandExecutor: CommandExecutor) {

    private val availableCommands: Set<String> = commandExecutor.getAvailableCommands().keys
    private val commandArgumentsReader = CommandArgumentsReader()

    fun handleConnection() {
        val socket = socketChannel.socket()
        val input = BufferedReader(InputStreamReader(socket.getInputStream()))
        val output = PrintWriter(socket.getOutputStream(), true)

        try {
            while (true) {
                val jsonRequest = input.readLine()
                val request = Json.decodeFromString<Request>(jsonRequest)
                val command = commandExecutor.getCommand(request.commandName)
                val args = command?.let { commandArgumentsReader.getRequiredArgs(it, null, request.args.map { it.value }) }
                val responseText = command?.execute(args ?: emptyList()) ?: "Bad command"
                val jsonResponse = Json.encodeToString(Response(responseText, availableCommands))
                output.println(jsonResponse)
                output.flush()
            }
        } catch (e: Exception) {
            println("Client disconnected.")
        } finally {
            socket.close()
        }
    }

}
