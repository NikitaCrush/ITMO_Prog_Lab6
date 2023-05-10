package client

import client.CommandData

class Task(private val commandData: CommandData) {
    fun execute(clientManager: ClientManager): Response {
        clientManager.sendCommand(commandData)
        return clientManager.receiveResponse()
    }
}
