import server.Server
import utils.CommandExecutor
import utils.ConsolePrinter

fun main() {
    val printer = ConsolePrinter()
    val commandExecutor = CommandExecutor(printer)
    val server = Server(3334, commandExecutor)
    server.start()
}
