package pl.gd.itstartup.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ITStartupRMIClientInterface extends Remote {
    void refresh() throws RemoteException;

    String getPlayerName() throws RemoteException;
}
