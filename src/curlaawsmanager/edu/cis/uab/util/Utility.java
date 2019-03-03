package curlaawsmanager.edu.cis.uab.util;

import com.google.common.net.InternetDomainName;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JTable;

public class Utility {

    public static String getDomainName(String url) throws URISyntaxException {

        if (url.startsWith("http")) {
//			URI uri = new URI(url);
//			String domain = uri.getHost();
//                        if(domain != null) // not a regular domain structure
//                            return domain.startsWith("www.") ? domain.substring(4) : domain;
//                        else
//                        { //handle exceptional domain
            String arr[] = url.split("/");
            System.out.println("top:" + InternetDomainName.from(arr[2]).topPrivateDomain().toString());
            return InternetDomainName.from(arr[2]).toString();
            //}
        } else {
            String[] arr = url.split("/");
            return arr[0];
        }

    }

    public static String getSelectedValueFromTable(ArrayListTableModel tableModel, JTable table, int column) {
        try {
            String value = (String) tableModel.getValueAt(table.getSelectedRow(), column);
            return value;
        } catch (ArrayIndexOutOfBoundsException ex) {
            ZLogger.e("No row is selected!!");
            return null;
        }
    }

    public static void runRemotScript(ProcessBuilder pb) throws Exception
    {
            Process proc = pb.start();
            proc.waitFor();
        
    }
    public static void main(String[] args) {
        try {
            //String url = "http://mark.carbonetta_678.doctorrhcy.ru/?91f91a16A4DDc7aA67ea9";//"http://luizaokubo.doctorrhcy.ru/?c98Ff0e9F470B94e57970D3a9";
            String url = "http://www.sqhrxq.com/cc4e7e47e4d32faea2e7787dbf8f81da111fe54e3b620fc1b94337dc89d474bd1cfdc2a636ed2e0a7bddacf389d6609b";

            System.out.println("d:" + Utility.getDomainName(url));
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
    }

    
}
