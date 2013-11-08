
import java.rmi.Remote;

public interface ExecuteCommands extends java.rmi.Remote {
    public boolean addPessoa(String username, String password) throws java.rmi.RemoteException;
    public boolean addTopic(String topicName) throws java.rmi.RemoteException;
    public int hasUSer(String username, String password) throws java.rmi.RemoteException;
    public Mensagem retrieveTopicos() throws java.rmi.RemoteException;
    public boolean addNewIdeia(String type, String ideiaText, String upId, String topicos, int person, int startShares, int value, String dataString, NamedByteArray file) throws java.rmi.RemoteException;
    public Mensagem retrieveIdeias(int topicoID) throws java.rmi.RemoteException;
    public Mensagem retrieveAttributes(int userID, int ideiaID) throws java.rmi.RemoteException;
    public boolean pendingTransaction(int idVende, int idCompra, int idIdeia, int sharesToBuy, int newPrice, int precoCompra, String dataString) throws java.rmi.RemoteException;
    public Mensagem retrieveIdeiasUser(int userID) throws java.rmi.RemoteException;
    public Mensagem retrieveUserData(int userID) throws java.rmi.RemoteException;
    public void changeSharePrice(int pessoaID, int ideiaID, int newPrice) throws java.rmi.RemoteException;
    public boolean removeIdeia(int pessoaID, int ideiaID) throws java.rmi.RemoteException;
    public Mensagem retrieveIdeiasTopico(int topicoID) throws java.rmi.RemoteException;
    public Mensagem retrieveAnexos(int id_ideia, String path) throws java.rmi.RemoteException;
    public void insereUserDataOffline(int userID, int userComprador) throws java.rmi.RemoteException;
    public String popUserDataOffline(int userID) throws java.rmi.RemoteException;
    public boolean addPendingTransaction(int userID, int ideiaID, int nr_shares, int newPrice, int maxPrice, String dataString) throws java.rmi.RemoteException;
    public boolean verifyExecutePendingTransaction(int ideiaID) throws java.rmi.RemoteException;
}