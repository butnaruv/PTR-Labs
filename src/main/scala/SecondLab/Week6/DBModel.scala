package SecondLab.Week6


case class TweetForTable(tweedId: Int, tweetName: String, engagementRatio:String , sentimentScore: String)

object DBModel {
  import slick.jdbc.PostgresProfile.api._
  class TweetTable(tag: Tag) extends Table[TweetForTable](tag, Some("tweets"), "Tweet"){
    def tweet_ID = column[Int]("tweet_id", O.PrimaryKey, O.AutoInc)

    def tweet_Name = column[String]("tweet_text")

    def engagement_Ratio = column[String]("engagement_ratio")

    def sentiment_Score = column[String]("sentiment_score")

    override def * = (tweet_ID, tweet_Name, engagement_Ratio, sentiment_Score) <> (TweetForTable.tupled, TweetForTable.unapply)
  }
  lazy val tweetTable = TableQuery[TweetTable]
}
