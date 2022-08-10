import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import org.ejml.simple.SimpleMatrix

fun main() {
    test1()
}

fun test1() {
    val array = arrayOf(intArrayOf(1, 2, 3))
    val array2 = array.map { it.copyOf() }

    array2[0][1] = 5

    array.forEach { it.forEach { println(it) } }
}

fun matrixTests() {
    val m1 = SimpleMatrix(arrayOf(
        doubleArrayOf(2.0, 4.0),
        doubleArrayOf(0.0, 9.0)))

    val m2 = SimpleMatrix(arrayOf(
        doubleArrayOf(0.0, 8.0),
        doubleArrayOf(9.0, 2.00)))

    println(m1.plus(m2))
}

fun serializationTest() {
    val myfood = listOf(Salad(), Burger())

    val module = SerializersModule {
        polymorphic(Food::class) {
            subclass(Pizza::class)
            subclass(Burger::class)
            subclass(Salad::class)
        }
    }

    val json = Json {
        serializersModule = module
    }

    val string = json.encodeToString(myfood)

    println(string)
}