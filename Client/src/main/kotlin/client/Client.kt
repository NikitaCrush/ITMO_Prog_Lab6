//// Client.kt
//package client
//
//import utils.CommandReader
//import java.io.ObjectInputStream
//import java.io.ObjectOutputStream
//import java.net.Socket
//
//class Client(private val host: String, private val port: Int) {
//
//    fun start() {
//        val socket = Socket(host, port)
//        val commandReader = CommandReader()
//
//        println("Client started. Type 'exit' to stop.")
//
//        while (true) {
//            val command = commandReader.readCommand()
//
//            if (command == "exit") {
//                break
//            }
//
//            val objectOutputStream = ObjectOutputStream(socket.getOutputStream())
//            objectOutputStream.writeObject(Task(command))
//
//            val objectInputStream = ObjectInputStream(socket.getInputStream())
//            val response = objectInputStream.readObject()
//
//            println("Response: $response")
//        }
//    }
//}
