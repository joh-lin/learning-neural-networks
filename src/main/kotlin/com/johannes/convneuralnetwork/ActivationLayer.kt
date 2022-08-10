package com.johannes.convneuralnetwork

class ActivationLayer(
    override val inputShape: Shape
) : NetworkLayer() {
    override val outputShape = inputShape

    override fun propagate(input: Array<Array<DoubleArray>>): Array<Array<DoubleArray>> {
        TODO("Not yet implemented")
    }
}