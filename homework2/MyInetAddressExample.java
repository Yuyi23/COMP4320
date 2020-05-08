import java.net.*;  // for InetAddress
import java.util.Scanner; // for prompt

public class MyInetAddressExample {

    public static void main(String[] args) {

        // Get name and IP address of the local host
        try {
            InetAddress address = InetAddress.getLocalHost();
            System.out.println("Local Host:");
            System.out.println("\t" + address.getHostName());
            System.out.println("\t" + address.getHostAddress());
        } catch (UnknownHostException e) {
            System.out.println("Unable to determine this host's address");
        }

        System.out.print("\nEnter a hostname: ");
        Scanner sc = new Scanner(System.in);
        String addressInput = sc.next();

            // Get name(s)/address(es) of hosts given on command-line
            try {
                InetAddress addressList = InetAddress.getByName(addressInput);
                System.out.println(addressInput + ":");
                // Print the first name.  Assume array contains at least one entry.
                System.out.println("\t" + addressList.getHostName());

                String[] addressInputArray = addressList.getHostAddress().split("\\.");
                System.out.print("\tBinary format: ");
                for (int i = 0; i < addressInputArray.length; i++) {
                    int hostAddress = Integer.parseInt(addressInputArray[i]);
                    String binaryAddress = Integer.toBinaryString(hostAddress);
                    System.out.print(binaryAddress);
                }
                System.out.print("\n\tBinary dotted-quad format: ");
                for (int i = 0; i < addressInputArray.length; i++) {
                    int hostAddress = Integer.parseInt(addressInputArray[i]);
                    String binaryAddress = Integer.toBinaryString(hostAddress);
                    if (i < addressInputArray.length -1) {
                        System.out.print(binaryAddress + ".");
                    }
                    else{
                        System.out.print(binaryAddress);
                    }
                }

                System.out.println("\n\tDecimal dotted-quad format: " + addressList.getHostAddress());


            } catch (UnknownHostException e) {
                System.out.println("Unable to find address for " + addressInput);
            }

    }
}