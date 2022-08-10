package com.johannes.neuralnetwork

import kotlinx.serialization.Serializable
import kotlin.math.max
import kotlin.math.sqrt
import kotlin.random.Random

@Serializable
class ActivationFunction(val type: Type) {
    fun function(input: Double): Double {
        when (type) {
            Type.ReLU -> return max(0.0, input)
        }
    }

    fun derivative(input: Double): Double {
        when (type) {
            Type.ReLU -> return if (input < 0) 0.0 else 1.0
        }
    }

    fun weightInitialization(inputCount: Int): Double {
        when (type) {
            Type.ReLU -> return Random.nextDouble() * sqrt(2.0 / inputCount)
        }
    }

    fun biasInitialization(): Double {
        when (type) {
            Type.ReLU -> return 0.0
        }
    }

    enum class Type {
        ReLU
    }
}