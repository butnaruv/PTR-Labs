# FAF.PTR16.1 -- Project 0
> **Performed by:** Butnaru Valeria, group FAF-201
> **Verified by:** asist. univ. Alexandru Osadcenco

## P0W1

**Minimal Task** -- Write a script that would print the message “Hello PTR” on the screen

```scala
def hello(): String = "Hello, PTR!"
```

Function _hello()_ does not take any parameter, and return a String with value "Hello, PTR!"

**Bonus Task** -- Create a unit test for your project.

```scala
  class HelloWorldTest extends AnyFunSuite {
    test("HelloPTR.hello returns the correct string") {
      assert(FirstLab.Week1.Main.hello() == "Hello, PTR!")
    }
  }
```

In order to create a unit test, I created a class named _HelloWorldTest_ which extends AnyFunSuite, a suite for testing in scala. It's objective is to verify if the string from the first task correspond to "Hello, PTR!" string. In assert precondition, we acceess the script and check if it is equal with the expected result. 


## P0W2

**Minimal Task 1** -- Write a function that determines whether an input integer is prime

```scala
  def isPrime(number: Int): Unit = {
    var isPrime = true
    if (number >= 1) 
    {
      for (i <- 2 to sqrt(number).toInt) {
        if (number % i == 0) isPrime = false;
      }
    }
    println(isPrime)
  }
```

&ensp;&ensp;&ensp; A number is prime if it has only two factors: 1 and itself. Thus, we should test if our number **n** is divisible with any number, starting with 2. According to the algorithm, if we test it until $\sqrt{n}$ and there is not any factor find, then the number is prime. 

&ensp;&ensp;&ensp; First, I assigned a boolean variable equals to true. Second, I entered a case in an if-else statement. If the number is greater or equal to two, the function check for factors, in a for loop.  If the number is less than 2, then it is prime and the value of isPrime does not need to be changed. 

**Minimal Task 2** -- Write a function to calculate the sum of unique elements in a list.

```scala
  def uniqueSum(myList: Array[Int]): Unit = {
    var sum = 0
    val newList = myList.to(collection.mutable.Set)
    for (s <- newList) sum = sum + s
    println(sum)
  }
```

&ensp;&ensp;&ensp; In order to compute the sum of unique elements, the should be converted in a set. Thus, we "delete" repetitive elements. Then, using a for loop, iterate the set and add each element to the sum.

**Minimal Task 3** -- Write a function that would rotate a list n places to the left.

```scala
  def rotateLeft(myList: Array[Int], n: Int): Unit = {
    val newList = new Array[Int](myList.length)
    var newIndex = 0
    for (i <- 0 until myList.length) {
      newIndex = ((i - n) + myList.length) % myList.length
      newList(newIndex) = myList(i)
    }
    for (i <- newList) {
      print(i + " ")
    }
  }
```

&ensp;&ensp;&ensp; Rotating elements from a list to the left with one position, for example, means that the element from the index 0 become the last element, the element from the inde 1 becomes element from the index 0, and so on. In order to rotate, we need a new list to store newly aranged values. Now, its the time to play with indexes. The newIndex will be the index decreased by n, where n is the value we want to rotate our list. 

 &ensp;&ensp;&ensp;When we want to rotate elements from the index less than n, here is a problem, because the newIndex is negative. To solve the problem we need to implement the mathematica opperation - module. For this, we add to the value of newIndex the number equals to the legth of the list, and then we compute the modulus of the newIndex and the length of the list.

 **Main Task 1** -- Write a function that eliminates consecutive duplicates in a list.

```scala
  def removeConsecutiveDuplicates(myList: Array[Int]): Unit = {
    print(myList(0) + " ")
    for (i <- 1 until myList.length) {
      if (myList(i) != myList(i - 1)) {
        print(myList(i) + " ")
      }
    }
    println()
  }
```

&ensp;&ensp;&ensp;  Consecutive duplicates in a list are the adjacent elements with the same value. In order to eliminate them, the function iterate the list and verify if the element at the current position has not the same value as its precedent. If it is the case, the element is printed.

 **Main Task 2** -- Create a pair of functions to encode and decode strings using the Caesar cipher.

