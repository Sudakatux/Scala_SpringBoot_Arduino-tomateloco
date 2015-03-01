package arduino.service

import java.util.TooManyListenersException
import java.util.concurrent.BlockingQueue
import jssc.SerialPort
import jssc.SerialPortEvent
import jssc.SerialPortEventListener
import java.util.concurrent.LinkedBlockingQueue
import jssc.SerialPortException
import scala.collection.mutable.StringBuilder



class ArduinoListener(port:SerialPort,pipe:BlockingQueue[String]) extends SerialPortEventListener {
  
	
    override def serialEvent(event: SerialPortEvent) = {
    	var s=""
    	val sBuilder = new StringBuilder
    	
    	sBuilder.append(new String(port.readBytes(4),"UTF-8"))
        
      pipe put sBuilder.toString    
    }
    
   
}



//@Service
class SerialService (
  portName: String,
  baudRate: Int = 9600,
  dataBits: Int = SerialPort.DATABITS_8,
  stopBits: Int = SerialPort.STOPBITS_1,
  parity: Int = SerialPort.PARITY_NONE) {



  private lazy val port: SerialPort = {
    //if (portIdentifier.isCurrentlyOwned()) throw new IOException("Port " + portName + " is currently in use")
    val serialPort:SerialPort = new SerialPort(portName)
    serialPort.openPort()
    serialPort.setParams(baudRate, dataBits, stopBits, parity)
    serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | 
                                  SerialPort.FLOWCONTROL_RTSCTS_OUT)
  //  serialPort.addEventListener(listener)
    serialPort
  }
  
  private lazy val pipeInput:BlockingQueue[String]= new LinkedBlockingQueue
 

  private lazy val listener:SerialPortEventListener = new ArduinoListener(port,pipeInput)
  
 def consumeString:String ={
   
   return pipeInput.take()
 
 }
 
  
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
    try{
      
    	port.writeString(command)
    	
    }catch{
      case sx:SerialPortException => println("Serial port exception")
    }
  }

}