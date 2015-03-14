package arduino.controller

import scala.beans.BeanProperty
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import arduino.service.SerialService
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.web.bind.annotation.RequestMethod


@JsonIgnoreProperties(ignoreUnknown = true)
case class PlantCommandResponse(@BeanProperty  response:String)

@RestController
@RequestMapping(Array("/test"))
class TestController @Autowired() (serialService:SerialService) {
  
  @RequestMapping(method=Array(RequestMethod.GET),value=Array("/api/v1/poor/{plant}"))
  def poorWater(@PathVariable plant:Integer):PlantCommandResponse={
    
    serialService.send("R"+plant)
    Thread sleep 5000 
    
    return new PlantCommandResponse("Ok")
    
  }
  
  
  

}

