package ch.makery.application.util

import scalikejdbc._

trait Database {
  val derbyDriverClassname = "org.apache.derby.jdbc.EmbeddedDriver"

  val dbURL = "jdbc:derby:myDB;create=true;";
  // initialize JDBC driver & connection pool start up database
  Class.forName(derbyDriverClassname)

  ConnectionPool.singleton(dbURL, "me2", "mine2")

  // ad-hoc session provider on the REPL
  implicit val session = AutoSession


}

object Database extends Database {
  def setupDB() = {
    if (!hasDBInitialize) {
      //Tasks.initializeTable()
    }
  }

  def hasDBInitialize: Boolean = {

    DB getTable "Tasks" match {
      case Some(x) => true
      case None => false
    }

  }
}
