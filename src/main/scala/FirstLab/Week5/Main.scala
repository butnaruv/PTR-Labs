package FirstLab.Week5

import java.net.{HttpURLConnection, URL}
import scala.collection.mutable.ArrayBuffer

object Main extends App {

  {

    println("---Minimal Tasks---")
    MinimalTask1()
    MinimalTask2()

  }
  private var body = ""

  def MinimalTask1(): Unit = {
    val url = new URL("https://quotes.toscrape.com/")
    val connection = url.openConnection().asInstanceOf[HttpURLConnection]
    connection.setRequestMethod("GET")
    println(s"Response status code: ${connection.getResponseCode}")
    println("Response headers:")
    connection.getHeaderFields.forEach((key, value) => println(s"$key: $value"))
    body = scala.io.Source.fromInputStream(connection.getInputStream).mkString
    println(s"Response body: $body")
    connection.disconnect()
  }

  def MinimalTask2(): Unit = {

    val listOfMaps = ArrayBuffer[Map[String, String]]()
    val selectGeneralBlock = "<div class=\"quote\"[\\s\\S]*?<\\/div>".r
    val selectQuotes = "<span class=\"text\" itemprop=\"text\">(“.*)<\\/span>".r
    val selectAuthors = "itemprop=\"author\">(.*)<\\/small>".r
    val selectTags = "<a class=\"tag\".*?>(.*)<\\/a>".r

    val listOfQuotes = selectGeneralBlock.findAllIn(body).toList
    for(element <- listOfQuotes){
      val quote = selectQuotes.findFirstMatchIn(element).map(_.group(1)).fold("")(_.toString)
      val author = selectAuthors.findFirstMatchIn(element).map(_.group(1)).fold("")(_.toString)
      val tags = selectTags.findAllMatchIn(element).map(_.group(1)).mkString("; ")
      listOfMaps.addOne(Map("quote" -> quote, "author" -> author, "tags" -> tags))
    }
    for(map <- listOfMaps) println(map)
  }
}
