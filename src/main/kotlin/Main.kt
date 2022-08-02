
import org.ejml.simple.SimpleMatrix

/*
 Idea for test:
 make the network say if number is even or uneven
 */

fun main() {

    val neuralNetwork = NeuralNetwork(
        listOf(3, 5, 4, 7),
        ReLUFunction()
    )

    println(neuralNetwork.process(listOf(3.0, 2.0, 1.0)))



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