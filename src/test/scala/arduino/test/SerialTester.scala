package arduino.test

import org.scalatest.FunSuite
import org.scalatest.Matchers
import arduino.service.SerialService
import arduino.service.SerialService
import jssc.SerialPortEventListener
import jssc.SerialPortEvent


//@RunWith(classOf[JUnitRunner])
class SerialTester extends FunSuite{
  
  test("test object creation"){
	val serialService = new SerialService("/dev/ttyACM0", 9600)
	
	assert(true)
	
  }
  
  test("service should be able to receive a listener"){
    println("I can see this in the console")
    
    val serialService = new SerialService("/dev/ttyACM0", 9600)
   // serialService.addListener
    
    Thread sleep 1000
    serialService.send("R")
    Thread sleep 4000
    println("awake")
    //println(serialService.consumeString)
    //println(serialService.consumeString)
    
  }

}