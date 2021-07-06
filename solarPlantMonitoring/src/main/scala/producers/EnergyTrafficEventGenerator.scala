package producers

import tuples.SolarPlantAreas
import tuples.events.EnergyTrafficEvent

object EnergyTrafficEventGenerator {
  val minProduction =0
  val maxProduction =1000000

  def  randomEnergyProduction:Float = {
    minProduction + scala.util.Random.nextFloat()*(maxProduction - minProduction)
  }

  /**
   * Generates a random electricity production or consumption event
   */
  def randomEnergyTrafficEvent(timestamp:Double): EnergyTrafficEvent = {
    EnergyTrafficEvent(randomEnergyProduction,scala.util.Random.nextBoolean(), SolarPlantAreas.randomSolarPlant,timestamp)
  }
}