package SecondLab.Week5.Main

import akka.actor.{Actor, Props}

class EngagementRatioActor extends Actor{
  override def receive: Receive = {
    case message : String =>
      val selectedFavouritesCount = "\"favourites_count\":(.*?),".r.findFirstMatchIn(message).map(_.group(1)).fold("")(_.toString)
      val selectedRetweetCount = "\"retweet_count\":(.*?),".r.findFirstMatchIn(message).map(_.group(1)).fold("")(_.toString)
      val selectedFollowersCount = "\"followers_count\":(.*?),".r.findFirstMatchIn(message).map(_.group(1)).fold("")(_.toString)
      val engagementRation = (selectedFavouritesCount.toInt + selectedRetweetCount.toInt) / selectedFollowersCount.toInt
      println("Engagement ratio " + engagementRation)
  }
}
object EngagementRatioActor {
  def props(): Props = Props[EngagementRatioActor]
}
