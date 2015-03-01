package arduino.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractMongoConfiguration
import com.mongodb.Mongo


@Configuration
class MongoDbConfig extends AbstractMongoConfiguration{
  
  def getDatabaseName:String = "arduinoDb"
    
  def mongo:Mongo= new Mongo("localhost")

}