package server

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import data.*
import java.io.*
import java.net.ServerSocket
import java.net.Socket

class ServerManager(private val port: Int) {
    private var serverSocket: ServerSocket? = null
    private var clientSocket: Socket? = null
    private var reader: BufferedReader? = null
    private var writer: PrintWriter? = null

    fun startServer() {
        serverSocket = ServerSocket(port)
        println("Server started on port $port")

        clientSocket = serverSocket!!.accept()
        println("Client connected: ${clientSocket!!.inetAddress.hostAddress}")

        reader = BufferedReader(InputStreamReader(clientSocket!!.getInputStream()))
        writer = PrintWriter(BufferedWriter(OutputStreamWriter(clientSocket!!.getOutputStream())))
    }

    fun stopServer() {
        reader?.close()
        writer?.close()
        clientSocket?.close()
        serverSocket?.close()
    }

    fun receiveCommandData(): CommandData {
        println("Receiving command data...")
        val serializedCommand = reader?.readLine()
        return Json.decodeFromString(serializedCommand ?: "")
    }

    fun sendResponse(response: Response) {
        println("Sending response...")
        val serializedResponse = Json.encodeToString(response)
        writer?.println(serializedResponse)
        writer?.flush()
    }

}
