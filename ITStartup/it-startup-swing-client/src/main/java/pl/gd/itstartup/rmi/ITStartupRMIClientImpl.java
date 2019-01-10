package pl.gd.itstartup.rmi;

import pl.gd.itstartup.swing.MainFrame;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ITStartupRMIClientImpl extends UnicastRemoteObject implements pl.gd.itstartup.rmi.ITStartupRMIClientInterface {

    MainFrame frame;

    public ITStartupRMIClientImpl(MainFrame frame) throws RemoteException {
        super();
        this.frame = frame;
    }

    @Override
    public void refresh() throws RemoteException {
        frame.refresh();
    }

    @Override
    public String getPlayerName() throws RemoteException {
        return frame.getPlayerName();
    }
}
