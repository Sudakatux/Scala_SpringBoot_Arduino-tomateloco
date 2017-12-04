package arduino.config

import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry


//@Configuration
//@EnableWebSocketMessageBroker
class WebSocketsConfig extends AbstractWebSocketMessageBrokerConfigurer{
  
  
  
  override def configureMessageBroker(config:MessageBrokerRegistry) ={
    
    config.enableSimpleBroker("/topic")
    config.setApplicationDestinationPrefixes("/app")
 
  }
  
  
  override def registerStompEndpoints(registry:StompEndpointRegistry){
    
    registry.addEndpoint("/hello").withSockJS();
  
  }
  

}