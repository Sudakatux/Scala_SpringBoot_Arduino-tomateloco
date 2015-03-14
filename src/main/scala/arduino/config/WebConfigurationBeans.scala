package arduino.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport

import arduino.service.SerialService

@Configuration
//@EnableWebMvc
class WebConfigurationBeans extends WebMvcConfigurationSupport {
  
 
  
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