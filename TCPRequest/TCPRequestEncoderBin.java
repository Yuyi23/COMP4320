import java.io.*;  // for ByteArrayOutputStream and DataOutputStream

public class TCPRequestEncoderBin implements TCPRequestEncoder, TCPRequestBinConst {

  private String encoding;  // Character encoding

  public TCPRequestEncoderBin() {
    encoding = DEFAULT_ENCODING;
  }

  public TCPRequestEncoderBin(String encoding) {
    this.encoding = encoding;
  }

  public byte[] encode(TCPRequest tcprequest) throws Exception {

    ByteArrayOutputStream buf = new ByteArrayOutputStream();
    DataOutputStream out = new DataOutputStream(buf);
    out.writeByte(tcprequest.tml);
    out.writeShort(tcprequest.request_id);
    out.writeByte(tcprequest.x);
    out.writeByte(tcprequest.a4);
    out.writeByte(tcprequest.a3);
    out.writeByte(tcprequest.a2);
    out.writeByte(tcprequest.a1);
    out.writeByte(tcprequest.a0);
    out.writeByte(tcprequest.checksum);
    out.flush();
    return buf.toByteArray();
  }
}
