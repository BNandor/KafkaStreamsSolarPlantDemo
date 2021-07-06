package tuples

/**
 * Generates random solar plant locations
 **/
object SolarPlantAreas {
  val countryCodes = Map("en"->(520,2),"ro"->(42,2), "de"->(295,2))
  val plantTypes = List("Auxin","Certainteed")
  def randomSolarPlant : SolarPlant = {
    val (countryCode,(county,areacode)) = countryCodes.toVector(scala.util.Random.nextInt(countryCodes.size))
    SolarPlant(countryCode,scala.util.Random.nextInt(county),plantTypes(scala.util.Random.nextInt(areacode) ))
  }
}
