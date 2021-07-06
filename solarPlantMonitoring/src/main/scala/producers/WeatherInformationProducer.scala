package producers

import java.util.Properties
import java.util.UUID.randomUUID

import config.DefaultConfig
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.kafka.common.serialization.StringSerializer

/**
 * Produces a random weather event stream
 */
object WeatherInformationProducer extends  App {

  val config: Properties = {
    val p = DefaultConfig.config
    p.put("key.serializer", classOf[StringSerializer].getName)
    p.put("value.serializer", classOf[StringSerializer].getName)
    p
  }

  val producer2 = new KafkaProducer[String,String](config)
  val producerUUID = randomUUID().toString
  var currentTimestamp = 0

  while(true) {
    val json = DefaultConfig.objectToJson.writeValueAsString(WeatherInformationEventGenerator.randomWeatherInfoEvent(currentTimestamp))
    producer2 send new ProducerRecord[String, String](DefaultConfig.weatherInfoStream, producerUUID, json)
    Thread.sleep(50)
    currentTimestamp+=1
  }
}
