public class Request {
  public byte tml;
  public short request_id;
  public byte x;
  public byte a4;
  public byte a3;
  public byte a2;
  public byte a1;
  public byte a0;
  public byte checksum; 
    

  public Request(byte tml,short request_id,byte x,byte a4,byte a3,byte a2,byte a1,byte a0,byte checksum)  {
      this.tml = tml;
      this.request_id = request_id;
      this.x = x;
      this.a4 = a4;
      this.a3 = a3;
      this.a2 = a2;
      this.a1 = a1;
      this.a0 = a0;
      this.checksum = checksum;
  }

  public String toString() {
    final String EOLN = java.lang.System.getProperty("line.separator");
    String value = "Total message length = " + tml + EOLN +
                   "Request ID = " + request_id + EOLN +
                   "Checksum = " + checksum + EOLN;
    return value;
  }
}
