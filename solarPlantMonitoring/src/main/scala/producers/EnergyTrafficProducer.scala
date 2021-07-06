package producers

import java.util.Properties

import com.fasterxml.jackson.annotation.ObjectIdGenerators.UUIDGenerator
import config.DefaultConfig
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.kafka.common.serialization.StringSerializer
import java.util.UUID.randomUUID

/**
 * Produces a random energy traffic event stream
 */
object EnergyTrafficProducer  extends App {

  val config: Properties = {
    val p = DefaultConfig.config
    p.put("key.serializer", classOf[StringSerializer].getName)
    p.put("value.serializer", classOf[StringSerializer].getName)
    p
  }

  val producer1 = new KafkaProducer[String,String](config)
  val producerUUID = randomUUID().toString
  var currentTimestamp = 0
  while(true) {
    val randomEnergyTrafficEv = EnergyTrafficEventGenerator.randomEnergyTrafficEvent(currentTimestamp)
    val json = DefaultConfig.objectToJson.writeValueAsString(randomEnergyTrafficEv)
    producer1 send new ProducerRecord[String, String](DefaultConfig.energyTrafficStream, producerUUID, json)
    Thread.sleep(50)
    currentTimestamp+=1
  }
}



