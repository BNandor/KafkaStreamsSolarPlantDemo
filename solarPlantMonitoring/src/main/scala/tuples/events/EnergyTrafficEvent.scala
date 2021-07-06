package tuples.events

import tuples.{SolarPlant, SolarPlantAreas}

/**
 * Encapsulates the production or consumption event of a solar plant at a particular time
 **/
case class EnergyTrafficEvent(kWatt:Double, produced:Boolean, plant:SolarPlant, timestamp:Double)


