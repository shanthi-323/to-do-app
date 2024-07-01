package ch.makery.application
import ch.makery.application.model.Tasks
import ch.makery.application.util.Database
import ch.makery.application.view.{TaskEditController, UserViewController, WelcomePageController}
import javafx.{scene => jfxs}
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.collections.ObservableBuffer
import scalafx.scene.Scene
import scalafx.stage.{Modality, Stage}
import scalafxml.core.{FXMLLoader, FXMLView, NoDependencyResolver}

object FinalApp extends JFXApp {
  Database.setupDB()

  val taskData = new ObservableBuffer[Tasks]()
  //taskData ++= Tasks.getAllTasks
  taskData += new Tasks("Clean", "Night ")
  taskData += new Tasks("Eat", "Morning")
  taskData += new Tasks("Homework", "Tomorrow")
  taskData += new Tasks("Dance Class", "Evening")


  // transform path of RootLayout.fxml to URI for resource location.
  private val rootResource = getClass.getResource("view/PageFrame.fxml")
  // initialize the loader object.
  private val loader = new FXMLLoader(rootResource, NoDependencyResolver)
  // Load root layout from fxml file.
  loader.load();
  // retrieve the root component BorderPane from the FXML
  private val roots = loader.getRoot[jfxs.layout.BorderPane]
  // initialize stage
  stage = new PrimaryStage {
    title = "FinalApp"
    scene = new Scene {
      stylesheets += getClass.getResource("view/DarkTheme.css").toString
      root = roots
    }
  }
  var overviewController: Option[UserViewController#Controller] = None

  //actions for display person overview window
  def showUserView() = {
    val resource = getClass.getResource("view/UserView.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    val control = loader.getController[UserViewController#Controller]()
    overviewController = Option(control)
    this.roots.setCenter(roots)
  }



  // actions for display person overview window
  def showWelcomePage() = {
    val resource = getClass.getResource("view/WelcomePage.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  def showTaskEdit(tasks: Tasks): Boolean = {
    val resource = getClass.getResourceAsStream("view/TaskEdit.fxml")
    val loader = new FXMLLoader(null, NoDependencyResolver)
    loader.load(resource);
    val roots2 = loader.getRoot[jfxs.Parent]
    val control = loader.getController[TaskEditController#Controller]

    val dialog = new Stage() {
      initModality(Modality.APPLICATION_MODAL)
      initOwner(stage)
      scene = new Scene {
        stylesheets += getClass.getResource("view/DarkTheme.css").toString
        root = roots2
      }
    }
    control.dialogStage = dialog
    control.tasks = tasks
    dialog.showAndWait()
    control.okClicked
  }

  def showInstruction() = {
    val resource = getClass.getResource("view/Instruction.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  def showPersonalDetails() = {
    val resource = getClass.getResource("view/PersonalDetails.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  //showUserView()
  showWelcomePage()
  //showInstruction()
  //showTaskEdit()
  //PersonalDetailController

}
