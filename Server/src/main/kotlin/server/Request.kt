// Request.kt
package server

import commands.Command
import java.io.Serializable

data class Request(val command: Command, val args: List<Any>) : Serializable
