import java.net.*;  // for DatagramSocket, DatagramPacket, and InetAddress
import java.io.*;   // for IOException
import java.util.Scanner;
import java.util.Arrays;

public class ClientUDP {

  public static void main(String args[]) throws Exception {

      if (args.length != 2 && args.length != 3)  // Test for correct # of args        
	       throw new IllegalArgumentException("Parameter(s): <Destination>" +
					     " <Port> [<encoding]");
      
      
      InetAddress destAddr = InetAddress.getByName(args[0]);  // Destination address
      int destPort = Integer.parseInt(args[1]);               // Destination port
      
      long start = System.currentTimeMillis();
      DatagramSocket sock = new DatagramSocket(); // UDP socket for sending 
      RequestEncoder encoder = (args.length == 3 ? new RequestEncoderBin(args[2]) : new RequestEncoderBin());
      DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);

      byte tml = 10;
      short request_id = 1;
      byte x,a4,a3,a2,a1,a0= 0;
      byte checksum = 1;
      int quit = 0;
      Scanner scan = new Scanner(System.in);
      while(quit == 0){
        System.out.print("Enter The x: ");
        x = scan.nextByte();
        System.out.print("Enter The a4: ");
        a4 = scan.nextByte();
        System.out.print("Enter The a3: ");
        a3 = scan.nextByte();
        System.out.print("Enter The a2: ");
        a2 = scan.nextByte();
        System.out.print("Enter The a1: ");
        a1 = scan.nextByte();
        System.out.print("Enter The a0: ");
        a0 = scan.nextByte();
        Request request = new Request(tml,request_id++,x,a4,a3,a2,a1,a0,checksum);
        try {
                byte[] codedRequest = encoder.encode(request); // Encode friend
                codedRequest[9] = checksum(codedRequest);
                // debugging purposes
                for (byte b : codedRequest) {
                  String st = String.format("%02X", b);
                  System.out.print(st + " ");
                }

                DatagramPacket message = new DatagramPacket(codedRequest, codedRequest.length, destAddr, destPort);
                sock.send(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        sock.receive(packet);
        ByteArrayInputStream payload = new ByteArrayInputStream(packet.getData(), packet.getOffset(),
                    packet.getLength());
        DataInputStream src = new DataInputStream(payload);
        byte new_tml = src.readByte();
        short id = src.readShort();
        byte err = src.readByte(); 
        int result = src.readInt();
        byte check_sum = src.readByte();
        System.out.println("\nRequest ID: " + id);
        System.out.println("x = " + x);
        String polinomial = "P(x) = ";
        if (a4 != 0) {
          polinomial = polinomial.concat(a4 +"x^4");
        }
        if (a3 != 0){
          if (a4==0){
            polinomial = polinomial.concat(a3 +"x^3");
          }else{
            polinomial = polinomial.concat(" + " + a3 +"x^3");
          }
        }
        if (a2 != 0){
          if (a4==0 && a3==0){
            polinomial = polinomial.concat(a2 +"x^2");
          }else{
            polinomial = polinomial.concat(" + " + a2 +"x^2");
          }
        }
        if (a1 != 0){
          if (a4==0 && a3==0 && a2==0){
            polinomial = polinomial.concat(a1 +"x");
          }else{
            polinomial = polinomial.concat(" + " + a1 +"x");
          }
        }
        if (a0 != 0){
          if (a4==0 && a3==0 && a2==0 && a1==0){
            polinomial = polinomial.concat(" "+ a0);
          }else{
            polinomial = polinomial.concat(" + " + a0);
          }
        }
        System.out.println(polinomial);
        System.out.println("Result: " + result);
        long end = System.currentTimeMillis(); 

        System.out.println("The request took " + Long.toString(end - start) + " milliseconds.");

        System.out.println("Press 1 to quit or other number to continue: ");
        if (scan.nextInt() == 1) {
          quit = 1;
        }
      }
      sock.close();
  }
  private static byte checksum(byte[] data){
    short sum = 256;
    for (int i = 0; i < data.length; i++){
      sum = (short) (sum - data[i]);
    }
    return (byte) (sum);
  }

}
