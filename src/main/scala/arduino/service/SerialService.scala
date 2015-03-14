package arduino.service

import java.util.Date
import java.util.TooManyListenersException
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue
import com.mongodb.casbah.MongoCollection
import com.mongodb.casbah.MongoConnection
import com.novus.salat.Context
import jssc.SerialPort
import jssc.SerialPortEvent
import jssc.SerialPortEventListener
import jssc.SerialPortException
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.MongoClient
import com.mongodb.casbah.MongoClientURI

//pipe:BlockingQueue[String]

object MongoFactory {
  private val SERVER = "localhost"
  private val PORT = 27017
  private val DATABASE = "arduino"
  private val COLLECTION = "events"

  def getConnection: MongoConnection = {

    return MongoConnection(SERVER)

  }

  def getCollection(conn: MongoConnection): MongoCollection = {

    return conn(DATABASE)(COLLECTION)

  }

  def closeConnection(conn: MongoConnection) {

    conn.close
  }

  def getMongoClient: MongoClient = MongoClient(SERVER, 27017)

}
package object custom_context {

  implicit val ctx = new Context {
    val name = "Custom Context"

    // some overrides or custom behavior
  }
}

case class ArduinoIncomingEvent(date: Date, component: String, message: String)

//object ArduinoIncomingEventDao extends SalatDAO[ArduinoIncomingEvent,Long](collection = MongoConnection()("arduino")("events"),ctx)

//@Service
class ArduinoListenerService(port: SerialPort) extends SerialPortEventListener {

  override def serialEvent(event: SerialPortEvent) = {
    var s = ""
    val incomingString = new String(port.readBytes(10), "UTF-8")
    val incomingEvent: Array[String] = incomingString.split(":");

    println("im here " + incomingString + "split1" + incomingEvent(0) + "split2" + incomingEvent(1))

    val event = ArduinoIncomingEvent(new Date, incomingEvent(0), incomingEvent(1))

    println("about to persist evant")

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

  }

}

//@Service
class SerialService(
  portName: String,
  baudRate: Int = 9600,
  dataBits: Int = SerialPort.DATABITS_8,
  stopBits: Int = SerialPort.STOPBITS_1,
  parity: Int = SerialPort.PARITY_NONE) {

  private lazy val port: SerialPort = {
    //if (portIdentifier.isCurrentlyOwned()) throw new IOException("Port " + portName + " is currently in use")
    val serialPort: SerialPort = new SerialPort(portName)
    serialPort.openPort()
    serialPort.setParams(baudRate, dataBits, stopBits, parity)
    serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN |
      SerialPort.FLOWCONTROL_RTSCTS_OUT)
    //  serialPort.addEventListener(listener)
    serialPort
  }

  private lazy val pipeInput: BlockingQueue[String] = new LinkedBlockingQueue

  private lazy val listener: SerialPortEventListener = new ArduinoListenerService(port)

  // def consumeString:String ={
  //   
  //   return pipeInput.take()
  // 
  // }

  def addListener() = {
    try {

      port.addEventListener(listener)

    } catch {

      case tm: TooManyListenersException => println("Too many listener")

    }
  }

  def removeListener = {
    port.removeEventListener
  }

  def send(command: String) = {
    try {

      port.writeString(command)

    } catch {
      case sx: SerialPortException => println("Serial port exception")
    }
  }

}