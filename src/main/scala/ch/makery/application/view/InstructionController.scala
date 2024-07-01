package  ch.makery.application.view
import ch.makery.application.FinalApp
import scalafxml.core.macros.sfxml



@sfxml
class InstructionController{


  def getToNext(): Unit = {
    FinalApp.showPersonalDetails()
  }

  def getOne(): Unit = {
    FinalApp.showWelcomePage()
  }
}
