package ch.makery.application.model
import ch.makery.application.util.Database
import ch.makery.application.util.DateUtil._
import scalafx.Includes._
import scalafx.beans.property.{ObjectProperty, StringProperty}
import scalikejdbc._

import java.time.LocalDate
import scala.util.Try




class Tasks(val todoS: String, val dueDateS: String) extends Database {
  def this() = this(null, null)


  var Task = new StringProperty(todoS)
  var DueDate = new StringProperty(dueDateS)
  var Progress = ObjectProperty[LocalDate](LocalDate.of(1999, 2, 21)) //StringProperty("some Street")
  var AddInfo = new StringProperty("some Info") //ObjectProperty[Int](1234)
  var taskstatus = new StringProperty("Completed")


  def _save_Data(): Try[Int] = {
    if (!(isExist)) {
      Try(DB autoCommit { implicit session =>
        sql"""
          insert into person (Task, DueDate,
            Progress, AddInfo, taskstatus) values
            (${Task.value}, ${DueDate.value}, ${Progress.value.asString},
              ${AddInfo.value},${taskstatus.value})
        """.update.apply()
      })
    } else {
      Try(DB autoCommit { implicit session =>
        sql"""
        update person
        set
        Task  = ${Task.value} ,
        DueDate   = ${DueDate.value},
        Progress    = ${Progress.value.asString},
        AddInfo = ${AddInfo.value},
        taskstatus       = ${taskstatus.value},
         where Task = ${Task.value} and
         DueDate = ${DueDate.value}
        """.update.apply()
      })
    }

  }

  def _delete_Data(): Try[Int] = {
    if (isExist) {
      Try(DB autoCommit { implicit session =>
        sql"""
        delete from task where
          Task = ${Task.value} and DueDate = ${DueDate.value}
        """.update.apply()
      })
    } else
      throw new Exception("Task not Exists in Database")
  }

  def isExist: Boolean = {
    DB readOnly { implicit session =>
      sql"""
        select * from task where
        DueDate = ${Task.value} and DueDate = ${DueDate.value}
      """.map(rs => rs.string("firstName")).single.apply()
    } match {
      case Some(x) => true
      case None => false
    }

  }
}

object Tasks extends Database {

   def apply(
             todoS: String,
             dueDateS: String,
             ProgressS: String,
             AddInfoS: String,
             taskstatusI: String
           ): Tasks = {

    new Tasks(todoS, dueDateS) {
      Progress.value = ProgressS.parseLocalDate
      AddInfo.value = AddInfoS
      taskstatus.value = taskstatusI
    }

   }

   def initializeTable() = {
    DB autoCommit { implicit session =>
      sql"""
      create table person (
      id int not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
      Tasks varchar(64),
      DueDate varchar(64),
      Progress varchar(64),
      AddInfo varchar(100),
      taskstatus varchar(100)
    )
    """.execute.apply()
    }
   }

   def getAllTasks: List[Tasks] = {
    DB readOnly { implicit session =>
      sql"select * from person".map(rs =>
        Tasks(rs.string("firstName"),
          rs.string("Progress"), rs.string("Due Date"),
          rs.string("Additional Info"), rs.string("Task Status"))).list.apply()
      }
    }




}