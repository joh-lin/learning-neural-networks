package com.johannes.convneuralnetwork

abstract class NetworkLayer {
    abstract val inputShape: Shape
    abstract val outputShape: Shape
    abstract fun propagate(input: Array<Array<DoubleArray>>): Array<Array<DoubleArray>>
    //abstract fun backpropagation(input: Array<Array<DoubleArray>>): BackpropagationResult


    // TODO: maybe don't use these data objects but just real results without encapsulation
    /*data class BackpropagationResult(
        val error: Unit = TODO()
    )

    data class PropagationResult(
        val output: Array<Array<DoubleArray>>
    )*/
}