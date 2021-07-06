package producers

import tuples.{PlantFailure, SolarPlant, SolarPlantAreas}
import tuples.events.{PlantFailureEvent, WeatherInformationEvent}

object PlantFailureEventGenerator {
  val failureList = PlantFailure.values.toList

  /**
   * Generates a random plant failure for a random solar plant
   */
  def randomPlantFailureEvent(timestamp:Double): PlantFailureEvent = {
    PlantFailureEvent(failureList(scala.util.Random.nextInt(failureList.size)).toString,SolarPlantAreas.randomSolarPlant,timestamp)
  }
 }
