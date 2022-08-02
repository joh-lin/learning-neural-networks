class ReLUFunction() : ActivationFunction {
    override fun process(input: Double): Double {
        if (input < 0) return 0.0
        return input
    }
}