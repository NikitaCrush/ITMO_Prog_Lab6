package client

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.*
import java.net.Socket

class ClientManager(private val host: String, private val port: Int) {
    private var socket: Socket? = null
    private var reader: BufferedReader? = null
    private var writer: PrintWriter? = null



    fun connect() {
        socket = Socket(host, port)
        reader = BufferedReader(InputStreamReader(socket!!.getInputStream()))
        writer = PrintWriter(BufferedWriter(OutputStreamWriter(socket!!.getOutputStream())))
    }

    fun disconnect() {
        reader?.close()
        writer?.close()
        socket?.close()
    }

    fun sendCommand(commandData: CommandData) {
        val commandWithArgs = commandData.copy(arguments = commandData.arguments.map { it.copy(value = it.value) })
        val serializedCommand = Json.encodeToString(commandWithArgs)
        writer?.println(serializedCommand)
        writer?.flush()
    }


    fun receiveResponse(): Response {
        val serializedResponse = reader?.readLine()
        if (serializedResponse.isNullOrBlank()) {
            throw IllegalStateException("No response received from the server.")
        }
        return Json.decodeFromString(serializedResponse)
    }

    fun sendCommandAndGetResponse(commandData: CommandData): Response {
        sendCommand(commandData)
        return receiveResponse()
    }
}
