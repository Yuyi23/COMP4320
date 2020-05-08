import java.io.*;   // for InputStream and IOException
import java.net.*;  // for DatagramPacket

public interface TCPRequestDecoder {
  TCPRequest decode(InputStream source) throws IOException;
  TCPRequest decode(DatagramPacket packet) throws IOException;
}