```scala
  def encode(input: String, key: Int): Unit = {
    for (e <- input) {
      print(((e.toInt + key - 97) % 26 + 97).toChar)
    }
    println()
  }
```

&ensp;&ensp;&ensp; Caesar cipher is a permutation cipher, it means that we need to permutate the alphabet with n units, value stored in constant key. For this, the function uses ascii code to transform the alphabet in numbers in order to permutate the alphabet. Using a for loop the plain text is iterated, each letter being firstly converted in int value, secondly being permuted by adding key value to integer value. Because we want to have only letters and to avoid to have negative values, we extract 97 (the value of 'a': now 'a' correspond to 1, 'b' to 2 and so on) , compute modulus 26 because we have 26 letters in alphabet, and adding back + 97, to get the ascii code of the permuted letter. 

```scala
  def decode(input: String, key: Int): Unit = {
    for (e <- input) {
      print(((e.toInt - key - 97) % 26 + 97).toChar)
    }
    println()
  }
```

&ensp;&ensp;&ensp; Decode function has the same idea, the difference is that the permutation is made to the left, so from the ascii value the key value is extracted, not added.

 **Main Task 3** -- Write a function that, given an array of strings, would group the anagrams
together.

```scala
  def groupAnagrams(list: Array[String]): Unit = {
    var map: Map[String, Array[String]] = Map.empty[String, Array[String]]

    for (i <- 0 until list.length) {
      var temp = Array[String]()
      for (j <- 0 until list.length) {
        if (list(i).sorted == list(j).sorted) {
          temp = temp ++ Array(list(j))
        }
      }
      map += (temp(0).sorted -> temp)
    }
    map.foreach { case (key, values) =>
      println(s"$key -> ${values.mkString(", ")}")
    }
  }
```

&ensp;&ensp;&ensp; First, the function declares a map in which the key is a string, and the value is an array of strings - all elements wich have the same letters as the key. The function iterates the list and create an array temporal variable to store all the elements which contains the same letter. For this, we'll have another for loop, and we'll verify if the sorted string of the outer loop is the same as the sorted string of the inner loop. If it is the case, temp variable is populated with the value. At the end of each outer iteration, the temp is stored in map, then it is reinitialised.

 **Bonus Task 1** -- Write a function to calculate the prime factorization of an integer.

```scala
  def factorize(number: Int): Unit = {
    var list = Array[Int]()
    var n = number
    for (i <- 2 to n) {
      while (n % i == 0) {
        list = list :+ i
        n = n / i
      }
    }
    for (e <- list) print(e + " ")
  }
   
```

&ensp;&ensp;&ensp; The prime factorization is the process of decomposing an integer in prime numbers. For this, in the function is initialise an array which will store the factors and a variable n which stores the number to be factorised. Starting with 2, we decompose the number until the modulus is different from 0, if it is different we decompose it with 3, then with 4 and so on. Then, the list of factors is displayed.

 **Bonus Task 2** -- Write a function to convert arabic numbers to roman numerals.

```scala
  def toRoman(input: Int): Unit = {
    val tuples = Map(
      1000 -> "M",
      900 -> "CM",
      500 -> "D",
      400 -> "CD",
      100 -> "C",
      90 -> "XC",
      50 -> "L",
      40 -> "XL",
      10 -> "X",
      9 -> "IX",
      5 -> "V",
      4 -> "IV",
      1 -> "I"
    )
    var result = ""
    var num = input
    val sortedKeys = tuples.keys.toList.sorted.reverse
    for (x <- sortedKeys) {
      while (num >= x) {
        result += tuples(x)
        num = num - x
      }
    }
    println(result)
  }
```

&ensp;&ensp;&ensp;  Firstly, the function initialise a map in which each specific roman number correspond to a key - an arabic value. The function initialise also a result variable and an integer variable to store the input. The second step consist in iteration of the sorted descending array of keys and while the number is bigger than the key, we'll add the key to the result, and the number is decreasing by the key value. We repeat this until we iterate all the keys and the value of number becomes 0.

## P0W3

 **Minimal Task 1** -- Create an actor that prints on the screen any message it receives.


