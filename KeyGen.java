/*
 * Brian Chase
 *
 * Found code to get Volume Serial Number and MAC Address at:
 * https://www.mkyong.com/java/how-to-get-mac-address-in-java/
 *
 */
package keygen;

/*
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 *
 * @author bchas
 */
public class KeyGen
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException
    {
        /*
        // One potential for getting the MAC address
        // Could be useful...
        // But it returns the wrong address (one associated with the vmware software
        InetAddress ip;
        try {
            ip = InetAddress.getLocalHost();
            System.out.println ("Current IP Address : " + ip.getHostAddress());
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            byte[] mac = network.getHardwareAddress();
            System.out.print ("Current MAC Address : ");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format ("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
            System.out.println (sb.toString());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        */
        
        // Password 1
        String pass1 = "ABCDEF123456";
        System.out.print ("Password 1 : " + pass1 + " + 5 = ");
        for (int i=0; i<pass1.length(); i++) 
            System.out.print ((char)(pass1.charAt(i) + 5));
        System.out.println();

        // Password 2
        String pass2 = "ARCHIEMILLER";
        System.out.print ("Passowrd 2 : " + pass2 + " - i = ");
        for (int i=0; i<pass2.length(); i++)
            System.out.print ((char)(pass2.charAt(i) - i));
        System.out.println();
        
        // Password 3
        String pass3 = "GO FLYERS!!!";
        System.out.print ("Password 3 : " + pass3 + " ^ i = ");
        for (int i=0; i<pass3.length(); i++)
            System.out.print ((char)(pass3.charAt(i) ^ i));
        System.out.println();
        
        // Password 4
        String volSerNo = "cmd /c vol c:";  // Gets volume serial number
        Process p = Runtime.getRuntime().exec(volSerNo);
        BufferedReader in1 = new BufferedReader (new InputStreamReader(p.getInputStream()));
        
        String line = null;
        String serial = null;
        while ((line = in1.readLine()) != null) {
            if (line.toLowerCase().contains("serial number")) {
                String[] strings = line.split(" ");
                serial = strings[strings.length-1];
            }
        }
        System.out.print ("Password 4 : Vol Serial Num " + serial + " to int = ");
        String serialNoDash = serial.substring(0, 4).concat(serial.substring(5, 9));
        
        int result = Integer.parseUnsignedInt(serialNoDash, 16);
        System.out.println (result);
        in1.close();

        // Password 5
        String command = "ipconfig /all";
        Process q = Runtime.getRuntime().exec(command);
        
        BufferedReader inn = new BufferedReader (new InputStreamReader(q.getInputStream()));
        Pattern pattern2 = Pattern.compile(".*Physical Addres.*: (.*)");
        
        while (true) {
            line = inn.readLine();
            if (line == null)
                break;
            Matcher mm2 = pattern2.matcher(line);
            if (mm2.matches()) {
                System.out.print("Password 5 : MAC Address " + mm2.group(1));
                String macAddr = mm2.group(1);
                String pass5 = macAddr.substring (9, 11);
                pass5 += macAddr.substring (6, 8);
                pass5 += macAddr.substring(3, 5);
                pass5 += macAddr.substring(0, 2);
                System.out.println (" convoluted = " + pass5);
                break;
            }
        }
    }
}
