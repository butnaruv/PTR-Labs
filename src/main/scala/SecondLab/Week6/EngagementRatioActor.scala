package SecondLab.Week6

import akka.actor.{Actor, ActorRef, Props}

class EngagementRatioActor(aggregatorActor: ActorRef) extends Actor {
  var engagementRation = 0;

  override def receive: Receive = {
    case message: TweetAndID =>
      val selectedFavouritesCount = "\"favourites_count\":(.*?),".r.findFirstMatchIn(message.tweet).map(_.group(1)).fold("")(_.toString)
      val selectedRetweetCount = "\"retweet_count\":(.*?),".r.findFirstMatchIn(message.tweet).map(_.group(1)).fold("")(_.toString)
      val selectedFollowersCount = "\"followers_count\":(.*?),".r.findFirstMatchIn(message.tweet).map(_.group(1)).fold("")(_.toString)
      if (selectedFollowersCount.toInt != 0) {
        engagementRation = (selectedFavouritesCount.toInt + selectedRetweetCount.toInt) / selectedFollowersCount.toInt
      }

      //println("Engagement ratio " + engagementRation)
      //println(self.path.name)
      //println("Engagement Actor: " + message.id)
      aggregatorActor ! TweetAndID(engagementRation.toString, message.id)
  }
}

object EngagementRatioActor {
  def props(aggregatorActor: ActorRef): Props = Props(new EngagementRatioActor(aggregatorActor))
}
