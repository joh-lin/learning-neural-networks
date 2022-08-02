import org.ejml.simple.SimpleMatrix
import java.lang.RuntimeException
import kotlin.random.Random

class NetworkLayer(val inputCount: Int, val neuronCount: Int, val neuralNetwork: NeuralNetwork) {
    private val weights = SimpleMatrix(neuronCount, inputCount+1)

    /* Random Weights
    init {
        for (row in 0 until weights.numRows()) {
            for (col in 0 until weights.numCols()) {
                weights.set(row, col, Random.nextDouble(0.0, 2.0))
            }
        }
    }*/

    fun process(input: SimpleMatrix): SimpleMatrix {
        if (input.numRows() != inputCount)
            throw RuntimeException(
                "Invalid number of inputs ( received ${input.numRows()} | expected $inputCount )")
        input.reshape(input.numRows()+1, 1)
        input.set(input.numRows()-1, 0, 1.0)
        val multResult = weights.mult(input)
        for (i in 0 until multResult.numRows()) {
            multResult.set(i, 0,
                neuralNetwork.activationFunction.process(multResult.get(i, 0))
            )
        }
        return multResult
    }
}