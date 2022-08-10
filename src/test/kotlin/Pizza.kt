import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Pizza() : Food {
    override val awesomeness = 80
    override fun eat() {
        println("This Pizza is so tasty.")
    }
}