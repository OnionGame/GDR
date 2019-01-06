package pl.gd.itstartup.rmi;

import pl.gd.itstartup.core.Game;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ITStartupRMIServerServerImpl extends UnicastRemoteObject implements ITStartupRMIServerInterface {

    private Game game;
    private List<ITStartupRMIClientInterface> clients = new ArrayList<>();

    protected ITStartupRMIServerServerImpl() throws RemoteException {
        game = new Game();
    }

    public Game getGame() {
        return game;
    }

    @Override
    public void addClient(ITStartupRMIClientInterface client) {
        try {
            clients.add(client);
            game.addPlayer(client.getPlayerName());
            refresh(game);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void refresh(Game game) throws RemoteException {
        this.game = game;
        for (ITStartupRMIClientInterface client : clients) {
            client.refresh();
        }

    }

    public static void main(String[] args) {
        try {
            //System.setSecurityManager(new RMISecurityManager());
            LocateRegistry.createRegistry(1099);
            ITStartupRMIServerServerImpl b = new ITStartupRMIServerServerImpl();
            Naming.rebind("rmi://localhost/itstartup", b);
            System.out.println(" Server is started.");
        } catch (Exception e) {
            System.out.println("Chat Server failed: " + e);
        }
    }
}
