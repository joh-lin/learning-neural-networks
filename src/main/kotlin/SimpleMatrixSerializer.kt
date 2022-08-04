import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.ejml.simple.SimpleMatrix

class SimpleMatrixSerializer : KSerializer<SimpleMatrix> {
    private val delegateSerializer = ListSerializer(ListSerializer(Double.serializer()))
    override val descriptor: SerialDescriptor =
        SerialDescriptor("SimpleMatrix", delegateSerializer.descriptor)

    override fun serialize(encoder: Encoder, value: SimpleMatrix) {
        val data = mutableListOf<MutableList<Double>>()
        for (row in 0 until value.numRows()) {
            val temp = mutableListOf<Double>()
            for (col in 0 until value.numCols()) {
                temp.add(value.get(row, col))
            }
            data.add(temp)
        }
        encoder.encodeSerializableValue(delegateSerializer, data)
    }

    override fun deserialize(decoder: Decoder): SimpleMatrix {
        val list = decoder.decodeSerializableValue(delegateSerializer)
        val matrix = SimpleMatrix(list.size, list.first().size)
        for (row in list.indices) {
            for (col in list[row].indices) {
                matrix.set(row, col, list[row][col])
            }
        }
        return matrix
    }
}