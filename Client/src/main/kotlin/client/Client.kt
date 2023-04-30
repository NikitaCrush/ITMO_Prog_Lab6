package client

import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.Socket

class Client(private val host: String, private val port: Int) {

    fun sendCommand(command: Command): Response {
        Socket(host, port).use { socket ->
            ObjectOutputStream(socket.getOutputStream()).use { output ->
                output.writeObject(command)
                output.flush()

                ObjectInputStream(socket.getInputStream()).use { input ->
                    return input.readObject() as Response
                }
            }
        }
    }
}

fun main() {
    val client = Client("localhost", 8080)

    // Read commands from the console, validate input data, serialize the entered command, and send it to the server.
    // Process the response from the server and output the command execution result to the console.
}
