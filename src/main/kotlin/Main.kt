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
    - Softmax
    - Weight Initialization ☑
    - Gradient Clipping
    - Weight Regularization
    - Weight Penalty
    - Cross-Entropy cost function
 */

fun main() {
    test2()
}
fun test2() {
    val neuralNetwork = NeuralNetwork(
        listOf(10, 10000, 10000, 1), ActivationFunction(ActivationFunction.Type.ReLU)
    )

    println(neuralNetwork.layers)

    val input = NeuralNetwork.listToColumnVector(listOf(0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0))
    val output = NeuralNetwork.listToColumnVector(listOf(0.5, 1.0, 0.0, 0.0, 0.0, 0.5, 1.0, 0.0, 0.0, 0.0))

    measureTimeMillis {
        for (e in 0..1_000) {
            neuralNetwork.propagate(input)
        }
    }.also { println(it) }

    println("""
        Reshape: ${Benchmark.reshapeTime}
        Mult: ${Benchmark.multTime}
        Activation: ${Benchmark.activationFunctionTime}
    """.trimIndent())

    println(neuralNetwork.propagate(input))
}

fun test1() {
    val layers = mutableListOf<NetworkLayer>(
        // layer 1
        NetworkLayer(2, 2, SimpleMatrix(arrayOf(
            doubleArrayOf(.29, .46),
            doubleArrayOf(.08, .98))),SimpleMatrix(arrayOf(
            doubleArrayOf(.86),
            doubleArrayOf(.69)))
        ),

        // layer 2
        NetworkLayer(2, 2, SimpleMatrix(arrayOf(
            doubleArrayOf(.50, .82),
            doubleArrayOf(.94, .20))),SimpleMatrix(arrayOf(
            doubleArrayOf(.76),
            doubleArrayOf(.26))))
    )
    val neuralNetwork = NeuralNetwork(
        listOf(2, 2, 2),ActivationFunction(ActivationFunction.Type.ReLU), layers
    )

    val input = listOf( .28 , .57 )

    val target = listOf( .75 , .25 )

    val output = neuralNetwork.propagate(input)

    println(output)

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