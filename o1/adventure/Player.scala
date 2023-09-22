package o1.adventure
import scala.collection.mutable.Map
import scala.io.StdIn.*

class Player(startingArea: Area):

  private var currentLocation = startingArea        // gatherer: changes in relation to the previous location
  private var quitCommandGiven = false              // one-way flag

  private var items = Map[String, Item]()

  def hasQuit = this.quitCommandGiven

  def location = this.currentLocation

  def go(direction: String) =
    val destination = this.location.neighbor(direction)
    this.currentLocation = destination.getOrElse(this.currentLocation)
    if destination.isDefined then "You go to " + direction + "." else "You can't go to " + direction + "."

  def rest() =
    "You rest for a while. Better get a move on, though."

  def quit() =
    this.quitCommandGiven = true
    ""

  override def toString = "Now at: " + this.location.name


  def drop(itemName: String) =
    if this.has(itemName) then
      this.location.addItem(this.items.remove(itemName).get)
      "You drop " + itemName + "."
    else "You don't have that!."

  def investigate(itemName: String) =
    if this.has(itemName) then "You look closely at " + itemName + ".\n" + items(itemName). description
    else "If you want to investigate something, you need to get it first."

  def get(itemName: String) =
    if this.currentLocation.contains(itemName) then
      items += itemName -> this.currentLocation.removeItem(itemName).get
      "You pick up " + itemName + "."
    else "There is no " + itemName + " here to pick up."

  def has(itemName: String) = this.items.contains(itemName)

  def inventory =
    if items.keys.isEmpty then "You are empty-handed."
    else "You are carrying: \n" + items.keys.mkString("\n")

  def help =
    "+ To win the game you have to finish two task: bring the criminal to the police office and drop him/her there, and give the right 'answer'.\n"+
      "+ Type 'go' plus the name of the place to that area. Ex: 'go teamroom'.\n"+
      "+ Type 'get' plus the name of the thing or person you want to investigate. Ex: 'get coach'.\n"+
      "+ Type 'investigate' plus name of the thing or person you already get. Ex: 'investigate coach'.\n"+
      "+ Type 'drop' plus name of the thing or person you already get to drop them at the place you are in. Ex: 'drop coach'.\n"+
      "+ Type 'inventory' to know what have you got with you.\n"

  var countTurn = 5
  var resultForNow = false
  def mudererIs =
    if countTurn > 0 then
      var shooter = readLine("Who's actually shoot Niko? ")
      shooter.takeWhile( _ != ' ' ) match
          case "coach" => println(s"The $shooter wasn't there when Niko was murdered.")
          case "boone" => println(s"$shooter's hiding something but he wasn't the muderer.")
          case "damon" => println(s"$shooter's suspicious to hide Niko bloofy t-shirt but no, he didn't shoot Niko.")
          case "adler" => println("Not him, but ...")
          case "cash"  => println("Not him ...")
          case "sister" => println(s"$shooter body has gun powder, she definately has shoot something recently, but not Niko.")
          case "veteran" => println(s"$shooter hand was shaking, he can't shoot.")
          case "dean" => println(s"$shooter wasn't there when Niko was murdered.")
          case "niko" => println("Niko really shot himself huh ...")
          case other => println("Remember to type in the right format. Ex: 'coach'.")

      var criminal = readLine("Who is the real criminal? ")
      criminal.takeWhile( _ != ' ' ) match
          case "dean" => if !this.location.contains("dean") then println("Dean, you're under arrested! Wait, where is she? You didn't bring her to the police office?") else println("Dean, you're under arrested!")
          case other => println(s"Oh no ... you are wrong! $criminal is not the criminal.")

      var crimeScene = readLine("Where is the crime scene? ")
      crimeScene.takeWhile( _ != ' ' ) match
          case "teamroom" => println("This explain the bullet hold on the wall and the blood mark.")
          case other => println(s"Oh no ... you are wrong! $crimeScene is not the crime scene")

      resultForNow = criminal == "dean" && crimeScene == "teamroom" && shooter == "niko"

    if resultForNow then println("Your guesses are right! Give the Dean to the police!")
    else countTurn -= 1
         if countTurn > 0 then s"You have $countTurn turns left."
         else "You have no turn left!"


end Player

