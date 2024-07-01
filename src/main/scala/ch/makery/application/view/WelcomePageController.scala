package ch.makery.application.view
import ch.makery.application.FinalApp
import scalafxml.core.macros.sfxml

@sfxml
class WelcomePageController() {

  def getStarted(): Unit = {
    FinalApp.showUserView()
  }

  def getIns(): Unit = {
    FinalApp.showInstruction()
  }

  def getNote(): Unit = {
    FinalApp.showPersonalDetails()
  }
}