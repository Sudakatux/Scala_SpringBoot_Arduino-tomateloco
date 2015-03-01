package arduino.service

import java.util.Date
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


case class ArduinoAction(name:String,date:Date)

trait ArduinoActionRepository extends MongoRepository[ArduinoAction,String]

@Service 
class EventManager @Autowired()(actionRepository:ArduinoActionRepository){
  
  def save(action:ArduinoAction)={
    actionRepository.save(action)
  }

}