package pl.gd.itstartup.rmi;

import pl.gd.itstartup.core.Game;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ITStartupRMIServerInterface extends Remote {
    Game getGame() throws RemoteException;

    void addClient(ITStartupRMIClientInterface client) throws RemoteException;

    void refresh(Game game) throws RemoteException;
}
