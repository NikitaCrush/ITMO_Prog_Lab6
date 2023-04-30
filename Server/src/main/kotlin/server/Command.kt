package server

import java.io.Serializable

interface Command : Serializable {
    fun execute(): Response
}
