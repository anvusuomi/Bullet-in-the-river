package o1.adventure
import scala.collection.mutable.Map

class Area(var name: String, var description: String):

  private val neighbors = Map[String, Area]()
  private var items = Map[String, Item]()

  def neighbor(direction: String) = this.neighbors.get(direction)

  /** Adds an exit from this area to the given area. The neighboring area is reached by moving in
    * the specified direction from this area. */
  def setNeighbor(direction: String, neighbor: Area) =
    this.neighbors += direction -> neighbor

  /** Adds exits from this area to the given areas. Calling this method is equivalent to calling
    * the `setNeighbor` method on each of the given direction–area pairs.
    * @param exits  contains pairs consisting of a direction and the neighboring area in that direction
    * @see [[setNeighbor]] */
  def setNeighbors(exits: Vector[(String, Area)]) =
    this.neighbors ++= exits


  def fullDescription =
    val exitList = "\n\nWhere do you want to go? " + this.neighbors.keys.mkString(" ")

    val itemsList = "\n\nYou see here: " + this.items.keys.mkString(" ")

    if items.isEmpty then this.description + exitList
    else this.description + itemsList + exitList


  /** Returns a single-line description of the area for debugging purposes. */
  override def toString = this.name + ": " + this.description.replaceAll("\n", " ").take(150)

  def addItem(item: Item) = this.items += item.name -> item

  def contains(itemName: String) = this.items.contains(itemName)

  def removeItem(itemName: String) = this.items.remove(itemName)

end Area

