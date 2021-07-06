package config

import java.util.Properties

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import consumers.Consumer.args
import org.apache.kafka.streams.StreamsConfig

object DefaultConfig {
  val config: Properties = {
    val p = new Properties()
    p.put(StreamsConfig.APPLICATION_ID_CONFIG, "SolarPlantMonitoring")
    val bootstrapServers =  " localhost:39092 localhost:29092 localhost:19092"
    p.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
    p
  }
  val objectToJson = new ObjectMapper()
  objectToJson.registerModule(DefaultScalaModule)
  val jsonToObject = new JsonMapper()
  jsonToObject.registerModule(DefaultScalaModule)

  val energyTrafficStream:String = "energy-traffic-in"
  val weatherInfoStream:String = "weather-info-in"
  val plantFailureStream:String = "plant-failure-in"
}
