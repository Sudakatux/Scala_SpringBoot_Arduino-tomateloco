#include <Servo.h> 


Servo myservo;

//long distancia;
//long tiempo;

int releOnPin=3;

int segundosDeRego=3;
int echoPin=9;
int trigerPin=8;


  // create servo object to control a servo 
                // a maximum of eight servo objects can be created 
 int pos = 0;

void setup(){
  myservo.attach(6);
  Serial.begin(9600);
  
  
  pinMode(releOnPin,OUTPUT);

  digitalWrite(releOnPin, LOW);
  
  moveToPosition(0);

}
/* Actions */

void regarAction(){
  //Serial.println("Poniendo en 180");
  char buffer[64];
  byte readLen = 0;
  
  if(Serial.available() > 0){
    int plantPos=Serial.parseInt();
    
    moveToPosition(plantPos);
    Serial.print("Wate:");
    char buf[5];
    sprintf(buf,"%05d",plantPos);
    Serial.println(buf);
    
    //Poor water
    digitalWrite(releOnPin, HIGH);
    
    //poor water for as long as seconds
    delay(segundosDeRego*1000); //parametro que tiene que ser configurable
    
    //Stop porring water
    digitalWrite(releOnPin, LOW);
    
    //Return to Original Position
    moveToOriginalPostion(plantPos);
    
    //Serial.println("R:0k");
  
  }
  
  
  
  
  
  
   // if(Serial.available()>0){
     // readLen = Serial.readBytes(buffer, readLen);
     // plantPos=strToInt(buffer, readLen);
      
          
   // }

}

int strToInt(char AStr[], byte ALen)
{
  int Result = 0;
  int c;
  for (int i=0; i < ALen; i++)
  {
    // calc the ordinal value of each character
    c = int(AStr[i] - '0');
    // check if the current char is number, otherwise return -1
    if (c < 0 || c > 9)
      return -1;
   
    // adjust the prior result and add the ordinal value of
    // the current char 
    Result = (Result * 10) + c;
  }
  return Result;
}

void moveToOriginalPostion(int lastPosition){

  for(pos=lastPosition; pos!=0; pos-=1)     // goes from 180 degrees to 0 degrees 
      {                                
        myservo.write(pos);              // tell servo to go to position in variable 'pos' 
        delay(50);                       // waits 15ms for the servo to reach the position 
      }
      pos=0;

}


void moveToPosition(int desiredPosition){

  if(pos==0){
    
    for(pos = 0; pos!=desiredPosition; pos+=1){
        myservo.write(pos);              // tell servo to go to position in variable 'pos' 
        delay(50);
    }
    
   
    
  
  }
  
  
  //Serial.print("Pos is");
  //Serial.println(pos);
      
}



/* End Actions */


/* Poolers */

int distancePooler(){
  int distance;
  int time;
  
  digitalWrite(echoPin,LOW); /* Por cuestión de estabilización del sensor*/
  delayMicroseconds(5);
  
  digitalWrite(echoPin, HIGH); /* envío del pulso ultrasónico*/
  delayMicroseconds(10);
  
  time=pulseIn(trigerPin, HIGH); 
  
  /* Función para medir la longitud del pulso entrante. Mide el tiempo que transcurrido entre el envío
  del pulso ultrasónico y cuando el sensor recibe el rebote, es decir: desde que el pin 12 empieza a recibir el rebote, HIGH, hasta que
  deja de hacerlo, LOW, la longitud del pulso entrante*/
  
  distance= int(0.017*time); /*fórmula para calcular la distancia obteniendo un valor entero*/
  /*Monitorización en centímetros por el monitor serial*/
  //Serial.println("Distancia ");
  
  //Serial.println(distancia);
  
  return distance;
  
  //Serial.println(" cm");
  //delay(1000);
}


/* End Readers */

void eventPublisher(int distance){
  
  Serial.println("DI:");
  Serial.println(distance);
  Serial.print("\n");

}


void readIncomingMessage(){
  if(Serial.available()>0){
    
    char firstByte =Serial.read(); 
     switch(firstByte){
       case 'R':
         //Serial.println("R:");
         //Serial.println(segundosDeRego);
         //Serial.println("s\n");
         regarAction();
         //Serial.println("R:F\n");
         break;
       case 'D':
         Serial.println("D:");
         Serial.println(distancePooler());
         Serial.println("cm\n");
         break;
       default:
         Serial.print("E:Unknown Module\n");
         break;
   
     }
   
    
    
  }
}


void loop(){
  
  readIncomingMessage();
  

  
  delay(1000);
}
