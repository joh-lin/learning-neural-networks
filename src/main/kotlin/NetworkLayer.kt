import kotlinx.serialization.Serializable
import org.ejml.simple.SimpleMatrix
import java.lang.RuntimeException

@Serializable
class NetworkLayer(
    val inputCount: Int,
    val neuronCount: Int,
    @Serializable(with = SimpleMatrixSerializer::class)
    val weights: SimpleMatrix = SimpleMatrix(neuronCount, inputCount+1)) {

    fun process(input: SimpleMatrix, activationFunction: ActivationFunction): SimpleMatrix {
        if (input.numRows() != inputCount)
            throw RuntimeException(
                "Invalid number of inputs ( received ${input.numRows()} | expected $inputCount )")
        input.reshape(input.numRows()+1, 1)
        input.set(input.numRows()-1, 0, 1.0)
        val multResult = weights.mult(input)
        for (i in 0 until multResult.numRows()) {
            multResult.set(i, 0,
                activationFunction.process(multResult.get(i, 0))
            )
        }
        return multResult
    }
}