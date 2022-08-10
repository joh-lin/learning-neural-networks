package com.johannes.neuralnetwork

import com.johannes.util.SimpleMatrixSerializer
import kotlinx.serialization.Serializable
import org.ejml.simple.SimpleMatrix

@Serializable
class NetworkLayer(
    val inputCount: Int,
    val neuronCount: Int,
    @Serializable(with = SimpleMatrixSerializer::class)
    val weights: SimpleMatrix = SimpleMatrix(neuronCount, inputCount),
    @Serializable(with = SimpleMatrixSerializer::class)
    val bias: SimpleMatrix = SimpleMatrix(neuronCount, 1)) {

    /** Create output from input and weights of this layer
     * @param input Input values.
     * @return Output values.
     */
    fun process(input: SimpleMatrix, activationFunction: ActivationFunction): ProcessResult {
        // calculate zValues with matrix multiplication
        val zValues = weights.mult(input).plus(bias)

        // apply activation function, store in aValues
        val aValues = SimpleMatrix(zValues.numRows(), zValues.numCols())
        for (i in 0 until zValues.numElements) {
            aValues.set(i, activationFunction.function(zValues.get(i)))
        }

        // return result
        return ProcessResult(
            aValues, zValues
        )
    }

    fun initializeWeightsAndBiases(activationFunction: ActivationFunction) {
        for (i in 0 until weights.numElements) {
            weights.set(i, activationFunction.weightInitialization(inputCount))
        }
        for (i in 0 until bias.numElements) {
            bias.set(i, activationFunction.biasInitialization())
        }
    }

    data class ProcessResult(
        val activations: SimpleMatrix,
        val zValues: SimpleMatrix,
    )

    override fun toString(): String {
        return "NetworkLayer(inputCount = $inputCount, neuronCount = $neuronCount)"
    }
}