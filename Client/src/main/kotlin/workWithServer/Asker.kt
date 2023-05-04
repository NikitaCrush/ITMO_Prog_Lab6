package workWithServer

import data.*
import java.time.LocalDate
import java.util.function.Predicate


typealias  TypeCaster<T> = (userInput: String) -> T

/**
 * A class representing an object that prompts the user for input and casts it to a specified type.
 *
 */

class Asker {

    /**
     * Prompts the user for input of a specific type, validates the input using a validator function, and returns the input value.
     *
     * @param caster A function that casts the user's input string to the desired type.
     * @param validator A predicate function that takes a value of the desired type as a parameter and checks if it is valid.
     * @return The user's input value of the desired type.
     */

    fun <T> readType(caster: TypeCaster<T>, validator: Predicate<T>): T {
        var output: T
        while (true) {
            try {
                val userInput = readln()
                output = caster(userInput)
            } catch (e: Exception) {
                when (e) {
                    is NumberFormatException -> {
                        println("Некорректный ввод числа. Попробуйте еще.")
                        continue
                    }

                    is IllegalArgumentException -> {
                        println("Некорректный ввод enum.")
                        continue
                    }

                    else -> {
                        println("Некорректный ввод. Попробуйте еще.")
                        continue
                    }
                }
            }
            if (validator.test(output)) {
                break
            } else {
                println("Некорректный ввод. Попробуйте еще.")
            }
        }
        return output
    }

    // Type casters for common data types:
    val toIntCaster: TypeCaster<Int> = { it.trim().toInt() }

    val toLongCaster: TypeCaster<Long> = { it.trim().toLong() }

    inline fun <reified T : Enum<T>> toEnumCaster(userInput: String): T {
        return enumValueOf(userInput.trim().uppercase())
    }


    inline fun <reified T : Enum<T>> toEnumOrNullCaster(userInput: String): T? {
        if (userInput.isEmpty()) {
            return null
        }
        return toEnumCaster<T>(userInput)
    }

    /**
     * Prompts the user for input of discipline information and returns a new Discipline object.
     *
     * @return A new Discipline object with information provided by the user.
     */

    fun askDiscipline(): Discipline {
        println("Введите название предмета, по которому дана лабораторная работа")
        val name = readType(caster = { it }, validator = { it.isNotEmpty() })
        println("Введите потраченное на выполнение лабораторной работы время")
        val selfStudyHours = readType(caster = toLongCaster, validator = { it > 0 })
        return Discipline(
            name,
            selfStudyHours
        )
    }

    /**
     * Prompts the user for input of coordinate information and returns a new Coordinates object.
     *
     * @return A new Coordinates object with information provided by the user.
     */

    fun askCoordinates(): Coordinates {
        println("Введите координату х")
        val x = readType(caster = toLongCaster, validator = { it <= 608 })
        println("Введите координату у")
        val y = readType(caster = toLongCaster, validator = { it > 0 })
        return Coordinates(x, y)
    }

    /**
     * Prompts the user for input of laboratory work information and returns a new LabWork object.
     *
     * @return A new LabWork object with information provided by the user.
     */

    fun askLabWork(): LabWork {
        println("Введите id лабораторной работы")
        val id = readType(caster = toLongCaster, validator = { it > 0 })

        println("Введите назввание лабораторной работы")
        val name = readType(caster = { it }, validator = { it.isNotEmpty() })

        println("Введите координаты")
        val coordinates = askCoordinates()

        println("Введите дату создания лабораторной работы")
        val creationDate = LocalDate.now()

        println("Введите минимальную оценку за лабораторную работу")
        val minimalPoint = readType(caster = toIntCaster, validator = { it > 0 })

        println("Введите минимальные скилы студента")
        val personalQualitiesMinimum = readType(caster = toIntCaster, validator = { it > 0 })

        println(
            "Введите сложность лабораторной работы ${
                Difficulty.values().map { it.toString() }
            } или оставьте строку пустой для null"
        )
        val difficultyInput = readLine() ?: ""
        val difficulty = if (difficultyInput.isNotEmpty()) Difficulty.valueOf(difficultyInput) else null

        println("Введите поля дисциплины")
        val discipline = askDiscipline()

        return LabWork(
            id,
            name,
            coordinates,
            creationDate,
            minimalPoint,
            personalQualitiesMinimum,
            difficulty,
            discipline
        )
    }

    /**
     * Prompts the user for input as a string and returns it.
     *
     * @return The user's input value as a string.
     */

    fun askCommand(): String {

        val command = readType(caster = { it }, validator = { it.isNotEmpty() })
        return command
    }

    /**
     * Prompts the user for input as a long integer and returns it.
     *
     * @return The user's input value as a long integer.
     */

    fun askLong(): Long {
        return readType(caster = toLongCaster, validator = { it > 0 })
    }

}