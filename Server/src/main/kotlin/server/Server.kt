package server

import java.net.InetSocketAddress
import java.nio.channels.ServerSocketChannel
import java.nio.channels.SocketChannel

class Server(private val port: Int) {

    fun start() {
        val serverChannel = ServerSocketChannel.open()
        serverChannel.bind(InetSocketAddress(port))
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
        val connectionHandler = ConnectionHandler(socketChannel)
        connectionHandler.handleConnection()
    }
}

