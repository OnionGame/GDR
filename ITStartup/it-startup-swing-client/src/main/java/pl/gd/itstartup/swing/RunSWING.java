package pl.gd.itstartup.swing;

import pl.gd.itstartup.rmi.ITStartupRMIClientImpl;
import pl.gd.itstartup.rmi.ITStartupRMIClientInterface;
import pl.gd.itstartup.rmi.ITStartupRMIServerInterface;

import javax.swing.*;
import java.rmi.Naming;
import java.util.Random;


public class RunSWING {
    public static void main(String a[]) {
        MainFrame mainFrame = null;
        try {
            ITStartupRMIServerInterface server = (ITStartupRMIServerInterface) Naming.lookup("rmi://localhost/itstartup");

            mainFrame = new MainFrame("Grzesio" + new Random().nextInt(), server);
            ITStartupRMIClientInterface client = new ITStartupRMIClientImpl(mainFrame);
            server.addClient(client);
        } catch (Exception e) {
            e.printStackTrace();
            if (mainFrame != null) {
                JOptionPane.showMessageDialog(mainFrame, e, "", JOptionPane.ERROR_MESSAGE);
            }
        }


    }

}