```scala
class MyFirstActor extends Actor {
  def receive = {
    case message: Any =>
      println("I received the message: " + message)
  }
}
```

&ensp;&ensp;&ensp; The class MyFirstActor is the class which implements the behavior of the actor. If it receives any type of message, it will print it.

```scala
object MyFirstActor {
  def props: Props = Props[MyFirstActor]
}
```
&ensp;&ensp;&ensp; In this piece of code is represented a companion object for
MyFirstActor class. The props method in this object returns an instance of the Props class for the MyFirstActor class. So, this code block is defining a method that returns a Props instance for MyFirstActor which can be used to create an instance of that actor.

```scala
  def MinimalTask1(): Unit = {
    val system = ActorSystem("PrinterSystem")
    val printer = system.actorOf(MyFirstActor.props, "printer")
    printer ! "Hello!"
    printer ! 445
  }
```

&ensp;&ensp;&ensp; This block of code creates an Akka actor system and an instance of an actor called _printer_ using the MyFirstActor.props method, and sends two messages to the printer actor. The ActorSystem is a container for actors and provides facilities for creating, configuring, and managing actors. The last two commands send to _printer_ a string message and an integer message, and the _printer_ will display them.

 **Minimal Task 2** -- Create an actor which receives numbers and with each request prints out the current average.


```scala
class ComputeAverageActor extends Actor{
  var sum : Double= 0
  var average: Double = 0
  def receive = {
    case message: Int =>
      sum = average + message
      average = sum / 2
      println("Current average is : " + average)
  }
}
```

&ensp;&ensp;&ensp; In this example, the ComputeAverageActor class has two instance variables sum and average of type Double. These variables will be used to keep track of the sum and average of numbers that are received by this actor.

The receive method of this actor is defined using a pattern matching expression. It matches any incoming message of type Int. When such a message is received, it updates the sum instance variable by adding the received integer value to the previous sum. It then updates the average instance variable by dividing the new sum by 2, as the class is designed to only calculate the average of two numbers. Finally, it prints the current average to the console.


 **Main Task** -- Create an actor which maintains a simple FIFO queue. You should write helper
functions to create an API for the user, which hides how the queue is implemented

```scala
class QueueActor extends Actor {
  var queue = ArrayBuffer[Int]()

  override def receive: Receive = {
    case message: Int =>
      queue = queue :+ message
      println("ok ")
      println(queue)
    case "pop" =>
      if (queue.isEmpty) {
        println("No elements in queue")
      }
      else {
        println(queue(0))
        queue.remove(0)
        println(queue)
      }
  }
}
```

&ensp;&ensp;&ensp; In this example, the QueueActor class has an instance variable queue of type ArrayBuffer[Int]. This variable will be used to keep track of the elements in the queue.

The receive method of this actor is defined using a pattern matching expression. It matches two types of incoming messages:

+ If the incoming message is of type Int, it adds the received integer value to the queue instance variable using the :+ method of ArrayBuffer. It then prints "ok" and the current state of the queue to the console.

+ If the incoming message is the string "pop", it checks if the queue is empty. If it is, it prints "No elements in queue" to the console. Otherwise, it prints the first element of the queue to the console using the apply method of ArrayBuffer. It then removes the first element of the queue using the remove method and prints the updated state of the queue to the console.

&ensp;&ensp;&ensp;In order to create an API for the user, the aplication which implement the queue has 3 functions: _new_queue()_, _push(actor: ActorRef, number: Int)_ and _pop(actor: ActorRef)_.

```scala
  def new_queue(): ActorRef = {
    val system = ActorSystem("NewQueueActor")
    return system.actorOf(QueueActor.props, "queueActor")
  }
```
&ensp;&ensp;&ensp; This function creates a new instance of the _QueueActor_ actor and obtain an ActorRef to that actor for sending messages.

```scala
  def push(actor: ActorRef, number: Int): Unit = {
    actor ! number
  }
```
&ensp;&ensp;&ensp; This function send the _number_ message to the actor which has the refererece _actor: ActorRef_. It will then add the _number_ to the queue.

```scala
  def pop(actor: ActorRef): Unit = {
    actor ! "pop"
  }
```

