package FirstLab.Week5

import java.net.{HttpURLConnection, URL}

object Main extends App {
  {
    println("---Minimal Tasks---")
    MinimalTask1()

  }

  def MinimalTask1(): Unit = {
    val url = new URL("https://quotes.toscrape.com/")
    val connection = url.openConnection().asInstanceOf[HttpURLConnection]
    connection.setRequestMethod("GET")

    println(s"Response status code: ${connection.getResponseCode}")
    println("Response headers:")
    connection.getHeaderFields.forEach((key, value) => println(s"$key: $value"))


    val body = scala.io.Source.fromInputStream(connection.getInputStream).mkString
    println(s"Response body: $body")


    connection.disconnect()

  }
}
