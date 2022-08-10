package com.johannes.convneuralnetwork

class MaxPoolingLayer(
    override val inputShape: Shape,
    val kernelSize: Int,
    val padding: Boolean = false
) : NetworkLayer() {
    val stride = kernelSize
    override val outputShape = Shape(
        if (padding) inputShape.width else inputShape.width / kernelSize,
        if (padding) inputShape.height else inputShape.height / kernelSize,
        inputShape.depth
    )


    override fun propagate(input: Array<Array<DoubleArray>>): Array<Array<DoubleArray>> {
        TODO("Not yet implemented")
    }
}