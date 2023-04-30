// ConnectionHandler.kt
package server

import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.nio.channels.SocketChannel

class ConnectionHandler(private val socketChannel: SocketChannel) {

    fun handleConnection() {
        val socket = socketChannel.socket()
        val input = ObjectInputStream(socket.getInputStream())
        val output = ObjectOutputStream(socket.getOutputStream())

        try {
            while (true) {
                val request = input.readObject() as Request
                val response = request.command.execute(request.args)
                output.writeObject(Response(response))
                output.flush()
            }
        } catch (e: Exception) {
            println("Client disconnected.")
        } finally {
            socket.close()
        }
    }
}
