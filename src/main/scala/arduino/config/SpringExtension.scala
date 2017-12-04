package arduino.config
import akka.actor.ActorSystem
import akka.dispatch.sysmsg.Create
import akka.actor.IndirectActorProducer
import akka.actor.Actor
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import akka.actor.AbstractExtensionId
import akka.actor.ExtendedActorSystem
import akka.actor.Props
import akka.actor.Extension

class SpringActorProducer(applicationContext:ApplicationContext,actorName:String) extends IndirectActorProducer{

 override def produce():Actor={
   
  return applicationContext.getBean(actorName) match{
    case actor:Actor =>actor
    case _=> throw new ClassCastException
    
  }
 }
  override def actorClass:Class[_ <: Actor] =  applicationContext.getType(actorName).asInstanceOf[Class[_ <: Actor]]
  
}

object SpringExtentionImpl {    
  def apply(system : ActorSystem,ctx: ApplicationContext) :  SpringExtentionImpl = 
  SpringExtension().get(system).initialize(ctx)
}

/**
 * The Extension implementation.
 */
class SpringExtentionImpl extends Extension {
  var applicationContext: ApplicationContext = _

  /**
   * Used to initialize the Spring application context for the extension.
   * @param applicationContext
   */
  def initialize(applicationContext: ApplicationContext) = {
    this.applicationContext = applicationContext
    this
  }

  /**
   * Create a Props for the specified actorBeanName using the
   * SpringActorProducer class.
   *
   * @param actorBeanName The name of the actor bean to create Props for
   * @return a Props that will create the named actor bean using Spring
   */
  def props(actorBeanName: String): Props =
    Props(classOf[SpringActorProducer], applicationContext, actorBeanName)

}




object SpringExtension {
  
  def apply() : SpringExtension = new SpringExtension

}


class SpringExtension extends AbstractExtensionId[SpringExtentionImpl] {
    import SpringExtension._
  /**
   * Is used by Akka to instantiate the Extension identified by this
   * ExtensionId, internal use only.
   */
  override def createExtension(system: ExtendedActorSystem) = new SpringExtentionImpl

  /**
   * Java API: retrieve the SpringExt extension for the given system.
   */
  override def get(system: ActorSystem): SpringExtentionImpl = super.get(system)

}
