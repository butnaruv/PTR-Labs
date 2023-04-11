package SecondLab.Week6

import slick.jdbc.PostgresProfile.api._
object Connection {
  val db = Database.forConfig("postgres")
}
