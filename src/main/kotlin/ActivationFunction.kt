import kotlinx.serialization.Serializable

@Serializable
class ActivationFunction(val type: Type) {
    fun process(input: Double): Double {
        when (type) {
            Type.ReLU -> {
                return if (input < 0) 0.0
                else input
            }
        }
    }

    enum class Type {
        ReLU
    }
}