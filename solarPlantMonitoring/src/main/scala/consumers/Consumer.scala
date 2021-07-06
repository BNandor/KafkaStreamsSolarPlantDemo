package consumers

import java.time.Duration
import com.fasterxml.jackson.core.`type`.TypeReference
import config.DefaultConfig
import org.apache.kafka.streams.scala.{Serdes, StreamsBuilder}
import org.apache.kafka.streams.scala.kstream.{KStream, KTable}
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.kstream.{TimeWindows, Windowed}
import tuples.events.{EnergyTrafficEvent, WeatherInformationEvent}

/**
 * Consumes the event streams, creates multiple
 * aggregation output streams based on the input streams
 * */
object Consumer extends App {
  import org.apache.kafka.streams.scala.ImplicitConversions._
  import org.apache.kafka.streams.scala.Serdes._
  import com.goyeau.kafka.streams.circe.CirceSerdes._
  import io.circe.generic.auto._

  val builder = new StreamsBuilder()
  val energyTrafficEventStream: KStream[String, EnergyTrafficEvent] =
    builder.stream[String, String](DefaultConfig.energyTrafficStream).
    mapValues(json => DefaultConfig.jsonToObject.readValue(json,new TypeReference[EnergyTrafficEvent]{}))

  /**
   * Aggregates average energy production for every country
   * */
  val  energyProductionByCountry: KTable[Windowed[String], String] =
    energyTrafficEventStream
    .filter((_,event) =>event.produced )
    .groupBy((_, event) => event.plant.country).windowedBy(TimeWindows.of(Duration.ofSeconds(5)).advanceBy(Duration.ofMillis(5000)))
      .aggregate((0.0,0.0))((country:String,event:EnergyTrafficEvent,agg:(Double,Double))=>(agg._1+1,agg._2+event.kWatt)).
      mapValues((_,agg)=>(agg._2/agg._1).toString)
  energyProductionByCountry.toStream.map((ws,avg)=>(ws.key() +" "+ ws.window().start(),avg)).to("out-energy-prod-by-country")//(producedFromSerde(timeWindowedSerde,Double))

  /**
   * Aggregates average energy consumption for every country
   * */
  val  energyConsumptionByCountry: KTable[Windowed[String], String] =
    energyTrafficEventStream
      .filterNot((_,event) =>event.produced )
      .groupBy((_, event) => event.plant.country).windowedBy(TimeWindows.of(Duration.ofSeconds(5)).advanceBy(Duration.ofMillis(5000)))
      .aggregate((0.0,0.0))((country:String,event:EnergyTrafficEvent,agg:(Double,Double))=>(agg._1+1,agg._2+event.kWatt)).
      mapValues((_,agg)=>(agg._2/agg._1).toString)
  energyConsumptionByCountry.toStream.map((ws,avg)=>(ws.key() +" "+ ws.window().start(),avg)).to("out-energy-cons-by-country")//(producedFromSerde(timeWindowedSerde,Double))


  val weatherInfoEventStream: KStream[String, WeatherInformationEvent] =
    builder.stream[String, String](DefaultConfig.weatherInfoStream).
      mapValues(json => DefaultConfig.jsonToObject.readValue(json,new TypeReference[WeatherInformationEvent]{}))

  /**
   * Calculates maximum humidity for each plant company
   * */
  val maxHumidityByPlantCompany : KTable[Windowed[String], String] =
    weatherInfoEventStream
      .groupBy((_, event) => event.plant.company).windowedBy(TimeWindows.of(Duration.ofSeconds(5)).advanceBy(Duration.ofMillis(5000)))
      .aggregate(0.0)((company:String,event:WeatherInformationEvent,agg:Double)=>scala.math.max(agg,event.humidityGramPerCubicMeter)).
      mapValues((_,agg)=>agg.toString)
  maxHumidityByPlantCompany.toStream.map((ws, max)=>(ws.key() +" "+ ws.window().start(),max)).to("out-max-humidity-by-company")

  /**
   * Calculates minimum humidity for each plant company
   * */
  val minHumidityByPlantCompany : KTable[Windowed[String], String] =
    weatherInfoEventStream
      .groupBy((_, event) => event.plant.company).windowedBy(TimeWindows.of(Duration.ofSeconds(5)).advanceBy(Duration.ofMillis(5000)))
      .aggregate(scala.Double.PositiveInfinity)((company:String,event:WeatherInformationEvent,agg:Double)=>scala.math.min(agg,event.humidityGramPerCubicMeter)).
      mapValues((_,agg)=>agg.toString)
  minHumidityByPlantCompany.toStream.map((ws, min)=>(ws.key() +" "+ ws.window().start(),min)).to("out-min-humidity-by-company")

  /**
   * Calculates average humidity for each plant company
   * */
  val averageHumidityByPlantCompany : KTable[Windowed[String], String] =
    weatherInfoEventStream
      .groupBy((_, event) => event.plant.company).windowedBy(TimeWindows.of(Duration.ofSeconds(5)).advanceBy(Duration.ofMillis(5000)))
      .aggregate((0.0,0.0))((company:String,event:WeatherInformationEvent,agg:(Double,Double))=>(agg._1+1,agg._2+event.humidityGramPerCubicMeter)).
      mapValues((_,agg)=>(agg._2/agg._1).toString)
  averageHumidityByPlantCompany.toStream.map((ws, average)=>(ws.key() +" "+ ws.window().start(),average)).to("out-average-humidity-by-company")

  val streams: KafkaStreams = new KafkaStreams(builder.build(), DefaultConfig.config)

  // Always (and unconditionally) clean local state prior to starting the processing topology.
  // We opt for this unconditional call here because this will make it easier for you to play around with the example
  // when resetting the application for doing a re-run (via the Application Reset Tool,
  // http://docs.confluent.io/current/streams/developer-guide.html#application-reset-tool).
  //
  // The drawback of cleaning up local state prior is that your app must rebuilt its local state from scratch, which
  // will take time and will require reading all the state-relevant data from the Kafka cluster over the network.
  // Thus in a production scenario you typically do not want to clean up always as we do here but rather only when it
  // is truly needed, i.e., only under certain conditions (e.g., the presence of a command line flag for your app).
  // See `ApplicationResetExample.java` for a production-like example.
  streams.cleanUp()
  streams.start()
  sys.ShutdownHookThread {
    streams.close(Duration.ofSeconds(10))
  }

}
