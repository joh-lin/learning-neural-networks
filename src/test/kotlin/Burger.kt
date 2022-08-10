import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Burger() : Food {
    override val awesomeness = 90
    override fun eat() {
        println("This Burger is mad tasty!")
    }
}