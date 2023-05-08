// Response.kt
package server

import kotlinx.serialization.Serializable

@Serializable
data class Response(val result: String, val availableCommands: Set<String>)
