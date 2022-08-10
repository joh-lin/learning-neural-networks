import com.johannes.convneuralnetwork.*
import com.johannes.neuralnetwork.ActivationFunction
import com.johannes.neuralnetwork.NetworkLayer
import com.johannes.neuralnetwork.NeuralNetwork
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
    - Cross-Entropy cost function
    - Weight Initialization ☑
    - Gradient Clipping
    - Weight Regularization
    - Weight Penalty
 */

fun main() {
    test2()
}
fun test2() {
    val convnet = ConvolutionLayer(
        Shape(5, 5, 1),
        1,3,false
    )

    val res = convnet.propagate(arrayOf(arrayOf(
        doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0),
        doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0),
        doubleArrayOf(0.0, 0.0, 5.0, 0.0, 0.0),
        doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0),
        doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0)
    )))

    res.first().forEach { println(it.toList()) }
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
        listOf(2, 2, 2), ActivationFunction(ActivationFunction.Type.ReLU), layers
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