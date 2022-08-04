import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.ejml.simple.SimpleMatrix
import kotlin.random.Random

/*
 Idea for test:
 make the network say if number is even or uneven
 */

fun main() {


    //val test = mutableListOf("First Entry", "second", "3", "quadro", "V")
    val test = NeuralNetwork(listOf(3,3,3), ActivationFunction(ActivationFunction.Type.ReLU))
    println(test)

    val encoded = Json.encodeToJsonElement(test).toString()
    println(
        encoded
    )

    println(
        Json.decodeFromString<NeuralNetwork>(encoded)
    )

    /*val neuralNetwork = NeuralNetwork(
        listOf(3, 5, 4, 7),
        ReLUFunction()
    )

    println(neuralNetwork.process(listOf(3.0, 2.0, 1.0)))*/



    /*val firstMatrix = SimpleMatrix(
        arrayOf(
            doubleArrayOf(1.0, 5.0),
            doubleArrayOf(2.0, 3.0),
            doubleArrayOf(1.0, 7.0)))

    val secondMatrix = SimpleMatrix(
        arrayOf(
            doubleArrayOf(1.0, 2.0, 3.0, 7.0),
            doubleArrayOf(5.0, 2.0, 8.0, 1.0)))

    val expected = SimpleMatrix(
        arrayOf(
            doubleArrayOf(26.0, 12.0, 43.0, 12.0),
            doubleArrayOf(17.0, 10.0, 30.0, 17.0),
            doubleArrayOf(36.0, 16.0, 59.0, 14.0)
        )
    )

    val actual = firstMatrix.mult(secondMatrix)

    actual.print()*/

}

fun SimpleMatrix.filLRandomDouble(min: Double, max: Double) {
    for (row in 0 until this.numRows()) {
        for (col in 0 until this.numCols()) {
            this.set(row, col, Random.nextDouble(min, max))
        }
    }
}

fun SimpleMatrix.filLRandomInt(min: Int, max: Int) {
    for (row in 0 until this.numRows()) {
        for (col in 0 until this.numCols()) {
            this.set(row, col, Random.nextInt(min, max).toDouble())
        }
    }
}