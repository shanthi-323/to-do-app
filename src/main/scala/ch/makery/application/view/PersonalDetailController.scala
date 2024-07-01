package  ch.makery.application.view
import ch.makery.application.FinalApp
import scalafxml.core.macros.sfxml



@sfxml
class PersonalDetailController{

  def getToNext2(): Unit = {
    FinalApp.showUserView()
  }

  def getThree(): Unit = {
    FinalApp.showWelcomePage()
  }
}
