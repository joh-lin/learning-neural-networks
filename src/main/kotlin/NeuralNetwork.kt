import org.ejml.simple.SimpleMatrix
import java.lang.IndexOutOfBoundsException

/** A neural network
 * @param dimensions [I, L1, L2,..., O]
 *     I: number of inputs
 *     L1: neurons on second layer
 *     O: number of outputs
 */
class NeuralNetwork(private val dimensions: List<Int>, val activationFunction: ActivationFunction) {
    private val layers = mutableListOf<NetworkLayer>()
    private val inputCount: Int
    private val outputCount: Int

    init {
        if (dimensions.size <= 2) {
            throw RuntimeException("Dimensions array for neural network needs at least two layers (input and output).")
        }

        // construct network
        inputCount = dimensions.first()
        outputCount = dimensions.last()

        for (i in 1 until dimensions.size) {
            val newLayer = NetworkLayer(dimensions[i-1], dimensions[i], this)
            layers.add(newLayer)
        }
    }

    fun process(input: List<Double>): List<Double> {
        val matrix = SimpleMatrix(input.size, 1)
        for (i in input.indices) matrix.set(i, 0, input[i])

        val matrixResult = process(matrix)

        val output = mutableListOf<Double>()
        for (i in 0 until matrixResult.numRows()) {
            output.add(matrixResult.get(i, 0))
        }

        return output
    }

    fun process(input: SimpleMatrix): SimpleMatrix {
        var lastOutput = input
        for (layer: NetworkLayer in layers) {
            lastOutput = layer.process(lastOutput)
        }
        return lastOutput
    }
}