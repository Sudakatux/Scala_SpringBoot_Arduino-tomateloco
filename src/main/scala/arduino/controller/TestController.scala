package arduino.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import arduino.service.SerialService
import org.springframework.web.bind.annotation.RequestMethod


@RestController
@RequestMapping(Array("/test"))
class TestController @Autowired() (serialService:SerialService) {
  
  @RequestMapping(method=Array(RequestMethod.GET))
  def sayHello()={
    //val s = new SerialService("/dev/ttyACM0", 9600)
    
    var response=""
    
    serialService.send("something")
    
   
    
    response
  
  }
  
  
  

}

