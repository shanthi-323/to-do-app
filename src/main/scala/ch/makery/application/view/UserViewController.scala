package ch.makery.application.view
import ch.makery.application.FinalApp
import ch.makery.application.model.Tasks
import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, Label, TableColumn, TableView}
import scalafxml.core.macros.sfxml


@sfxml
class UserViewController(

                          private val TasksTable: TableView[Tasks],
                          private val TodoColumn: TableColumn[Tasks, String],
                          private val DueDateColumn: TableColumn[Tasks, String],
                          private val TaskLabel: Label,
                          private val DueDateLabel: Label,
                          private val ProgressLabel: Label,
                          private val AddInfoLabel: Label,
                          private val StatusLabel: Label,

                        ) {
  // initialize Table View display contents model
  TasksTable.items = FinalApp.taskData
  // initialize column's cell values
  TodoColumn.cellValueFactory = a => a.value.Task
  DueDateColumn.cellValueFactory = b => b.value.DueDate

  import ch.makery.application.util.DateUtil._

  private def showTaskDetails(person: Option[Tasks]) = {
    person match {
      case Some(tasks) =>
        // Fill the labels with info from the person object.
        TaskLabel.text <== tasks.Task
        DueDateLabel.text <== tasks.DueDate
        ProgressLabel.text.value = tasks.Progress.value.asString
        AddInfoLabel.text <== tasks.AddInfo //.value.toString
        StatusLabel.text <== tasks.taskstatus

      case None =>
        // Person is null, remove all the text.
        TaskLabel.text.unbind()
        DueDateLabel.text.unbind()
        ProgressLabel.text.unbind()
        AddInfoLabel.text.unbind()
        StatusLabel.text.unbind()
        TaskLabel.text = ""
        DueDateLabel.text = ""
        ProgressLabel.text = ""
        AddInfoLabel.text = ""
        StatusLabel.text = ""

    }
  }

  showTaskDetails(None)

  TasksTable.selectionModel.value.selectedItem.onChange(
    (_, _, z) => {
      showTaskDetails(Option(z))
    }
  )


  def handleRemoveTask(action: ActionEvent) = {
    val selectedIndex = TasksTable.selectionModel().selectedIndex.value
    if (selectedIndex >= 0) {
      //val tasks = TasksTable.items().remove(selectedIndex);
      FinalApp.taskData.remove(selectedIndex)
      //tasks.delete()
    } else {
      //Nothing selected.
      val alert = new Alert(AlertType.Warning) {
        initOwner(FinalApp.stage)
        title = "No Selection"
        headerText = "No Task Selected"
        contentText = "Please select a task in the table."
      }.showAndWait()
    }
  }

  def handleNewTask(action: ActionEvent) = {
    val person = new Tasks("", "")
    val okClicked = FinalApp.showTaskEdit(person);
    if (okClicked) {
      FinalApp.taskData += person
      person._save_Data()
    }
  }

  def handleEditTask(action: ActionEvent) = {
    val selectedPerson = TasksTable.selectionModel().selectedItem.value
    if (selectedPerson != null) {
      val okClicked = FinalApp.showTaskEdit(selectedPerson)

      if (okClicked) showTaskDetails(Some(selectedPerson))
      selectedPerson._save_Data()

    } else {
      // Nothing selected.
      val alert = new Alert(Alert.AlertType.Warning) {
        initOwner(FinalApp.stage)
        title = "No Selection"
        headerText = "No Person Selected"
        contentText = "Please select a person in the table."
      }.showAndWait()
    }

  }
}

