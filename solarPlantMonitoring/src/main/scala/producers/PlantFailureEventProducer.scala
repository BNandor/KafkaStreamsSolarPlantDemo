package producers

import java.util.Properties
import java.util.UUID.randomUUID

import config.DefaultConfig
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.kafka.common.serialization.StringSerializer

/**
 * Produces a random plant failure event stream
 */
object PlantFailureEventProducer extends App {

  val config: Properties = {
    val p = DefaultConfig.config
    p.put("key.serializer", classOf[StringSerializer].getName)
    p.put("value.serializer", classOf[StringSerializer].getName)
    p
  }

  val producer3 = new KafkaProducer[String,String](config)
  val producerUUID = randomUUID().toString
  var currentTimestamp = 0

  while(true) {
    val json = DefaultConfig.objectToJson.writeValueAsString(PlantFailureEventGenerator.randomPlantFailureEvent(currentTimestamp))
    producer3 send new ProducerRecord[String, String](DefaultConfig.plantFailureStream, producerUUID, json)
    Thread.sleep(10000)
    currentTimestamp+=1
  }
}
