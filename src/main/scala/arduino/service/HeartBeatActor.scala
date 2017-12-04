package arduino.service

import org.springframework.stereotype.Service
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.beans.factory.annotation.Autowired
import org.slf4j.LoggerFactory
import org.slf4j.Logger
import akka.actor.{ActorSystem, ActorLogging, Actor, Props}
import org.springframework.context.annotation.Scope

object HeartBeatActor {
	case object Tick
  
}

@Service //TODO this class should be changed for scala actors
@Scope("prototype")
class HeartBeatActor @Autowired() (serialService:SerialService)  extends Actor with ActorLogging {
  //val logger:Logger = LoggerFactory.getLogger(classOf[HeartBeatScheduler])
  import HeartBeatActor._
  
  //var serialService:SerialService= _
  
  def receive = {
    
  case Tick =>
  	  println("received something")
      serialService.send("H")
  }
  

}