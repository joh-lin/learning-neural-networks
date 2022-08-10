import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Salad() : Food {
    override val awesomeness = 70
    override fun eat() {
        println("This Salad is probably very healthy.")
    }
}