&ensp;&ensp;&ensp; _pop(actor: ActorRef)_ function send a "pop" message to the actor of reference _actor: ActorRef_ . And the actor then will diplay the first element from its queue.

## P0W4

**Minimal Task** -- Create a supervised pool of identical worker actors. The number of actors
is static, given at initialization. Workers should be individually addressable. Worker actors
should echo any message they receive. If an actor dies (by receiving a “kill” message), it should
be restarted by the supervisor

```scala
class WorkerActor extends Actor {
  override def receive: Receive = {
    case "kill" => {
      throw new Exception("Something went wrong!")
    }
    case message: String => println("I received " + message)
  }
}

```

&ensp;&ensp;&ensp; This code defines an actor class _WorkerActor_ which override the function _receive()_. It works based on two cases:
  + if the message is equal to string "kill", it will throw an exception and the superviser will manage it.
  + if the message is any other string, the actor will print the message.

  &ensp;&ensp;&ensp; All the worker actors will be created by a superviser. In order to determine their creation, in the function receive there is implemented a case class _CreateWorker()_. 

```scala
  case CreateWorker =>
    for (i <- 0 until numberOfActors) {      
      listOfActors += context.actorOf(WorkerActor.props)
      context.actorOf(WorkerActor.props)
      }
    println(s"${listOfActors.length} of actors are created")
```

&ensp;&ensp;&ensp; This case class implements a for loop, in which one by one an worker actor is created and added to a list.

```scala
  case SendMessage(message, index) => {
    if (index < listOfActors.length) {
      println(s"Message sent to actor $index")
      listOfActors(index) ! message
    }
    else println(s"Actor $index does not exist")
  }
```

&ensp;&ensp;&ensp; Another case class of the superviser is _SendMessage(message: String, index:Int)_, which takes as parameters the message to be sent to the worker and the index of that worker in the list. In case the actor does not exists, the message can not be sent, so a message of absence of the actor will be displayed.  

```scala
  case SendKill(index) => 
    println(s"Kill message sent to actor $index")
    listOfActors(index) ! "kill"
```

&ensp;&ensp;&ensp; The last case class in the receive function is _SendKill(index: int)_ which takes as parameter the index of the actor in the list. When the superviser receive such a message, it should send a strin "kill". This will condition the worker actor to throw the exception. Here the managing actor implementation goes.

```scala
  override val supervisorStrategy = OneForOneStrategy() {
    case _: Exception => Restart
  }
```

&ensp;&ensp;&ensp; In this code the superviser establish the supervisorStrategy: _OneForOneStrategy()_  which means that if a child actor throws an exception, only that child actor will be affected, and the other child actors will continue to run. In our case, the worker with "problems" will be restarted. 

**Main Task** -- Create a supervised processing line to clean messy strings. 
+ The first worker in
the line would split the string by any white spaces (similar to Python’s str.split method).
+ The second actor will lowercase all words and swap all m’s and n’s (you nomster!).
+ The third actor will join back the sentence with one space between words (similar to Python’s str.join
method). 

Each worker will receive as input the previous actor’s output, the last actor printing the result on screen. If any of the workers die because it encounters an error, the whole
processing line needs to be restarted.

```scala
class SplitMessageActor extends Actor {
  var string = ""
  var listOfWords = new ArrayBuffer[String]()
  println("First Actor is here!")

  override def receive: Receive = {
    case "" =>
      sender() ! restartMe()
      try throw new Exception("Something went wrong!")
    case message: String =>
      println("1. Am primit mesajul: " + message)
      val newMessage = message.trim.replaceAll(" +", " ") + " "
      for (c <- newMessage) {
        if (c != ' ') {
          string += c
        }
        else if (c == ' ') {
          listOfWords += string
          string = ""
        }
      }
      println("1. Am returnat mesajul: " + listOfWords)
      sender ! listOfWords
      Thread.sleep(1000)
      listOfWords.clear()
  }
}
```

