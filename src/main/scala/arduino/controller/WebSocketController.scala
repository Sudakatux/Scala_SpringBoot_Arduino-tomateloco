package arduino.controller

import java.io.BufferedReader
import java.io.InputStreamReader
import scala.beans.BeanProperty
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import arduino.service.SerialService
import jssc.SerialPortEvent
import jssc.SerialPortEventListener
import arduino.service.EventManager
import arduino.service.ArduinoAction
import java.util.Date

@JsonIgnoreProperties(ignoreUnknown = true)
case class PlantCommand(@JsonProperty("name") name:String)

@JsonIgnoreProperties(ignoreUnknown = true)
case class PlantCommandResponse(@BeanProperty  response:String)

@Controller
class WebSocketControllerImpl  @Autowired() (serialService:SerialService,em:EventManager) {
  
  @MessageMapping(Array("/hello"))
  @SendTo(Array("/topic/greetings"))
  def plantCommander(plantCommand:PlantCommand):PlantCommandResponse = {
	
    
    
    println ("Will send "+plantCommand.name)
    
    serialService.send(plantCommand.name)
    
    Thread sleep 5000 // Waits for some response
    
    println ("Will consume  string")
      
    //em.save(new ArduinoAction("testing", new Date))
        
	return new PlantCommandResponse(serialService.consumeString)
	  //	return "testing"
  }
 

}