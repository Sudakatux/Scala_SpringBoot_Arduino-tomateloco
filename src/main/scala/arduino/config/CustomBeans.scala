package arduino.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import akka.actor.ActorSystem
import arduino.service.HeartBeatActor
import arduino.service.SerialService
import arduino.service.SerialService
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import org.springframework.context.annotation.Scope



@Configuration
class CustomBeans  {
  @Autowired
  var ctx:ApplicationContext=_
  //implicit val ctx=applicationContext;
   
   @Bean
  def serialService():SerialService={
    val serialObject =  new SerialService("/dev/ttyACM0", 9600)
    
    //serialObject.addListener
    
    return serialObject
  }
   
  @Bean
  def actorySystem:ActorSystem ={
    val system = ActorSystem("AkkaScalaSpring")
    SpringExtentionImpl.apply(system,ctx)
    return system
  }
  
  @Bean
  def akkaConfiguration:Config={
    ConfigFactory.load()
  } 
  
  
 // @Bean
  //@Scope("prototype")
  //def heartBeatActor:HeartBeatScheduler = {
   // val heartBeatActor = new HeartBeatScheduler(this.serialService)
    //heartBeatActor
    //heartBeatActor.serialService=serialService()
    
  //}
 

}