package o1.adventure
import scala.io.StdIn.*

class Adventure:

  val title = "'Hudson & Rex - Bullet in the Water'"

  private val river = Area("river", "Where Niko got shot. There are witnesses on the river bank. They all said that they heard two gun shots.\n" +
    "Probably from the hill area.")
  private val riverBank = Area("riverbank", s"Four witnesses on the riverbank, Adler's sister was one of the witnesses.\n"+
    "The sister came forward and said that she heard only one gun shot from the hill.\nShe insisted that the veteran from the hill shot Niko.")
  private val hill = Area("hill", "Where the veteran live. Are the shots from the hill down?")
  private val officeP = Area("police office", "Decision zone.\nDo you have the criminal with you?")
  private val roomT = Area("teamroom", "Preparation room of the team, next to the river.")
  private val school = Area("school", "Hello Dean!")


  river.setNeighbors(Vector("riverbank" -> riverBank, "teamroom" -> roomT, "hill" -> hill, "policeoffice" -> officeP ))
  riverBank.setNeighbors(Vector("hill" -> hill, "river" -> river, "teamroom" -> roomT, "policeoffice" -> officeP ))
  hill.setNeighbors(Vector("riverbank" -> riverBank, "river" -> river, "policeoffice" -> officeP))
  officeP.setNeighbors(Vector("school" -> school, "riverbank" -> riverBank, "hill" -> hill, "river" -> river, "teamroom" -> roomT))
  roomT.setNeighbors(Vector("school" -> school,"river" -> river, "policeoffice" -> officeP))
  school.setNeighbors(Vector("teamroom" -> roomT, "policeoffice" -> officeP))

  private val bullet = Item("bullet", "We found the bullet! It was located at the river bank. There's Niko blood on it.\n" +
    "This is trully the bullet that kill Niko. Does it mean the river is the crime scene?\nBut the shoot direction on Niko body don't match! Also, where is the gun?")
  riverBank.addItem(bullet)

  private val blood = Item("blood", "Why do the room has dry blood mark?\nAdler tried to cover it and said that it's because rowers always have some crack on their hands. \n" +
    "Following the tiny blood mark, there is ... what??? A bullet hole on the wall? Does it fit with the bullet at the riverbank?")
  private val criminal1 = Item("boone", "Boone is a rowling team member, his eyes were nervously checking around while talking.")
  private val criminal2 = Item("cash", "Cash is a rowling team member, he doesn't say anything, just looks sad.")
  private val criminal3 = Item("damon", "Damon is a rowling team member, he was hiding something, a t-shirt with someone blood on it? \n" +
    "Is it Niko blood? What is this Damon?\n'It's not what you think!', Damon shout. 'I swear I didn't shoot Niko, I swear', Damon is shaking.")
  private val criminal4 = Item("niko", "Niko is a rowling team member, the one who was shot. \n" +
    "Surprisingly, the shot on his temper show that it wasn't a shot down from a far distance.\nInstead, it was from somewhere near and up.\n" +
    "Does this means the crime scene was not at the river?\n" +
    "Let's check the riverbank, where they heard the gun shots and the hill, too.")
  river.addItem(criminal4)

  private val criminal5 = Item("adler", "Adler is the leader of the rowling team, he seems confused, " +
    "he's calming his sister down while she was crying, and look at her eyes while she answered police's questions.\n" +
    "He tried to cover the blood on the floor, ... it seems suspicious. Should we capture him?")
  private val criminal6 = Item("coach", "Rowling team coach, he suggests that Niko has good relation ship with the rowling team. \n" +
    "Niko family were poor unlike other members' family but he has a schoolership to cover for all his expenses.\n" +
    "'Go talk to the Dean' he said, 'if you want further information.' \n" +
    "Quite suspicious that the coach changed the route to make it cross the hill that day, and claimed that changing route is normal. \n" +
    "He also think that the Veteran is the criminal.")
  roomT.addItem(blood)
  roomT.addItem(criminal1)
  roomT.addItem(criminal2)
  roomT.addItem(criminal3)
  roomT.addItem(criminal5)
  roomT.addItem(criminal6)

  private val criminal7 = Item("veteran", "The old veteran live on the hill. He's holding grudge with the rowling team. \n" +
    "His hand was always shaking and he keeps shouting 'Dame thieves, keep crossing my land'.\n" +
    "He seems to be a potential killer but his hands were shaking, he can't shoot the target with shaking hands.\n" +
    "His gun is not a match with the found bullet. Let's find the coach and the team to ask for more information.")
  hill.addItem(criminal7)

  private val criminal8 = Item("sister", s"Sister of Adler, she will do anything that her brother ask, she seems sad because Niko was her friend.\n"+
    "However, why are there gun powder on her clothes?")
  riverBank.addItem(criminal8)

  private val criminal9 = Item("dean", "Dean of the school. She said that Niko used to be a drug addict. He may be killed by the drug dealer.")
  private val paper1 = Item("document", "Look what I found, Damon family donated a large sum of money to the Shool.\n" +
    "And why does the Dean has some large amount of money deposit to her personal account? She also send money to buy something, hmm ... a gun? \n" )
  school.addItem(criminal9)
  school.addItem(paper1)

  /** The character that the player controls in the game. */
  val player = Player(river)

  /** The number of turns that have passed since the start of the game. */
  var turnCount = 0
  /** The maximum number of turns that this adventure game allows before time runs out. */
  val timeLimit = 40

  /** Determines if the adventure is complete, that is, if the player has won. */

  def isComplete = this.player.resultForNow && officeP.contains("dean")


  /** Determines whether the player has won, lost, or quit, thereby ending the game. */
  def isOver = this.isComplete || this.player.hasQuit || this.turnCount == this.timeLimit || this.player.countTurn < 1

  /** Returns a message that is to be displayed to the player at the beginning of the game. */

  def welcomeMessage =

    var detectiveName = ""
    detectiveName = readLine("May I ask what's your name, Detective?\n")
    s"Hello $detectiveName! Welcome to $title.\n\n" +
    s"We need your help, $detectiveName!\n" +
    "The Olympic rowling team was practising their morning routine in the river as usual.\n" +
    "They were singing a happy song and waved to the runners on the river bank.\n" +
    "But then two shots were heard.\n" +
    "And a team member has been shot dead by a bullet that go through his temper.\n\n" +
    "Please hurry, catch the criminal and bring him to the Police office or the criminal will escape!\n" +
      "You can always use 'help' anywhere you want!"

  def goodbyeMessage =
    if this.isComplete then
      "Finally, the police got the Dean and the whole story was revealed.\n" +
      "The Dean wants to get money for the school and secreatly for herself too, so she wants to kick the poor student out to let the rich with donation in.\n"+
      "She couldn't make Niko go himself, so she thought of a trick, that is to give Niko a 'fake' gun, promise him that the gun will never shoot while playing 'Russian rullete' game.\n"+
      "Niko believes in that lie and shot himself dead. Other members and the coach didn't know it was plan all along, so they made a fake crimescene with the help of the sister,\n"+
      "trying to keep the team in the Olympic and get rid of the annoying veteran at the same time.\n\n" +
      "Well done! You catch the criminal and win the game!"

    else if this.turnCount == this.timeLimit || this.player.countTurn < 1 then
      "Oh no! The criminal has covered him/her identity perdectly. " +
      "Too bad you couldn't find out who is the criminal. You have to stop here and let other police do the job. \nGame over!"
    else  // game over due to player quitting
      "Quitter! The killer is still living freely!"


  /** Plays a turn by executing the given in-game command, such as “go west”. Returns a textual
    * report of what happened, or an error message if the command was unknown. In the latter
    * case, no turns elapse. */
  def playTurn(command: String) =
    val action = Action(command)
    val outcomeReport = action.execute(this.player)
    if outcomeReport.isDefined then
      this.turnCount += 1
    outcomeReport.getOrElse(s"Unknown command: \"$command\".")

end Adventure

