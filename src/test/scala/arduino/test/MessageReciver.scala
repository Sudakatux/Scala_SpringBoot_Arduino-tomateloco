package arduino.test

import org.scalatest.FunSuite

class MessageReciver extends FunSuite{
  
  test("simple message tester"){
    val simpleMessage="<DIST:00025>"
    val possibleBuffer=simpleMessage+"\r\n"
      
      val messageArray=possibleBuffer.split(">")
      
      println(messageArray(0))
      
      assert(messageArray(0).replace("<", "").replace(">", "").equals(simpleMessage.replace("<", "").replace(">", "")))

      
      
  }
  
   test("Not so simple buffer"){
    val simpleMessage="<DIST:00025>"
    var possibleBuffer:Array[Byte]=new Array[Byte](1000)
    var bufferPos=0
    
     val incomingBuffer=new String(simpleMessage+"<DIST:").toCharArray().map(_.toByte)
      
     Array.copy(incomingBuffer, 0, possibleBuffer, bufferPos, incomingBuffer.length) 
     bufferPos+=incomingBuffer.length
     
     
     
     val messageArray=new String(possibleBuffer.map(_.toChar)).split(">")
      
      val stringToPutInBuffer=messageArray.filter(e=>e != messageArray(0)).foldLeft("")((s,n)=>s+n)
    
      val messageToPersist=messageArray(0).replace("<", "").replace(">", "")
      
        for (i <- 0 until possibleBuffer.length - 1)
        {
        	if(i<=stringToPutInBuffer.length()-1){
        		possibleBuffer.update(i, stringToPutInBuffer.toCharArray()(i).toByte)
        	}
          
        }
      
    	     
      
      assert(messageToPersist.equals(simpleMessage.replace("<", "").replace(">", "")))
      
      println(new String(possibleBuffer.map(_.toChar)))
      println("length is"+new String(possibleBuffer.map(_.toChar)).length())
      //assert(new String(possibleBuffer.map(_.toChar)) == "<DIST:")
      
      
  }

}