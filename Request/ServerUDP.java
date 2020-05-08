import java.net.*;  // for DatagramSocket and DatagramPacket
import java.io.*;   // for IOException
import java.util.Arrays;

public class ServerUDP {

  public static void main(String[] args) throws Exception {

      if (args.length != 1 && args.length != 2)  // Test for correct # of args        
	     throw new IllegalArgumentException("Parameter(s): <Port> [<encoding>]");
      
      int port = Integer.parseInt(args[0]);   // Receiving Port
      
      DatagramSocket sock = new DatagramSocket(port);  // UDP socket for receiving      
      DatagramPacket packet = new DatagramPacket(new byte[1024],1024);
      System.out.println("Server is acitive");
      while(true){
        sock.receive(packet);
        byte tml = 9;
        byte err = 0;
        if (packet.getLength() != 10){
          err = 127;
        }
        int result =0;
        byte[] reply;
        byte checksum = 1;
      // Receive binary-encoded friend                              
      // FriendDecoder decoder = new FriendDecoderBin();
        RequestDecoder decoder = (args.length == 2 ?   // Which encoding              
          new RequestDecoderBin(args[1]) : new RequestDecoderBin());
        System.out.println(packet);
        Request receivedRequest = decoder.decode(packet);
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
        DatagramPacket next_answer = new DatagramPacket(reply, reply.length, packet.getAddress(), packet.getPort());
        sock.send(next_answer);

        System.out.println("\nServer Respond");
        for (byte b : reply) {
                  String st = String.format("%02X", b);
                  System.out.print(st + " ");
        }
      }   
  }
  private static byte checksum(byte[] data){
    short sum = 256;
    for (int i = 0; i < data.length; i++){
      sum = (short) (sum - data[i]);
    }
    return (byte) (sum);
  }
}
