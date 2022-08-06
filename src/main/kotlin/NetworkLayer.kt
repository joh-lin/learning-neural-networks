import kotlinx.serialization.Serializable
import org.ejml.simple.SimpleMatrix
import java.lang.RuntimeException

@Serializable
class NetworkLayer(
    val inputCount: Int,
    val neuronCount: Int,
    @Serializable(with = SimpleMatrixSerializer::class)
    val weights: SimpleMatrix = SimpleMatrix(neuronCount, inputCount+1)) {

    /** Create output from input and weights of this layer
     * @param input Input values.
     * @return Output values.
     */
    fun process(input: SimpleMatrix, activationFunction: ActivationFunction): ProcessResult {
        // safety check
        if (input.numRows() != inputCount)
            throw RuntimeException(
                "Invalid number of inputs ( received ${input.numRows()} | expected $inputCount )")

        // create new input vector with bias "1" added
        val modifiedInput = SimpleMatrix(input.numRows()+1, 1)
        for (row in 0 until modifiedInput.numRows()-1) {
            modifiedInput.set(row, 0, input.get(row, 0))
        }
        modifiedInput.set(modifiedInput.numRows()-1, 0, 1.0)

        // calculate z Values
        val zValues = weights.mult(modifiedInput)

        // apply activation function
        val multResult = zValues.copy()
        for (i in 0 until multResult.numRows()) {
            multResult.set(i, 0,
                activationFunction.function(multResult.get(i, 0))
            )
        }

        // return result
        return ProcessResult(
            multResult, zValues
        )
    }

    fun initializeWeightsAndBiases(activationFunction: ActivationFunction) {
        for (row in 0 until weights.numRows()) {
            for (col in 0 until weights.numCols()) {
                weights.set(row, col,
                    if (col == weights.numCols()-1) activationFunction.biasInitialization()
                    else activationFunction.weightInitialization(inputCount)
                )
            }
        }
    }

    data class ProcessResult(
        val activations: SimpleMatrix,
        val zValues: SimpleMatrix,
    )
}