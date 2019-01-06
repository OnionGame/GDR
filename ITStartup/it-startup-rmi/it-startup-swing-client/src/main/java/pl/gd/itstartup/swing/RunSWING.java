package pl.gd.itstartup.swing;

import pl.gd.itstartup.core.Game;
import pl.gd.itstartup.core.Player;
import pl.gd.itstartup.rmi.ITStartupRMIClientImpl;
import pl.gd.itstartup.rmi.ITStartupRMIClientInterface;
import pl.gd.itstartup.rmi.ITStartupRMIServerInterface;

import java.awt.*;
import java.rmi.Naming;
import java.util.Random;


public class RunSWING {
    public static void main(String a[]) {

        try {
            ITStartupRMIServerInterface server = (ITStartupRMIServerInterface) Naming.lookup("rmi://localhost/itstartup");

            MainFrame mainFrame = new MainFrame("Grzesio" + new Random().nextInt(), server);
            ITStartupRMIClientInterface client = new ITStartupRMIClientImpl(mainFrame);
            server.addClient(client);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
