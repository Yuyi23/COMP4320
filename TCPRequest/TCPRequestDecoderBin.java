import java.io.*;  // for ByteArrayInputStream
import java.net.*; // for DatagramPacket

public class TCPRequestDecoderBin implements TCPRequestDecoder, TCPRequestBinConst {

  private String encoding;  // Character encoding

  public TCPRequestDecoderBin() {
    encoding = DEFAULT_ENCODING;
  }

  public TCPRequestDecoderBin(String encoding) {
    this.encoding = encoding;
  }

  public TCPRequest decode(InputStream wire) throws IOException {
    DataInputStream src = new DataInputStream(wire);
    byte tml = src.readByte();
    short request_id = src.readShort();
    byte x = src.readByte();
    byte a4 = src.readByte();
    byte a3 = src.readByte();
    byte a2 = src.readByte();
    byte a1 = src.readByte();
    byte a0 = src.readByte();
    byte checksum = src.readByte();
    return new TCPRequest(tml,request_id,x,a4,a3,a2,a1,a0,checksum);
  }

  public TCPRequest decode(DatagramPacket p) throws IOException {
    ByteArrayInputStream payload =
      new ByteArrayInputStream(p.getData(), p.getOffset(), p.getLength());
    return decode(payload);
  }
}
