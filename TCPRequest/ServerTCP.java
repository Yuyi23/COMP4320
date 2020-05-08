import java.io.*;   // for Input/OutputStream
import java.net.*;  // for Socket and ServerSocket
import java.util.Arrays;

public class ServerTCP {

  public static void main(String args[]) throws Exception {

    if (args.length != 1 && args.length != 2)  // Test for correct # of args
      throw new IllegalArgumentException("Parameter(s): <Port>");

    int port = Integer.parseInt(args[0]);    // Receiving Port
	  System.out.println("Server is acitive");
    ServerSocket servSock = new ServerSocket(port);
    Socket clntSock = servSock.accept();
    
    while(true){
      byte tml = 9;
      byte err = 0;
      int result =0;
      byte[] reply;
      byte checksum = 1;

      try{
        TCPRequestDecoder decoder = (args.length == 2 ?   // Which encoding              
          new TCPRequestDecoderBin(args[1]) : new TCPRequestDecoderBin());
        TCPRequest receivedRequest = decoder.decode(clntSock.getInputStream());
           
        int request_id = receivedRequest.request_id;
        byte x = receivedRequest.x;
        byte a4 = receivedRequest.a4;
        byte a3 = receivedRequest.a3;
        byte a2 = receivedRequest.a2;
        byte a1 = receivedRequest.a1;
        byte a0 = receivedRequest.a0;
        result = a4 * x * x * x * x + a3 * x * x * x + a2 * x * x + a1 * x + a0;
        ByteArrayOutputStream buf = new ByteArrayOutputStream(36);
        DataOutputStream out = new DataOutputStream(buf);
        out.writeByte(tml);
        out.writeShort(request_id);
        out.writeByte(err);
        out.writeInt(result);
        out.writeByte(checksum);
        out.flush();
        reply = buf.toByteArray();
        reply[8] = checksum(reply);
        OutputStream out_reply = clntSock.getOutputStream();
        out_reply.write(reply);
        System.out.println("\nServer Respond");
        for (byte b : reply) {
          String st = String.format("%02X", b);
          System.out.print(st + " ");
        }
        System.out.print("\n");
      }catch(Exception e){
        break;
      } 

    }
    
    clntSock.close();
    servSock.close();
  }
  private static byte checksum(byte[] data){
    short sum = 256;
    for (int i = 0; i < data.length; i++){
      sum = (short) (sum - data[i]);
    }
    return (byte) (sum);
  }
}
