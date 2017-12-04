package arduino.service

import java.util.Date
import java.util.TooManyListenersException
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue
import com.mongodb.casbah.MongoClient
import com.mongodb.casbah.MongoClientURI
import com.mongodb.casbah.commons.MongoDBObject
import jssc.SerialPort
import jssc.SerialPortEvent
import jssc.SerialPortEventListener
import jssc.SerialPortException
import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.ActorRef

//pipe:BlockingQueue[String]




//object MongoFactory {
//  private val SERVER = "localhost"
//  private val PORT = 27017
//  private val DATABASE = "arduino"
//  private val COLLECTION = "events"
//
//  def getConnection: MongoConnection = {
//
//    return MongoConnection(SERVER)
//
//  }
//
//  def getCollection(conn: MongoConnection): MongoCollection = {
//
//    return conn(DATABASE)(COLLECTION)
//
//  }
//
//  def closeConnection(conn: MongoConnection) {
//
//    conn.close
//  }
//
//  def getMongoClient: MongoClient = MongoClient(SERVER, 27017)
//
//}




//package object custom_context {
//
//  implicit val ctx = new Context {
//    val name = "Custom Context"
//
//    // some overrides or custom behavior
//  }
//}

//case class ArduinoIncomingEvent(date: Date, component: String, message: String)

//object ArduinoIncomingEventDao extends SalatDAO[ArduinoIncomingEvent,Long](collection = MongoConnection()("arduino")("events"),ctx)

//@Service
class ArduinoListenerService(port: SerialPort, actor:ActorRef) extends SerialPortEventListener {

  override def serialEvent(event: SerialPortEvent) = {
 //   println ("Im in serial service")
    
    import MessagePersisterActor.ReceiveMessageCommand
    //println("value of event "+ event.getEventValue())
    println("bufer "+port.getInputBufferBytesCount())
    if(event.isRXCHAR())
    	actor ! ReceiveMessageCommand
    else if(event.isCTS())
      if(event.getEventValue()==1)
        println("CTS ON")
      else
        println("CTS OFF")
        
    
   // var s = ""
  //  val incomingString = port.readString()
    
    //println("incomming string is "+incomingString)
    //val incomingEvent: Array[String] = incomingString.split(":");

    //println("im here split1: " + incomingEvent(0) + "split2:" + incomingEvent(1))

//    val event = ArduinoIncomingEvent(new Date, incomingEvent(0), incomingEvent(1))
//
//    //TODO use logger here
//    println("about to persist event")
//
//    val builder = MongoDBObject.newBuilder
//    builder += "event_date" -> event.date
//    builder += "component" -> event.component
//    builder += "message" -> event.message
//    builder += "incoming" -> "arduino"
//
//    val persistableEvent = builder.result
//
//    val uri = """mongodb://localhost:27017/"""
//    val db = MongoClient(MongoClientURI(uri))("""tomate-loco""")
//    val collection = db("events")
//
//    collection.insert(persistableEvent)

  }

}

//@Service
class SerialService(
  portName: String,
  baudRate: Int = 9600,
  dataBits: Int = SerialPort.DATABITS_8,
  stopBits: Int = SerialPort.STOPBITS_1,
  parity: Int = SerialPort.PARITY_NONE) {

  val serialPort: SerialPort = {
    //if (portIdentifier.isCurrentlyOwned()) throw new IOException("Port " + portName + " is currently in use")
    val serialPort: SerialPort = new SerialPort(portName)
    serialPort.openPort()
    serialPort.setParams(baudRate, dataBits, stopBits, parity)
    serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN |
      SerialPort.FLOWCONTROL_RTSCTS_OUT)
    serialPort.setEventsMask(SerialPort.MASK_RXCHAR)
    //  serialPort.addEventListener(listener)
    serialPort
  }

  private lazy val pipeInput: BlockingQueue[String] = new LinkedBlockingQueue

 // lazy val listener: SerialPortEventListener = new ArduinoListenerService(serialPort)

  // def consumeString:String ={
  //   
  //   return pipeInput.take()
  // 
  // }

  def addListener(serialPortListener:SerialPortEventListener) = {
    try {

      serialPort.addEventListener(serialPortListener)

    } catch {

      case tm: TooManyListenersException => println("Too many listener")

    }
  }

  def removeListener = {
    serialPort.removeEventListener
  }

  def send(command: String) = {
    try {

      serialPort.writeString(command)

    } catch {
      case sx: SerialPortException => println("Serial port exception")
    }
  }

}