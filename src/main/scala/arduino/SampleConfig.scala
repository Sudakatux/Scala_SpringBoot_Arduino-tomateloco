package arduino

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.boot.SpringApplication
import org.springframework.context.ApplicationContext
import akka.actor.ActorSystem
import akka.event.LoggingAdapter
import akka.event.Logging
import arduino.config.SpringExtentionImpl
import scala.concurrent.duration._
import arduino.service.SerialService
import arduino.service.ArduinoListenerService

@Configuration
@EnableAutoConfiguration
@ComponentScan
class SampleConfig 


object SampleConfig extends App{
  
  val context:ApplicationContext=SpringApplication.run(classOf[SampleConfig])
  
 val system= context.getBean(classOf[ActorSystem])
 
 val log:LoggingAdapter=Logging.getLogger(system, "Application")

 val heartBeatActor=system.actorOf(SpringExtentionImpl(system,context).props("heartBeatActor"))
 
 val messagePersistorActor=system.actorOf(SpringExtentionImpl(system,context).props("messagePersisterActor"))
 
 import system.dispatcher
 import arduino.service.HeartBeatActor.Tick
 
 val scheduler = system.scheduler.schedule(0 milliseconds , 10 seconds,heartBeatActor,Tick)

 //register message persister actor
  val serialService = context.getBean(classOf[SerialService])
  
 //TODO add actor
  serialService.addListener(new ArduinoListenerService(serialService.serialPort,messagePersistorActor))
}
