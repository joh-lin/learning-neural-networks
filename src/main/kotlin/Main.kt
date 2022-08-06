import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.ejml.simple.SimpleMatrix
import kotlin.random.Random
import kotlin.system.measureTimeMillis

/* TODO:
    Types:
    - Convolutional Network
    - Recurrent Network
    - LSTM (Long short-term memory)
    - NEAT (Neuroevolution of augmenting topologies)
    - Neuroevolution
    Learning:
    - Reinforcement Learning
    - learning beyond training data
    Other:
    - Weight Initialization ☑
    - Gradient Clipping
    - Weight Regularization
    - Weight Penalty
    - Cross-Entropy cost function
 */

fun main() {
    val neuralNetwork = NeuralNetwork(
        listOf(5, 5, 1), ActivationFunction(ActivationFunction.Type.ReLU)
    )

    for (e in 0..1_000_000) {
        val input = mutableListOf<Double>()
        var avg = 0.0
        for (i in 0..4) {
            val v = Random.nextDouble()
            input.add(v)
            avg += v / 5
        }
        val result = neuralNetwork.learn(input, listOf(avg), 0.1)
        if (e > 900_000)println("i: $input = $avg / ${result.finalOutput.get(0,0)}   ->  ${result.totalError}")
    }
}

fun test1() {
    val layers = mutableListOf<NetworkLayer>(
        // layer 1
        NetworkLayer(2, 2, SimpleMatrix(arrayOf(
            doubleArrayOf(.29, .46, .86),
            doubleArrayOf(.08, .98, .69)))),

        // layer 2
        NetworkLayer(2, 2, SimpleMatrix(arrayOf(
            doubleArrayOf(.50, .82, .76),
            doubleArrayOf(.94, .20, .26))))
    )
    val neuralNetwork = NeuralNetwork(
        listOf(2, 2, 2),ActivationFunction(ActivationFunction.Type.ReLU)
    )

    val input = listOf( .28 , .57 )

    val target = listOf( .75 , .25 )

    val output = neuralNetwork.propagate(input)

    println("\nBACKPROPAGATION\n")

    val training = neuralNetwork.learn(input, target, 1.0)

    println("""
TotalError: ${training.totalError}
Error: ${training.error}
    """.trimIndent())
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