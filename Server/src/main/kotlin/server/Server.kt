package server


import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.nio.channels.ServerSocketChannel
import java.nio.channels.SocketChannel

class Server(private val port: Int) {

    fun start() {
        val serverChannel = ServerSocketChannel.open()
        serverChannel.bind(java.net.InetSocketAddress(port))
        serverChannel.configureBlocking(false)

        println("Server started on port $port")

        while (true) {
            val socketChannel = serverChannel.accept()
            if (socketChannel != null) {
                handleConnection(socketChannel)
            }
        }
    }

    private fun handleConnection(socketChannel: SocketChannel) {
        val socket = socketChannel.socket()
        val input = ObjectInputStream(socket.getInputStream())
        val output = ObjectOutputStream(socket.getOutputStream())

        val command = input.readObject() as Command
        val response = command.execute()

        output.writeObject(response)
        output.flush()

        socket.close()
    }
}

fun main() {
    val server = Server(8080)
    server.start()
}