&ensp;&ensp;&ensp; _SplitMessageActor()_ is the actor responsible for splitting the message in words. The function receive has 2 cases:
 + If the message is empty, the actor throws an exception
 + If the message is any other string, it will be processed. In a for loop, each letter is iterated, and added to a temporal variable which forms the word. When the " " is reached, the value of the temporal variable is stored in an ArrayBuffer, and the variable is reinitialised. At the end, the message is sent to the _sender()_- the superviser of workers. It will forward the message to the next worker.

 ```scala
class LowercaseMessageActor extends Actor {
  var listOfLowercaseWords = ArrayBuffer[String]()
  var newWord = ""

  println("Second Actor is here!")

  override def receive: Receive = {
    case message: ArrayBuffer[String] =>
      println("2. Am primit mesajul: " + message)
      for (word <- message) {
        for (char <- word.toLowerCase()) {
          if (char == 'm') newWord += 'n'
          else if (char == 'n') newWord += 'm'
          else newWord += char
        }
        listOfLowercaseWords += newWord
        newWord = ""
      }
      println("2. Am returnat mesajul " + listOfLowercaseWords)
      sender ! listOfLowercaseWords
      Thread.sleep(1000)
      listOfLowercaseWords.clear()
  }
}
```

&ensp;&ensp;&ensp; _LowercaseActor_ is responsible for swaping m's and n's and for tranform all letters in lowercase. For swaping, each word is iterated char by char, and if an m is find, it is replaced by an n and vice versa. The word obtained is stored in an ArrayBuffer and it is sent as message to the superviser, which in turn forward it to the next worker.

```scala
class JoinWordsActor extends Actor {
  var result = ""

  println("Third Actor is here!")

  override def receive: Receive = {
    case message: ArrayBuffer[String] =>
      println("3. Am primit mesajul: " + message)

      for (word <- message) {
        if (word != message.last) result = result + word + " "
        else result = result + word
      }
      println("3. Am returnat mesajul " + result)
      Thread.sleep(1000)
      result = ""
  }
}
```

&ensp;&ensp;&ensp; _JoinWords_ actor is responsible for concatenating all words in a sentence and for diplaying it. It iterates the buffer received and concatenate each element,followed by a space, to a string. If the element is last in the buffer, then  it will not be followed by space. In the end the result is returned.

&ensp;&ensp;&ensp;The behavior of the superviser has nothing special. It just create one actor of each type and is responsible for the order in which actors process the string. The specific part is the error handling of any child. For this, a _supervisionStrategy_ is defined.

```scala
  override val supervisorStrategy = AllForOneStrategy() {
    case _: Exception => Restart
  }
```

&ensp;&ensp;&ensp; As we can see in the code, the _supervisionStrategy_ is set to _AllForOneStrategy()_, a method to apply a specific change to all actors from the system if any of the actors throws an exception. Thus, the superviser will restart all the workers if any of them will fail. 

## P0W5
**Minimal Task 1** -- Write an application that would visit this link. Print out the HTTP response
status code, response headers and response body.

```scala
    val url = new URL("https://quotes.toscrape.com/")
    val connection = url.openConnection().asInstanceOf[HttpURLConnection]
    connection.setRequestMethod("GET")
    println(s"Response status code: ${connection.getResponseCode}")
    println("Response headers:")
    connection.getHeaderFields.forEach((key, value) => println(s"$key: $value"))
    body = scala.io.Source.fromInputStream(connection.getInputStream).mkString
    println(s"Response body: $body")
    connection.disconnect()

```

&ensp;&ensp;&ensp; This function implements a HTTP GET request to the URL "https://quotes.toscrape.com/" using the HttpURLConnection class. First, it opens a connection to the URL using the _openConnection()_ method of the URL object, and casts the resulting URLConnection object to an HttpURLConnection object. Secondly it sets the request method for the connection to "GET" using the _setRequestMethod()_. Then, the request is sent to the server and the response status code is retrieved using the _getResponseCode()_ method. Finally, it retrieves the response headers and prints them out using a forEach loop. Then it retrieves the response body and prints it out.

**Minimal Task 2** -- Extract all quotes from the HTTP
response body. Collect the author of the quote, the quote text and tags. Save the data
into a list of maps, each map representing a single quote.

