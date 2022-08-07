import org.ejml.simple.SimpleMatrix
import kotlin.math.floor
import kotlin.random.Random
import kotlin.system.measureTimeMillis

class Benchmark {
    companion object {
        var reshapeTime: Double = 0.0
        var activationFunctionTime: Double = 0.0
        var multTime: Double = 0.0
    }
}

fun main() {
    val size = 1000
    val repetitions = 10_000

    // matrix
    val array = Array<DoubleArray?>(size) { null }

    for (row in 0 until size) {
        val innerArray = DoubleArray(size)
        for (col in 0 until size) {
            innerArray[col] = floor(Random.nextDouble(100.0))
        }
        array[row] = innerArray
    }

    val matrix = SimpleMatrix(array)

    val arrayI = DoubleArray(size)
    val colVector = SimpleMatrix(size, 1)

    for (row in 0 until size) {
        arrayI[row] = floor(Random.nextDouble(100.0))
        colVector.set(row, 0, arrayI[row])
    }

    println(matrix)
    println(colVector)

    println(measureTimeMillis {
        for (i in 0 until repetitions) {
            matrix.mult(colVector)
        }
    })


    println(measureTimeMillis {
        for (i in 0 until repetitions) {
            matrixMult(array, arrayI)
        }
    })
}

fun matrixMult(input: Array<DoubleArray?>, input2: DoubleArray): DoubleArray {
    val result = DoubleArray(input.size)
    for (row in result.indices) {
        for (col in result.indices) {
            result[row] += (input[row]?.get(col) ?: -1.0) * input2[col]
        }
    }
    return result
}