package direstruts.xmeta1;

import java.rmi.*;

public interface Hello_C_I extends Remote{
    public void print_on_client(String id, String s) throws java.rmi.RemoteException;
    public void print_all_client(String msg) throws java.rmi.RemoteException;
}