package workWithServer

import data.*
import java.time.LocalDate
import java.util.function.Predicate


typealias  TypeCaster<T> = (userInput: String) -> T

/**
 * Класс для работы команды insert. Пошагово вводит поля, запрпашивая от пользователя
 * введение правильных данных при их ошибочном введении
 * @param toIntCaster перевод к типу Int
 * @param toLongCaster перевод к типу Long
 */
class Asker {

    /**
     * Метод чтения данных, введенных пользователем и попытке привести их
     * к нужному типу данных
     *
     * @param caster
     * @param validator
     * @return T
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

    val toIntCaster: TypeCaster<Int> = { it.trim().toInt() }

    val toLongCaster: TypeCaster<Long> = { it.trim().toLong() }

    /**
     * Метод для приведения введенных данных к значению Enum
     * @param userInput
     */
    inline fun <reified T : Enum<T>> toEnumCaster(userInput: String): T {
        return enumValueOf(userInput.trim().uppercase())
    }

    /**
     * Метод для приведения введенных данных к значению Enum (с учетом того, что данные могут быть null)
     * @param userInput
     */
    inline fun <reified T : Enum<T>> toEnumOrNullCaster(userInput: String): T? {
        if (userInput.isEmpty()) {
            return null
        }
        return toEnumCaster<T>(userInput)
    }

    /**
     * Метод для введения полей класса Discipline
     * @return Discipline
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
     * Метод для введения полей класса Coordinates
     * @return Coordinates
     */
    fun askCoordinates(): Coordinates {
        println("Введите координату х")
        val x = readType(caster = toLongCaster, validator = { it <= 608 })
        println("Введите координату у")
        val y = readType(caster = toLongCaster, validator = { it > 0 })
        return Coordinates(x, y)
    }

    /**
     * Метод для введения полей класса LabWork
     * @return LabWork
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
            "Введите сложность лабораторной рпаботы ${
                Difficulty.values().map { it.toString() }
            } или оставьте строку пустой для null"
        )

        println("Введите поля дисциплины")
        val discipline = askDiscipline()

        return LabWork(
            id,
            name,
            coordinates,
            creationDate,
            minimalPoint,
            personalQualitiesMinimum,
            discipline
        )
    }

    /**
     * Метод запроса команд
     * @return String
     */
    fun askCommand(): String {

        val command = readType(caster = { it }, validator = { it.isNotEmpty() })
        return command
    }

    /**
     * Метод запроса числа типа Long
     * @return Long
     */
    fun askLong(): Long {
        return readType(caster = toLongCaster, validator = { it > 0 })
    }

}