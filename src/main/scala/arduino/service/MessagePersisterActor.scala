package arduino.service

import akka.actor.{ ActorSystem, ActorLogging, Actor, Props }
import org.springframework.beans.factory.annotation.Autowired
import java.util.Date
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.MongoClient
import com.mongodb.casbah.MongoClientURI
import jssc.SerialPort
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service

case class ArduinoIncomingEvent(date: Date, component: String, message: String)

object MessagePersisterActor {
  case object ReceiveMessageCommand
}
@Service //TODO this class should be changed for scala actors
@Scope("prototype")
class MessagePersisterActor @Autowired() (serialService: SerialService) extends Actor {

  //import MessagePersisterActor._
  var internalBuffer = new Array[Byte](1000)
  var bufferPos = 0

  import MessagePersisterActor._

  def receive = {

    case ReceiveMessageCommand =>
      println("got here ")

      val serialPort: SerialPort = serialService.serialPort
      val buffer: Option[Array[Byte]] = Option(serialPort.readBytes)

      println("buffer content:  " + buffer)


      buffer match {
        case None => println("Null came in. Why?")
        case Some(x) =>

          Array.copy(buffer.get, 0, internalBuffer, bufferPos, buffer.get.length)

          bufferPos += buffer.get.length

          val message: String = new String(internalBuffer.map(_.toChar))

          //This could be made even better if i can ask for the byte equivelent
          if (message.contains(">")) { //Got a full message
            //Split message
            val arrayMensajes = message.split(">") //Split messages
            
            //Build remaing string getting rid of the first string
            val stringRemainingInBuffer = arrayMensajes.filter(e => e != arrayMensajes(0)).foldLeft("")((s, n) => s + n)

            //persist message
            persistMessage(arrayMensajes(0).replace("<", "").replace(">", ""))
            
            //Reset buffer pos
            bufferPos=0;
            
            //rebuild buffer with the remaing string
            for (i <- 0 until internalBuffer.length - 1) {
              if (i <= stringRemainingInBuffer.length() - 1) {
                internalBuffer.update(i, stringRemainingInBuffer.toCharArray()(i).toByte)
                bufferPos+=1;
              }

            }
            
          }


      }

    case _ =>
      println("Aca llego joya")
  }

  def persistMessage(message: String) = {
    println("persist message called message " + message)
    val incomingEvent: Array[String] = message.split(":");
    val event = ArduinoIncomingEvent(new Date, incomingEvent(0), incomingEvent(1))

    //TODO use logger here
    println("about to persist event")

    val builder = MongoDBObject.newBuilder
    builder += "event_date" -> event.date
    builder += "component" -> event.component
    builder += "message" -> event.message
    builder += "incoming" -> "arduino"

    val persistableEvent = builder.result

    val uri = """mongodb://localhost:27017/"""
    val db = MongoClient(MongoClientURI(uri))("""tomate-loco""")
    val collection = db("events")

    collection.insert(persistableEvent)

    //Clean the buffer
    for (i <- 0 until internalBuffer.length - 1)
      internalBuffer.update(i, 0)

  }

}