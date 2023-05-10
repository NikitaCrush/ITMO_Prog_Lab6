package data

import kotlinx.serialization.Serializable

/**
 * A data class representing coordinates with x and y values.
 *
 * @property x The x coordinate value.
 * @property y The y coordinate value.
 */
@Serializable
data class Coordinates(
    val x: Long,//Максимальное значение поля: 608, Поле не может быть null
    val y: Double//Поле не может быть null
) : java.io.Serializable