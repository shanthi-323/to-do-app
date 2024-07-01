package ch.makery.application.view


import ch.makery.application.model.Tasks
import ch.makery.application.util.DateUtil
import ch.makery.application.util.DateUtil.{DateFormater, StringFormater}
import scalafx.event.ActionEvent
import scalafx.scene.control.{Alert, TextField}
import scalafx.stage.Stage
import scalafxml.core.macros.sfxml


@sfxml
class TaskEditController(
                          private val TaskField: TextField,
                          private val DateField: TextField,
                          private val ProgressField: TextField,
                          private val InfoField: TextField,
                          private val StatusField: TextField

                        ) {
  var dialogStage: Stage = null //property
  //data model
  private var _task: Tasks = null //data field
  var okClicked = false

  def tasks  = _task//getter

  def tasks_= (x: Tasks) { //setter
    _task = x //change my data field

    TaskField.text = _task.Task.value
    DateField.text = _task.DueDate.value
    ProgressField.text = _task.Progress.value.asString
    ProgressField.setPromptText(DateUtil.DATE_PATTERN);
    InfoField.text = _task.AddInfo.value  //.toString
    StatusField.text = _task.taskstatus.value
    //birthdayField.text = _person.date.value.asString
    //birthdayField.setPromptText(DateUtil.DATE_PATTERN);
  }

  def handleInputDone(action: ActionEvent) {

    if (isInputValid()) {
      _task.Task.value = TaskField.text.value
      _task.DueDate.value = DateField.text.value
      //_task.Progress.value = ProgressField.text.value.parseLocalDate
      _task.AddInfo.value = InfoField.text()
      _task.taskstatus.value = StatusField.text()//StatusField.getText().toInt
      _task.Progress.value = ProgressField.text.value.parseLocalDate;

      okClicked = true;
      dialogStage.close()
    }
  }

  def handleCancelTask(action: ActionEvent) {
    dialogStage.close();
  }

  def nullChecking(x: String) = x == null || x.length == 0

  def isInputValid(): Boolean = {
    var errorMessage = ""

    if (nullChecking(TaskField.text.value))
      errorMessage += "No valid task!\n"
    if (nullChecking(DateField.text.value))
      errorMessage += "No valid time !\n"
    if (nullChecking(StatusField.text.value))
      errorMessage += "No valid progress!\n"
    if (nullChecking(InfoField.text.value))
      errorMessage += "No valid info!\n"

    if (errorMessage.length() == 0) {
      return true;
    } else {
      if (nullChecking(ProgressField.text.value))
        errorMessage += "No valid date entered!\n"
      else {
        if (!ProgressField.text.value.isValid) {
          errorMessage += s"No valid date entered. Use the format ${DateUtil.DATE_PATTERN}!\n";
        }
      }

      // Show the error message.
      val alert = new Alert(Alert.AlertType.Error) {
        initOwner(dialogStage)
        title = "Invalid Fields"
        headerText = "Please correct invalid fields"
        contentText = errorMessage
      }.showAndWait()

      return false;
    }
  }
}
