package FirstLab.Week5

import play.api.libs.json.{Json}
import java.io._
import java.net.{HttpURLConnection, URL}
import scala.collection.mutable.ArrayBuffer


object Main extends App {
  {
    println("---Minimal Tasks---")
    MinimalTask1()
    MinimalTask2()
    MinimalTask3()
  }

  private var body = ""
  var globalListOfMaps = ArrayBuffer[Map[String, String]]()

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
    val selectGeneralBlock = "<div class=\"quote\"[\\s\\S]*?<\\/div>".r
    val selectQuotes = "<span class=\"text\" itemprop=\"text\">(“.*)<\\/span>".r
    val selectAuthors = "itemprop=\"author\">(.*)<\\/small>".r
    val selectTags = "<a class=\"tag\".*?>(.*)<\\/a>".r

    val listOfMaps = ArrayBuffer[Map[String, String]]()
    val listOfQuotes = selectGeneralBlock.findAllIn(body).toList

    for (element <- listOfQuotes) {
      val quote = selectQuotes.findFirstMatchIn(element).map(_.group(1)).fold("")(_.toString)
      val author = selectAuthors.findFirstMatchIn(element).map(_.group(1)).fold("")(_.toString)
      val tags = selectTags.findAllMatchIn(element).map(_.group(1)).mkString("; ")
      listOfMaps.addOne(Map("quote" -> quote, "author" -> author, "tags" -> tags))
    }
    globalListOfMaps = listOfMaps
    for (map <- listOfMaps) {
      println("Map: ")
      for (element <- map) println(element)
    }
  }

  private def MinimalTask3(): Unit = {
    val jsonFormat = Json.prettyPrint(Json.toJson(globalListOfMaps))
    val jsonFile = new File("quotes.json")
    val writer = new FileWriter(jsonFile)
    writer.write(jsonFormat.toString())
    writer.close()
  }
}
