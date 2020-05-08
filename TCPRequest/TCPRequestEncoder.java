public interface TCPRequestEncoder {
  byte[] encode(TCPRequest tcprequest) throws Exception;
}
