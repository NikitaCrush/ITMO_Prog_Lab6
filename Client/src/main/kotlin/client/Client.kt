package client

import workWithServer.*
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.InetSocketAddress
import java.nio.channels.SocketChannel

/**
 * A class representing a client that communicates with the server.
 *
 */

class Client {

    /**
     * A mutable list of new commands received from the server.
     */

    val listOfNewCommands= mutableListOf<String>()

    /**
     * Establishes a connection to the server and returns a SocketChannel object for communication.
     *
     * @return The SocketChannel object representing the connection to the server.
     * @throws RuntimeException if the connection is unsuccessful.
     */

    fun connection(): SocketChannel {
        return try {
            val clientSocket = SocketChannel.open()
            clientSocket.socket().connect(InetSocketAddress("localhost", 3334))
            clientSocket
        } catch (e: RuntimeException) {
            println("Bad connection")
            SocketChannel.open(InetSocketAddress("localhost", 3334))
            throw e
        }
    }

    /**
     * Sends a Task object to the server through the output stream and handles the response through the input stream.
     *
     * @param task The Task object to be sent to the server.
     */

    fun outputStreamHandler(task: Task) {
        try {
            val clientSocket = connection()
            if (clientSocket.isConnected) {
                val objectOutputStream = ObjectOutputStream(clientSocket.socket().getOutputStream())
                objectOutputStream.writeObject(task)
                inputSteamHandler(clientSocket, task)
            }
        } catch (e: Exception) {
            println("Bad output")
        }
    }

    /**
     * Handles the server's response received through the input stream.
     *
     * @param clientSocket The SocketChannel object representing the connection to the server.
     * @param task The Task object that was sent to the server.
     */

    fun inputSteamHandler(clientSocket: SocketChannel, task: Task) {
        val objectInputStream = ObjectInputStream(clientSocket.socket().getInputStream())
        val answer = objectInputStream.readObject() as Answerer
        listOfNewCommands.addAll(answer.listOfNewCommand)
        println(answer.result)
        clientSocket.close()
    }

    /**
     * Returns the list of new commands received from the server.
     *
     * @return The mutable list of new commands received from the server.
     */

    fun returnNewCommands(): MutableList<String>{
        return listOfNewCommands
    }

    /**
     * Clears the list of new commands received from the server.
     */

    fun resetNewCommands(){
        listOfNewCommands.clear()
    }
}