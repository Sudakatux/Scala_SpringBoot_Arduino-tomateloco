/*
  DigitalReadSerial
 Reads a digital input on pin 2, prints the result to the serial monitor

 This example code is in the public domain.
 */

// digital pin 2 has a pushbutton attached to it. Give it a name:


// the setup routine runs once when you press reset:
void setup() {
  // initialize serial communication at 9600 bits per second:
  Serial.begin(9600);
  // make the pushbutton's pin an input:
//  pinMode(pushButton, INPUT);
}

// the loop routine runs over and over again forever:
void loop() {
  // read the input pin:
 // int buttonState = digitalRead(pushButton);
  // print out the state of the button:
  //Serial.println("hola");
  
  if(Serial.available()>0){
        //Serial.println("got stuff");  
    char firstByte =Serial.read(); 
     switch(firstByte){
       case 'R':
         Serial.print("R:");
         Serial.print(3);
         Serial.println ("s");
         
         
      default:
        Serial.println("Unknown command"q);  
     }
  }else{
    //Serial.println("nada");
  }
         //Serial.println("R:F\n");
  delay(1);        // delay in between reads for stability
}



