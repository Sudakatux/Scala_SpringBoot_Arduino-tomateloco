//package arduino.util
//
//import org.springframework.http.converter.HttpMessageNotWritableException
//import org.springframework.http.converter.HttpMessageConverter
//import org.springframework.http.converter.HttpMessageNotReadableException
//import org.springframework.http.HttpInputMessage
//import java.io.IOException
//import com.codahale.jerkson.Json
//import org.springframework.http.HttpOutputMessage
//import org.springframework.http.MediaType
//import scala.collection.JavaConversions
//
//class MappingJerksonHttpMessageConverter(val supportedMediaType: MediaType) extends HttpMessageConverter[Any] {
//
//  override def canRead(clazz: Class[_], mediaType: MediaType): Boolean = {
//
//    Json.canDeserialize(Manifest.classType(clazz))
//  }
//
//  override def canWrite(clazz: Class[_], mediaType: MediaType): Boolean = {
//    Json.canSerialize(Manifest.classType(clazz));
//  }
//
//  override def getSupportedMediaTypes(): java.util.List[MediaType] = {
//    JavaConversions.asJavaList(List(supportedMediaType))
//  }
//
//  @throws(classOf[IOException])
//  @throws(classOf[HttpMessageNotReadableException])
//  override def read(clazz: Class[_], inputMessage: HttpInputMessage): AnyRef = {
//    Json.parse(inputMessage.getBody())
//  }
//
//  @throws(classOf[IOException])
//  @throws(classOf[HttpMessageNotWritableException])
//  override def write(contentType: AnyRef, mediaType: MediaType, outputMessage: HttpOutputMessage): Unit = {
//    Json.generate(contentType, outputMessage.getBody())
//  }
//}