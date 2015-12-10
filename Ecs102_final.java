/*
 * Xiaozhi Li
 * ECS 102 Final project
 * Vending machine similuator
 * 
project proposal:
I am going to write a program that simulates a vending machine.
The computer will ask if the customer uses a credit card or cash, once chosen, the computer will display a catalog of the inventory, with prices, names, and quantities.
Then the computer will ask the customer to make a choice.
There is a random chance that the customer can get free item.
Then there will be instructions saying the item is delivered.
Then the vending machine will return changes using quarters if customer used cash prior.
There is also an option for vending machine workers to check the current inventory of coins and items using a prepared .txt file, the computer will out put a txt file with each quantities and alert if anything need to be refilled.
(I鈥檓 planning on using array list for coins)

 */
//package ecs102_final;
import java.util.Scanner;
import java.io.*;
/**
 *
 * @author turfmacia
 */
public class Ecs102_final {

    

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) throws IOException {
     //declaring inputs.   
     String paymentChoice;
     int maxIndex=15;
     double currentCash=0;
     double charge;
     boolean ifFree=false;
     String userEnter;
     int choice;
     String buffer;
     Item []storage= new Item[maxIndex];
     //input scanner
     Scanner usr = new Scanner(System.in);

     //read in the file.
     Scanner inventory= new Scanner(new File("inventory.txt"));
     //assign every object in the array with an empty object.
     for(int i=0;i<maxIndex;i++)
     {  storage[i]=new Item();}
       
     //asking user to make selection of payment
     System.out.print(message("welcome"));
     userEnter=usr.next();
     if(userEnter.equals("1"))
       creditpay();
     else if(userEnter.equals("2"))
       debitpay();
     else
       currentCash=cashpay();
     //if there were an error, we shut down the program.
      if(currentCash==-1) return;
     
     //take the input file of inventory and calculate
      System.out.print(message("order"));
     if(searchLine(inventory,"inventory:"))
       { buffer=inventory.nextLine();// adjust the max index so we dont fill array with wrong type.
       maxIndex=Integer.parseInt(buffer); 
       int i;
         for(i=0;i<maxIndex;i++)
       System.out.print(i+")"+checkInventory(storage[i],inventory));
         maxIndex=i-1;
       }
     choice=usr.nextInt();
     if(choice>maxIndex||choice<0)System.out.print(message("do not exist"));
     else
       return;
     
     
     //
    System.out.println("This means we worked!");
    }
    
    

    static String  checkInventory(Item it,Scanner in){
     String result="";
      if(in.hasNext()){
            it.name=in.next();
            it.price=in.nextDouble();
            it.quantity=in.nextInt();
            in.nextLine();
            result=(it.name+" "+it.price+"\n");
      }
      else return "";  
        
        return result;
    }
        // will be used for out put messages.
   static String message(String message){
      String ans="";  
      //use a switch for different message cases
      switch (message){
        //the following is our welcome message.
        case "welcome": ans="________________________________________\n"+
        "Vending Machine 1.0\nWelcome! customer, Please make your choice:\n"+
          "enter a number for your prefer of payment:\n"+
          "will you use a 1)credit card, 2) debit card;\n"+
        "or cash if you enter anyother number:\n";
         break;
        case"cash": ans="Great now please insert the money:\n";
          break;
        case"currency":ans="1)a quater; 2) 1 dollar: 3) 5 dollar;\n"+
          "4) 10 dollar; 5) 20 dollar;6)That is enough.\n";
        break;
        case"wrong input1": ans="!!Wrong input!! Please select a number from 1-5.\n";
        break;
        case"wrong input2": ans= "!!Wrong input!! Stop treating me like a stupid computer,\n"+
            "I am calling the cops if you dont make this one right!\n"+
          "selections:\n";
        break;
        case"call police": ans="!!Wrong input!! Attempted damaging of private property detected.\n"+
          "Dialling 911..\n";
        break;
        
        case"order":ans="What would you like today?\n";
        break;
        case"do not exist":ans="!!Error, selection does not exist!!\n"+
          "You money shall be returned.\n";
        break;
        case"nice day":ans="Have a nice day!\n";
        break;
        case"grats":ans="Congratulations! you just won an free item.\n"+
          "This purchase is free of charge.\n";
        break;
        case"credit":ans="Thanks for taking advantage of mordern payment.\n"+
          "You selected credit card.\n";
        break;
        case"debit":ans="Thanks for taking advantage of mordern payment.\n"+
          "You selected debit card.\n";
        break;
        
      }
        return ans;
    }
   
   //this method returns the amount of cash user entered.
   //will return -1 if the input is wrong.
   static double cashpay(){
     Scanner in=new Scanner(System.in);
     double result=0.00;
     int select=0;
     //we would give the user 3 chances to make the right option
     int errortime=0;
     
     System.out.print(message("cash"));
     do{System.out.print(message("currency"));
     select=in.nextInt();
     if(select>6&&errortime<1) {
       System.out.print(message("wrong input1"));
       errortime+=1;
     }
     else if(select>6&&errortime<2)
     {System.out.print(message("wrong input2"));
       errortime+=1;}
     else if(select>6&&errortime<3)
     {System.out.print(message("call police"));
       errortime+=1;
     return -1;
     }     
      switch (select){
        case 1:result+=.25;
        break;
        case 2:result+=1.00;
        break;
        case 3:result+=5.00;
        break;
        case 4:result+=10.00;
        break;
        case 5:result+=20.00;
        break;
        case 6:errortime=3;
        break;
        
      }
      System.out.print("You inserted "+ result+"$\n");
     }
     while(errortime<3&&result<25);
     if(result>25) System.out.println("Stop putting in money!\nThat is enough!");
     return result;
   }
   static String creditpay(){
     Scanner in=new Scanner(System.in);
     String result="";
     System.out.print(message("credit"));
     return result;
   }
   static String debitpay(){
     Scanner in=new Scanner(System.in);
     String result="";
     System.out.print(message("debit"));
     return result;
     
   }
   static boolean searchLine(Scanner in, String cate){
     boolean ans=false;
     String buf="";
     while(in.hasNext()){
       buf=in.nextLine();
       if(buf.equals(cate))
       {ans=true;break;}
     }
     return ans;
   }
}
