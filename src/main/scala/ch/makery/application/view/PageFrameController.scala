package ch.makery.application.view
import ch.makery.application.FinalApp
import scalafx.event.ActionEvent
import scalafxml.core.macros.sfxml

@sfxml
class PageFrameController(){
  def handleClose(): Unit = {
    System.exit(0)
  }

  def handleMenu(actionEvent: ActionEvent): Unit = {
    FinalApp.overviewController match {
      case Some(contrl) =>
        FinalApp.showWelcomePage()
      case None =>
    }
  }

  def handleDeleteInput(action: ActionEvent): Unit = {
  //you call the handle delete in userview
  FinalApp.overviewController match {
   case Some(contrl) =>
   contrl.handleRemoveTask(action)
   case None =>
  }
  }
}
