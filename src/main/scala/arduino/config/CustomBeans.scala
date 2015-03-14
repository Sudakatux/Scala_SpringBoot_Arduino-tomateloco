package arduino.config

import arduino.service.SerialService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class CustomBeans {
  
   @Bean
  def serialService:SerialService ={
    val serialObject =  new SerialService("/dev/ttyACM1", 9600)
    
    serialObject.addListener
    
    return serialObject
  }

}