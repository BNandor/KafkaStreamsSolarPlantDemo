package producers

import tuples.SolarPlantAreas
import tuples.events.WeatherInformationEvent

object WeatherInformationEventGenerator {
  val minLumen = 0
  val maxLumen = 3000

  val minCelsius = -20
  val maxCelsius = 100

  val minHumidityGramPerCubicMeter = 1
  val maxHumidityGramPerCubicMeter = 5000

  def  randomValueBetween(a:Float,b:Float):Float = {
    a + scala.util.Random.nextFloat()*(b - a)
  }

  /**
   * Generates a random weather event for a random plant
   */
  def randomWeatherInfoEvent(timestamp:Double): WeatherInformationEvent = {
    WeatherInformationEvent(randomValueBetween(minLumen,maxLumen),
      randomValueBetween(minCelsius,maxCelsius),
      randomValueBetween(minHumidityGramPerCubicMeter,maxHumidityGramPerCubicMeter),
      SolarPlantAreas.randomSolarPlant,timestamp)
  }
}
