package com.johannes.convneuralnetwork

import java.lang.RuntimeException
import kotlin.random.Random

class ConvolutionLayer (
    override val inputShape: Shape,  // amount of images/matrices fed to this network + dimensions
    val filterCount: Int,  // = output count
    val kernelSize: Int,  // width and height of kernels
    val padding: Boolean = false,  // false = valid padding | true = same padding
    val filters: Array<Array<Array<DoubleArray>>> = Array(filterCount) {  // list of filters,
        Array(inputShape.depth) {  // which in turn is a list of kernels for each input channel:
            Array(kernelSize) { DoubleArray(kernelSize) }  // matrix aka kernel
        }
    },
    val bias: Array<Array<DoubleArray>> = Array(inputShape.depth) {  // list of bias matrix for every output/filter
        Array(inputShape.width - if (padding) 0 else (kernelSize - 1)) { // width of final output
            DoubleArray(inputShape.height - if (padding) 0 else (kernelSize - 1)) }  // height of final output
    }
) : NetworkLayer() {
    val stride = 1
    val internalPadding = if (padding) 0 else (kernelSize - 1) / 2  // when no padding is used, this is the number
                                                                    // of pixels lost on each side of the input image
    override val outputShape: Shape = Shape(
        inputShape.width - internalPadding * 2,
        inputShape.height - internalPadding * 2,
        filterCount
    )

    init {
        // do some checks
        if (kernelSize % 2 == 0) throw RuntimeException("Kernel size has to be odd.")
        if (kernelSize < 1) throw RuntimeException("Kernel size has to be at least 1.")

        // randomly initialize kernels
        for (filterIndex in 0 until filterCount) {
            for (inputIndex in 0 until inputShape.depth) {
                for (x in 0 until kernelSize) {
                    for (y in 0 until kernelSize) {
                        filters[filterIndex][inputIndex][x][y] = Random.nextDouble()
                    }
                }
            }
        }
    }

    override fun propagate(input: Array<Array<DoubleArray>>): Array<Array<DoubleArray>> {
        println("""
            Internal Padding: $internalPadding
            Input Shape: $inputShape
            Output Shape: $outputShape
        """.trimIndent())

        val output = Array(outputShape.depth) {
            Array(outputShape.width) { DoubleArray(outputShape.height) } } // deep copy of bias array

        for (filterIndex in filters.indices) {  // for every filter ( so every output )
            val outputImage = output[filterIndex]
            println(outputImage.size)

            for (inputIndex in input.indices) {  // this now happens for every kernel/weight-matrix
                val kernel = filters[filterIndex][inputIndex]
                val inputImage = input[inputIndex]

                for (posX in internalPadding until inputShape.width - internalPadding) {
                    for (posY in internalPadding until inputShape.height - internalPadding) {
                        // this happens for every pixel in the input * filters

                        // convolution step
                        for (offsetX in 0 until kernelSize) {
                            for (offsetY in 0 until kernelSize) {
                                // iterate over kernel
                                val effectiveX = offsetX + posX - (kernelSize - 1) / 2
                                val effectiveY = offsetY + posY - (kernelSize - 1) / 2

                                // add weight * input to output
                                outputImage[posX-(kernelSize-1)/2][posY-(kernelSize-1)/2] +=
                                        if (effectiveX < 0 || effectiveX >= outputShape.width ||
                                            effectiveY < 0 || effectiveY >= outputShape.height) 0.0 // out of bounds
                                        else kernel[offsetX][offsetY] * inputImage[effectiveX][effectiveY]
                            }
                        }
                    }
                }
            }
        }

        return output
    }
}