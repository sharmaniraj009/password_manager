import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Scanner;

public class MessageDigestExample {
   public static void main(String[] args) throws Exception{
      try (//Reading data from user
      Scanner sc = new Scanner(System.in)) {
         System.out.println("Enter the message");
         String message = sc.nextLine();

         System.out.println("input the message to verify");
         String verificationString = sc.nextLine();
  
         //Creating the MessageDigest object  
         MessageDigest md = MessageDigest.getInstance("SHA-512");
         MessageDigest me = MessageDigest.getInstance("SHA-512");

         //Passing data to the created MessageDigest Object
         md.update(message.getBytes());
         me.update(verificationString.getBytes());
         
         //Compute the message digest
         byte[] digest = md.digest();      
         System.out.println(Arrays.toString(digest));

         byte[] digeste = me.digest();
         System.out.println(Arrays.toString(digest));
    
         //Converting the byte array in to HexString format
         StringBuilder
                 hexString = new StringBuilder();
         StringBuilder hexStringe = new StringBuilder();

          for (byte b : digest) {
              hexString.append(Integer.toHexString(0xFF & b));
          }

          for (byte b : digeste) {
              hexStringe.append(Integer.toHexString(0xFF & b));
          }


         System.out.println("Hex format : " + hexString);
         System.out.println("Hex format : " + hexStringe);

         if(hexString.toString().contentEquals(hexStringe)){
            System.out.println("The message is verified");
         }else{
            System.out.println("The message is not verified");
         }
      }
      
   }
}