package arduino.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport
import org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping
import arduino.service.SerialService
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import com.fasterxml.jackson.databind.ObjectMapper

@Configuration
//@EnableWebMvc
class CustomBeans extends WebMvcConfigurationSupport{
  
  @Bean
  def serialService:SerialService ={
    val serialObject =  new SerialService("/dev/ttyACM0", 9600)
    
    serialObject.addListener
    
    return serialObject
  }
  
//  @Bean
//  def mappingJackson2HttpMessageConverter():MappingJackson2HttpMessageConverter ={
//    val jsonConverter:MappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter()
//    val mapper = new ObjectMapper()
//    
//    mapper.registerModule(DefaultScalaModule)
//    mapper.registerModule(CaseClassModule)
//    
//    jsonConverter.setObjectMapper(mapper)
//    
//    jsonConverter
//    
//  }
//  
//  override def configureMessageConverters(converters:List[HttpMessageConverter[_]]){
//	  //val converter = new MappingJackson2HttpMessageConverter("application/json")
//		converters.add(this.mappingJackson2HttpMessageConverter())
//  }
  
//  @Bean
//	def handleMapping() : DefaultAnnotationHandlerMapping  = {
//		new DefaultAnnotationHandlerMapping
//	}
 
//	@Override
//	override def configureMessageConverters(converters: java.util.List[HttpMessageConverter[_]]) {
//		
//		converters.add(this.mappingJackson2HttpMessageConverter())
//		
//	}
//  
  
  override def addResourceHandlers(registery:ResourceHandlerRegistry) = {
     registery.addResourceHandler("/**").addResourceLocations("/content/","classpath:/content/")
  }
  
  
//  @Override
// public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//  converters.add(customJackson2HttpMessageConverter());
//  super.addDefaultHttpMessageConverters();
// }
  
  
//  @Bean
//  def mappingJackson2HttpMessageConverter:Jackson2ObjectMapperBuilder ={
//    val jsonBuilder:Jackson2ObjectMapperBuilder = new Jackson2ObjectMapperBuilder()
//    jsonBuilder.
    
//    val mapper = new ObjectMapper
//    
//    mapper.registerModule(DefaultScalaModule)
//    mapper.registerModule(CaseClassModule)
//    
//    jsonConverter.setObjectMapper(mapper)
//    
//    jsonConverter
    
  //}
  
  
  
//  @Bean
//  public Jackson2ObjectMapperBuilder jacksonBuilder() {
//    Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
//    builder.indentOutput(true).dateFormat(new SimpleDateFormat("yyyy-MM-dd"));
//    return builder;
//}

}