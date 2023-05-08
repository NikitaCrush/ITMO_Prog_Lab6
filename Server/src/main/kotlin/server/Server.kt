package server

import utils.CommandExecutor
import java.net.InetSocketAddress
import java.nio.channels.ServerSocketChannel
import java.nio.channels.SocketChannel
import java.util.concurrent.Executors

class Server(private val port: Int, private val commandExecutor: CommandExecutor) {

    private val threadPool = Executors.newFixedThreadPool(4)

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
        val connectionHandler = ConnectionHandler(socketChannel, commandExecutor)
        threadPool.submit { connectionHandler.handleConnection() }
    }
}
