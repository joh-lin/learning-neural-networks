package com.johannes.neuralnetwork

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.ejml.simple.SimpleMatrix
import kotlin.math.pow

/** A neural network
 * @param dimensions [I, L1, L2,..., O]
 *     I: number of inputs
 *     L1: neurons on second layer
 *     O: number of outputs
 */
@Serializable
class NeuralNetwork(
    val dimensions: List<Int>,
    val activationFunction: ActivationFunction,
    val layers: MutableList<NetworkLayer> = mutableListOf()) {
    val inputCount: Int
    val outputCount: Int

    init {
        if (dimensions.size <= 1) {
            throw RuntimeException("Dimensions array for neural network needs at least two layers (input and output).")
        }

        inputCount = dimensions.first()
        outputCount = dimensions.last()

        // if layers already filled, network was created from deserialization
        if (layers.size == 0) {

            // construct network

            for (i in 1 until dimensions.size) {
                val newLayer = NetworkLayer(dimensions[i - 1], dimensions[i])
                newLayer.initializeWeightsAndBiases(activationFunction)
                layers.add(newLayer)
            }
        }
    }

    /** Produce an output based on this network and the input.
     * @param input List representation of input.
     * @return Output in List format.
     */
    fun propagate(input: List<Double>): List<Double> {
        return columnVectorToList(
            propagate(
                listToColumnVector(input)
            )
        )
    }

    /** Produce an output based on this network and the input.
     * @param input Matrix representation of input.
     * @return Output in Matrix format.
     */
    fun propagate(input: SimpleMatrix): SimpleMatrix {
        var lastOutput = input
        for (layer: NetworkLayer in layers) {
            lastOutput = layer.process(lastOutput, activationFunction).activations
        }
        return lastOutput
    }


    // Learning -- Backpropagation

    /** Produce output of NN, but also execute gradient descend function - backpropagation. */
    fun learn(input: List<Double>, target: List<Double>, learningRate: Double): LearnResult {
        return learn(
            listToColumnVector(input),
            listToColumnVector(target),
            learningRate
        )
    }

    /** Produce output of NN, but also execute gradient descend function - backpropagation. */
    fun learn(input: SimpleMatrix, target: SimpleMatrix, learningRate: Double): LearnResult {
        return backpropagation(input, target, learningRate,0)
    }

    /** Execute backpropagation algorithm with given data */
    private fun backpropagation(input: SimpleMatrix, target: SimpleMatrix, learningRate: Double, layerIndex: Int): LearnResult {
        // Executed after last layer, calculate Error and store output
        if (layerIndex == layers.size) {
            val error = input.minus(target)
            var totalError = 0.0
            for (row in 0 until input.numRows()) {
                totalError += (input.get(row, 0) - target.get(row, 0)).pow(2)
            }
            return LearnResult(input,error,input,totalError)
        }

        val layer = layers[layerIndex]
        // calculate output of this layer
        val result: NetworkLayer.ProcessResult = layer.process(input, activationFunction)
        // Recursive call to this function
        val nextLayer = backpropagation(result.activations, target, learningRate, layerIndex+1)

        // calculate zError from aError and activationFunction derivative
        val aError = nextLayer.error
        val zError = SimpleMatrix(aError.numRows(), 1)
        for (i in 0 until zError.numElements) {
            // Error = f'(z) * e
            zError.set(i,activationFunction.derivative(result.zValues.get(i)) * aError.get(i))
        }

        // calculate error for neurons of previous layer
        val prevLayerAError = layer.weights.transpose().mult(zError)

        // apply calculated gradient to each weight of this layer
        for (row in 0 until layer.weights.numRows()) {
            for (col in 0 until layer.weights.numCols()) {
                val weight = layer.weights.get(row, col)
                val error = input[col] * zError.get(row)
                layer.weights.set(row, col,
                    weight - (learningRate * error)
                )
            }
        }

        // apply gradient to bias
        for (i in 0 until layer.bias.numElements) {
            layer.bias.set(i, layer.bias.get(i) - (learningRate * zError.get(i)))
        }


        // return to previous layer
        return LearnResult(result.activations, prevLayerAError, nextLayer.finalOutput, nextLayer.totalError)
    }

    data class LearnResult(
        val output: SimpleMatrix,       // temporary, output of previous layer
        val error: SimpleMatrix,        // temporary, error of previous layer
        val finalOutput: SimpleMatrix,  // total, output of network
        val totalError: Double,         // total, error of whole network
    )

    companion object {
        /** Converts a column vector into a list
         * @param vector SimpleMatrix with n rows, and 1 column
         */
        fun columnVectorToList(vector: SimpleMatrix): List<Double> {
            val list = mutableListOf<Double>()
            for (i in 0 until vector.numRows()) {
                list.add(vector.get(i, 0))
            }
            return list
        }

        /** Converts a list into a column vector
         * @return SimpleMatrix with n rows, and 1 column
         */
        fun listToColumnVector(list: List<Double>): SimpleMatrix {
            val vector = SimpleMatrix(list.size, 1)
            for (i in list.indices) vector.set(i, 0, list[i])
            return vector
        }
    }
}