```scala
val selectGeneralBlock = "<div class=\"quote\"[\\s\\S]*?<\\/div>".r
    val selectQuotes = "<span class=\"text\" itemprop=\"text\">(“.*)<\\/span>".r
    val selectAuthors = "itemprop=\"author\">(.*)<\\/small>".r
    val selectTags = "<a class=\"tag\".*?>(.*)<\\/a>".r

```

&ensp;&ensp;&ensp; In my opinion the most difficult part of this task were to select the information needed and for this I used Regular Expression. _selectGeneralBlock_ select only the `<div>` tags with class = "quote". Then, for quotes, authors and tags I identified a pattern and used it to find all the elements which corresponds to the needed information.

```scala
        val listOfMaps = ArrayBuffer[Map[String, String]]()
    val listOfQuotes = selectGeneralBlock.findAllIn(body).toList

    for (element <- listOfQuotes) {
      val quote = selectQuotes.findFirstMatchIn(element).map(_.group(1)).fold("")(_.toString)
      val author = selectAuthors.findFirstMatchIn(element).map(_.group(1)).fold("")(_.toString)
      val tags = selectTags.findAllMatchIn(element).map(_.group(1)).mkString("; ")
      listOfMaps.addOne(Map("quote" -> quote, "author" -> author, "tags" -> tags))
    }
```
&ensp;&ensp;&ensp; Then, in a list all the general blocks are find using _findAllin()_ method, and all of them are stored. Iterating this list, the function searches in each block the quote, the author of it and the tags using regular expression defined before. All this information is stored in  _listOfMaps_ variable.

**Minimal Task 3** -- Persist the list of quotes into a file.
Encode the data into JSON format. Name the file quotes.json.

```scala
 val jsonFormat = Json.prettyPrint(Json.toJson(globalListOfMaps))
    val jsonFile = new File("quotes.json")
    val writer = new FileWriter(jsonFile)
    writer.write(jsonFormat.toString())
    writer.close()

```
&ensp;&ensp;&ensp;This block of Scala code writes a list of maps to a JSON file:
 + Convert the list of maps globalListOfMaps to a JSON string using the Play JSON library.

 + Create a new File object for the JSON file.

 + Create a new FileWriter object for the JSON file.

+ Write the JSON string to the file using the write() method of the FileWriter object.

+ Close the FileWriter object.

The resulting JSON file will contain the data in a structured format that can be easily read and processed by other programs.




## Conclusion

In conclusion, taking the first steps in studying Scala involves getting familiar with its key concepts such as the Actor model, data types and data structures such as lists or buffers. In addition, being able to make HTTP requests, retrieve data, parse it to JSON format, and write the data to files are also fundamental skills that are often required in real-world applications.

Understanding the Actor model is essential to writing concurrent, parallel, and distributed systems in Scala. It involves learning how to use actors as basic units of computation that communicate with each other using messages.

When operating with data types and structures such as lists or buffers, it is important to understand how to use their built-in methods to manipulate data efficiently. This can include operations such as filtering, mapping, reducing, or folding.

Making HTTP requests is often necessary when working with web applications or REST APIs. In Scala, this can be done using the built-in Java libraries or more advanced libraries such as Akka HTTP or Play Framework.

After making an HTTP request, it is often necessary to parse the response data into a structured format such as JSON. Scala provides several libraries such as Play JSON or Circe for this purpose.

Finally, once the data has been parsed and processed, it can be written to files for further analysis or storage. This involves working with file I/O operations such as creating or opening files, writing data to files, and closing them when done.

Overall, taking the first steps in studying Scala can be challenging, but it provides a solid foundation for developing scalable, concurrent, and distributed applications.

## Bibliography

[Scala Official Documentation](https://docs.scala-lang.org/overviews/collections/introduction.html)  
[Scala Arrays](https://www.tutorialspoint.com/scala/scala_arrays.htm)  
[Scala Arrays](https://docs.scala-lang.org/overviews/collections/arrays.html)  
[Akka classic official documentation](https://doc.akka.io/docs/akka/current/actors.html)  
[Java.net official documentation](https://docs.oracle.com/javase/7/docs/api/java/net/package-summary.html)  
[Regular Expressions](https://regex101.com/)