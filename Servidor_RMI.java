import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.naming.NamingException;

public class Servidor_RMI extends UnicastRemoteObject implements ExecuteCommands {
    private static int PORT_RMI = 1099;
    private String HOST_DB = "localhost";
    private String user = "sd";
    private String password = "sd";
    private Connection DBConn = null;
    private Statement statement = null;
    private final static DateFormat dateFormat =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

    Servidor_RMI() throws RemoteException, SQLException {
        InputStreamReader isr = new InputStreamReader( System.in );
        BufferedReader stdin = new BufferedReader( isr );
        try {
            System.out.print( "Introduza o Porto do RMI: " );
            PORT_RMI = tryParse(stdin.readLine());
            System.out.print( "Introduza o IP da Base de Dados: " );
            HOST_DB = stdin.readLine();
            System.out.print( "Introduza o nome do Utilizador da Base de Dados: " );
            user = stdin.readLine();
            System.out.print( "Introduza a senha do Utilizador da Base de Dados: " );
            password = stdin.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\nPorto RMI: " + PORT_RMI + "\nBase de Dados: " + "\n\tIP: " + HOST_DB + "\n\tUser: " + user + "\n\tPassword: " + password + "\n");

        getConnection();
        if (DBConn != null) {
            System.out.println("You made it, take control your database now!");

            try {
                statement = DBConn.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConn.setAutoCommit(false);
        }
        else {
            System.out.println("Failed to make connection!");
        }
    }

    private Integer tryParse(String text) {
        try {
            return new Integer(text);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static void main(String args[]) throws NamingException, RemoteException {
        try {
            Servidor_RMI RMIServer = new Servidor_RMI();
            LocateRegistry.createRegistry(PORT_RMI).rebind("ServerRMI", RMIServer);
        } catch (SQLException e) {System.err.println("Connection Failed! Check output console");
        } catch (RemoteException re) {System.err.println("Exception in HelloImpl.main: " + re);
        }
    }

    public void getConnection() {
        System.out.println("-------- Oracle JDBC Connection Testing ------");
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your Oracle JDBC Driver?");
            e.printStackTrace();
            return;
        }
        System.out.println("Oracle JDBC Driver Registered!");
        try {
            DBConn = DriverManager.getConnection("jdbc:oracle:thin:@"+HOST_DB+":1521:xe", user,password);
        } catch (SQLException e) {
            System.err.println("Connection Failed! Check output console: " + e);
            e.printStackTrace();
            return;
        }
    }

    public void RollBack() {
        try{DBConn.rollback();
        }   catch(SQLException e) {
            System.err.println("Connection Failed Rolling Back! Check output console: " + e);}
    }

    public boolean addTransactionDescription(int userID, String description, String dataString) {
        String addDescritpion = "INSERT INTO TRANSACCOES VALUES "
                + "("+ userID + ",'"+ description + "','" + dataString +"')";
        try {
            statement.executeUpdate(addDescritpion);
            return true;
        }catch (SQLException e) {
            System.err.println("Connection Failed add Transaction to User. Check output console " + e);
            return false;
        }
    }

    public Mensagem retrieveTopicos() throws RemoteException {
        String topicos = "SELECT * FROM TOPICO";
        Mensagem mTopicos = new Mensagem(4,0);
        try {
            ResultSet rs = statement.executeQuery(topicos);
            while(rs.next()) {
                String ID = String.valueOf(rs.getInt(1));
                String topicoName = rs.getString(2);
                mTopicos.addList(ID);
                mTopicos.addList(topicoName);
            }
        }catch (SQLException e) {
            System.err.println("Connection Failed Retrieving Topics. Check output console " + e);
        }
        return mTopicos;
    }

    public Mensagem retrieveAttributes(int userID, int ideiaID) throws RemoteException {
        String attributes = "SELECT IDUSER, PRICE, NR_SHARE FROM USER_SHARE " +
                "WHERE IDIDEIA = " + ideiaID + " AND IDUSER != " + userID;
        Mensagem mIdeias = new Mensagem(8, ideiaID);
        try {
            ResultSet rs = statement.executeQuery(attributes);
            while(rs.next()) {
                String IDuser = String.valueOf(rs.getInt(1));
                String price = String.valueOf(rs.getString(2));
                String numShares = String.valueOf(rs.getString(3));
                mIdeias.addList(IDuser);
                mIdeias.addList(price);
                mIdeias.addList(numShares);
            }
        }catch (SQLException e) {
            System.err.println("Connection Failed Retrieving User_Shares. Check output console " + e);
        }
        return mIdeias;
    }

    public Mensagem retrieveUserData(int userID) throws RemoteException {
        Mensagem m = new Mensagem(10, userID);
        String userData = "SELECT DINHEIRO FROM UTILIZADOR "
                + "WHERE IDUSER = " + userID;
        String userHistorico = "SELECT DESCRIPTION, DATA FROM TRANSACCOES "
                + "WHERE IDUSER = " + userID;
        try {
            ResultSet rs1 = statement.executeQuery(userData);
            rs1.next();
            m.addList(String.valueOf(rs1.getInt(1)));
            ResultSet rs2 = statement.executeQuery(userHistorico);
            while(rs2.next()) {
                m.addList(rs2.getString(2));
                m.addList(rs2.getString(1) + ";\n");
            }
        } catch(SQLException e){System.err.println("Failed to retrieve data From user: " + e);}
        return m;
    }

    public Mensagem retrieveIdeiasUser(int userID) throws RemoteException {
        String ideias = "select ideia.idideia, ideia.mensagem, ideia.opiniao, ideia.total_share, USER_SHARE.price, "
                + "USER_SHARE.nr_share  from ideia, USER_SHARE "
                + "where USER_SHARE.idideia = ideia.idideia AND USER_SHARE.IDUSER = " + userID;
        Mensagem mIdeias = new Mensagem(11, 0);
        String ideiaID, mensagem, opiniao, total_share, price, nr_share, topicosCumulativos;
        try {
            Statement statement2 = DBConn.createStatement();
            ResultSet rsIdeia = statement.executeQuery(ideias);
            while(rsIdeia.next()){
                topicosCumulativos = "";
                ideiaID = String.valueOf(rsIdeia.getInt(1));
                mensagem = rsIdeia.getString(2);
                opiniao = rsIdeia.getString(3);
                total_share = String.valueOf(rsIdeia.getInt(4));
                price = String.valueOf(rsIdeia.getInt(5));
                nr_share = String.valueOf(rsIdeia.getInt(6));
                String topicos = "select idtopico from topico_ideia where idideia = " + ideiaID;
                ResultSet rsTopico = statement2.executeQuery(topicos);
                while(rsTopico.next()) {
                    String idTopico = String.valueOf(rsTopico.getInt(1));
                    topicosCumulativos += idTopico + "\n";
                }
                mIdeias.addList(ideiaID);
                addRespostaMensagem(mIdeias, ideiaID, mensagem);
                mIdeias.addList(opiniao);
                mIdeias.addList(total_share);
                mIdeias.addList(price);
                mIdeias.addList(nr_share);
                mIdeias.addList(topicosCumulativos);
            }
        }catch (SQLException e) {
            System.err.println("Connection Failed Retrieving Ideas. Check output console " + e);
        }
        return mIdeias;
    }

    public Mensagem retrieveIdeias(int topicoID) throws RemoteException {
        String ideias = "SELECT IDEIA.IDIDEIA, IDEIA.MENSAGEM, IDEIA.OPINIAO FROM TOPICO_IDEIA, IDEIA " +
                "WHERE TOPICO_IDEIA.IDIDEIA = IDEIA.IDIDEIA AND TOPICO_IDEIA.IDTOPICO = " + topicoID;
        Mensagem mIdeias = new Mensagem(5,0);
        try {
            ResultSet rs = statement.executeQuery(ideias);
            while(rs.next()) {
                String ideiaID = String.valueOf(rs.getInt(1));
                String ideiaMensagem = rs.getString(2);
                String ideiaOpiniao = rs.getString(3);
                mIdeias.addList(ideiaID);
                addRespostaMensagem(mIdeias, ideiaID, ideiaMensagem);
                mIdeias.addList(ideiaOpiniao);
            }
        }catch (SQLException e) {
            System.err.println("Connection Failed Retrieving Ideas. Check output console " + e);
        }
        return mIdeias;
    }

    public String existeTopicID(int topicoID) {
        String topicoNome = "SELECT NOME FROM TOPICO " +
                "WHERE IDTOPICO =  " + topicoID;
        try {
            ResultSet rs = statement.executeQuery(topicoNome);
            if(rs.next()) return rs.getString(1);
        }catch (SQLException e) {
            System.err.println("Connection Failed Retrieving Ideas. Check output console " + e);}
        return "";
    }

    public Mensagem retrieveIdeiasTopico(int topicoID) {
        String ideias = "SELECT IDEIA.IDIDEIA, IDEIA.MENSAGEM, IDEIA.OPINIAO FROM TOPICO_IDEIA, IDEIA " +
                "WHERE TOPICO_IDEIA.IDIDEIA = IDEIA.IDIDEIA AND TOPICO_IDEIA.IDTOPICO = " + topicoID;
        Mensagem mIdeias = new Mensagem(13, 0);
        String nome = existeTopicID(topicoID);
        mIdeias.addList(nome);
        if(!existeTopicID(topicoID).isEmpty()){
            try {
                ResultSet rs = statement.executeQuery(ideias);
                while(rs.next()) {
                    String ideiaID = String.valueOf(rs.getInt(1));
                    String ideiaMensagem = rs.getString(2);
                    String ideiaOpiniao = rs.getString(3);
                    mIdeias.addList(ideiaID);
                    addRespostaMensagem(mIdeias, ideiaID, ideiaMensagem);
                    mIdeias.addList(ideiaOpiniao);
                }
            }catch (SQLException e) {
                System.err.println("Connection Failed Retrieving Ideas. Check output console " + e);
            }
        }
        return mIdeias;
    }

    public void addRespostaMensagem(Mensagem m, String ideiaID, String mensagem) {
        int IDideia = Integer.parseInt(ideiaID);
        String ideiasUp = "SELECT IDEIA_IDEIA.IDIDEIA, MENSAGEM, OPINIAO  FROM IDEIA_IDEIA, IDEIA " +
                "WHERE IDIDEIADOWN = " + ideiaID + " AND IDEIA_IDEIA.IDIDEIA IS NOT NULL AND IDEIA_IDEIA.IDIDEIA = IDEIA.IDIDEIA";
        String texto = "Em resposta a: ";
        try{
            Statement statement2 = DBConn.createStatement();
            ResultSet rs = statement2.executeQuery(ideiasUp);
            while(rs.next()) {
                texto += "\n\tID: " + String.valueOf(rs.getInt(1))+"\n\tOpinião: "+ rs.getString(3) + "\n\tMensagem:\n\t\t"+ rs.getString(2) + "\n";
            }
            texto += "\n\nMensagem:\n\n\t"+ mensagem + "\n\n\n";
            m.addList(texto);
        }catch (SQLException e) {
            System.err.println("Connection Failed adding Ideias to Resposta. Check output console " + e);}
    }

    public synchronized int incrementAttributeID() {
        String updateAttributeID = "UPDATE CHAVES SET CUSER_SHARE = CUSER_SHARE + 1";
        String getAttributeID = "SELECT CUSER_SHARE FROM CHAVES";
        try {
            ResultSet rs = statement.executeQuery(getAttributeID);
            rs.next();
            int id = rs.getInt(1);
            statement.executeUpdate(updateAttributeID);
            return id;
        }catch (SQLException e) {
            System.err.println("Connection Failed incrementing CUSER_SHARE. Check output console" + e);
            return 0;
        }
    }

    public synchronized int incrementUserID() {
        String updateUserID = "UPDATE CHAVES SET CUTILIZADOR = CUTILIZADOR + 1";
        String getUserID = "SELECT CUTILIZADOR FROM CHAVES";
        try {
            ResultSet rs = statement.executeQuery(getUserID);
            rs.next();
            int id = rs.getInt(1);
            statement.executeUpdate(updateUserID);
            return id;
        }catch (SQLException e) {
            System.err.println("Connection Failed incrementing CUSER. Check output console " + e);
        }
        return 0;
    }
    public synchronized int incrementIdeiaID() {
        String updateIdeiaID = "UPDATE CHAVES SET CIDEIA = CIDEIA + 1";
        String getIdeiaID = "SELECT CIDEIA FROM CHAVES";
        try {
            ResultSet rs = statement.executeQuery(getIdeiaID);
            rs.next();
            int id = rs.getInt(1);
            statement.executeUpdate(updateIdeiaID);
            return id;
        }catch (SQLException e) {
            System.err.println("Connection Failed incrementing CIDEIA. Check output console " + e);
        }
        return 0;
    }
    public synchronized int incrementTopicoID() {
        String updateTopicoID = "UPDATE CHAVES SET CTOPICO = CTOPICO + " + 1;
        String getTopicoID = "SELECT CTOPICO FROM CHAVES";
        try {
            ResultSet rs = statement.executeQuery(getTopicoID);
            rs.next();
            int id = rs.getInt(1);
            statement.executeUpdate(updateTopicoID);
            return id;
        }catch (SQLException e) {
            System.err.println("Connection Failed incrementing CTOPICO. Check output console" + e);
            return 0;
        }
    }

    public boolean addPessoa(String username, String password) throws RemoteException {
        if(!hasPessoa(username)){
            addNewPessoa(username,password);
            System.out.println("Adicionado User com o nome " + username + ".");
            try{
                DBConn.commit();
                return true;
            } catch (SQLException e) {
                System.err.println("Connection Failed commiting addPessoa. Check output console" + e);}
        }
        else{
            System.out.println("Já existe um User com o nome " + username + ".");
            return false;
        }
        return true;
    }

    public boolean addNewPessoa(String username, String password) {
        int userID = incrementUserID();
        String insertUser = "INSERT INTO UTILIZADOR VALUES " + "(" + userID + ",'" + username + "','" + password+ "'," + 10000 +")";
        try {
            statement.executeUpdate(insertUser);
            return true;
        }catch (SQLException e) {
            System.err.println("Connection Failed creating Person! Check output console " + e );
            RollBack();
        }
        return false;
    }

    public int hasUSer(String username, String password) throws RemoteException {
        String test = "SELECT * FROM UTILIZADOR WHERE USERNAME = '" + username + "' and PASSWORD = '" + password + "'";
        try {
            ResultSet rs = statement.executeQuery(test);
            if(rs.next())
                return rs.getInt(1);
        }catch (SQLException e) {
            System.err.println("Connection Failed verifying User! Check output console " + e);
        }
        return 0;
    }

    public boolean hasPessoa(String username) {
        boolean cond = false;
        String test = "SELECT * FROM UTILIZADOR WHERE USERNAME = '" + username + "'";
        try {
            ResultSet rs = statement.executeQuery(test);
            if(rs.next()){
                //System.out.println(rs.getString(1)+" " + rs.getString(2));
                cond = true;
            }
        }catch (SQLException e) {
            System.out.println("User: " + username + " não encontrado.");
        }
        return cond;
    }

    public boolean addTopic(String topicName) throws RemoteException {
        if(hasTopic(topicName)){
            addNewTopic(topicName);
            System.out.println("Adicionado tópico com o nome " + topicName + ".");
            try{
                DBConn.commit();
            } catch (SQLException e) {
                System.err.println("Connection Failed commiting addPessoa. Check output console" + e);}
            return true;
        }
        else
            System.out.println("Já existe um tópico com o nome " + topicName + ".");
        return false;
    }

    public boolean addNewTopic(String topicName) {
        int topicoID = this.incrementTopicoID();
        String insertTopic = "INSERT INTO TOPICO VALUES " + "(" + topicoID + ",'" + topicName + "')";
        try{
            statement.executeUpdate(insertTopic);
            return true;
        }catch (SQLException e) {
            System.err.println("Connection Failed creating Topic! Check output console " + e);
            RollBack();
            return false;
        }
    }

    public boolean hasTopic(String topicName) {
        boolean cond = false;
        String test = "SELECT * FROM TOPICO WHERE NOME = '" + topicName + "'";
        try {
            ResultSet rs = statement.executeQuery(test);
            if(!rs.next())
                cond = true;
        }catch (SQLException e) {
            System.err.println("Connection Failed verifying TOPICO! Check output console " + e);
        }
        return cond;
    }

    public boolean verifyIdeiaMensagem(String mensagem) {
        String ideiasMensagem = "SELECT IDIDEIA FROM IDEIA "
                + "WHERE MENSAGEM = '" + mensagem + "'";
        try{
            ResultSet rs = statement.executeQuery(ideiasMensagem);
            if(rs.next()) return true;
        } catch(SQLException e) {
            System.err.println("Connection Failed verifying mensagem text! Check output console " + e);
            return true;
        }
        return false;
    }

    // Faz pop do histórico de um user específico
    public String popUserDataOffline(int userID) {
        String getUserDataOffline, removeUserDataOffline, userOfflineData = "";
        if(verifyUserOfflne(userID)) {
            getUserDataOffline = "SELECT USERS_BUYERS FROM OFFLINE_USERS " +
                    "WHERE IDUSER = " + userID;
            removeUserDataOffline = "DELETE FROM OFFLINE_USERS " +
                    "WHERE IDUSER = " + userID;
            try {
                ResultSet rs = statement.executeQuery(getUserDataOffline);
                rs.next();
                userOfflineData = rs.getString(1);
            }catch (SQLException e) {System.err.println("Connection Failed retrieving userDataOffline! Check output console");}
            try {
                statement.executeUpdate(removeUserDataOffline);
                DBConn.commit();
            }catch (SQLException e) {
                System.err.println("Connection Failed removing userDataOffline! Check output console");
                RollBack();
            }
        }
        return userOfflineData;
    }

    // insere um user para notificações offline com um comprador
    public void insereUserDataOffline(int userID, int userComprador) {
        //System.out.println(userComprador);
        if(verifyUserOfflne(userID)) {
            if(!verifyUsersBuyer(userID, userComprador)) {
                String updateStringOffline =  "UPDATE OFFLINE_USERS SET USERS_BUYERS = CONCAT(USERS_BUYERS, CONCAT('|', CONCAT(" + userComprador + ",'|'))) "
                        + "WHERE IDUSER = "+  userID;
                //System.out.println(userComprador+"\n"+updateStringOffline);
                try {
                    statement.executeUpdate(updateStringOffline);
                    DBConn.commit();
                }catch (SQLException e) {
                    System.err.println("Connection Failed updating UserOffline! Check output console: " + e);
                    RollBack();
                }
            }
        }
        else {
            String insertUsersBuyer =  "INSERT INTO OFFLINE_USERS VALUES (" + userID + ", CONCAT('|', CONCAT(" + userComprador + ", '|')))";
            try {
                statement.executeUpdate(insertUsersBuyer);
                DBConn.commit();
            }catch (SQLException e) {
                System.err.println("Connection Failed Inserting UserOffline! Check output console: " + e);
                RollBack();
            }
        }
    }

    public boolean verifyUserOfflne(int userID) {
        String getUserOffline = "SELECT IDUSER FROM OFFLINE_USERS " +
                "WHERE IDUSER = " + userID;
        try {
            ResultSet rs = statement.executeQuery(getUserOffline);
            if(rs.next()) return true;
            return false;
        }catch (SQLException e) {
            System.err.println("Connection Failed verifying UserOffline! Check output console: " + e);
            return false;
        }
    }

    public boolean verifyUsersBuyer(int userID, int userComprador) {
        String verifyUsersBuyers = "SELECT IDUSER FROM OFFLINE_USERS " +
                "WHERE IDUSER = " + userID + " AND USERS_BUYERS LIKE '%|" + userComprador + "|%'";
        try {
            ResultSet rs = statement.executeQuery(verifyUsersBuyers);
            if(rs.next()) return true;
            return false;
        }catch (SQLException e) {
            System.err.println("Connection Failed verifying UserOffline! Check output console: " + e);
            return false;
        }
    }

    // Verifica se já existem tópicos com aqueles IDs
    public boolean verifyTopico(String topicos) {
        String[] partes = topicos.split(",");
        for(int i = 0; i < partes.length; ++i) {
            int idTopico = Integer.parseInt(partes[i]);
            String getTopico = "SELECT IDTOPICO FROM TOPICO "
                    + "WHERE IDTOPICO = " + idTopico;
            try {
                ResultSet rs = statement.executeQuery(getTopico);
                if(!rs.next()) return false;
            }catch (SQLException e) {
                System.err.println("Connection Failed verifing Topico Existe! Check output console");
                return false;
            }
        }
        return true;
    }

    // Verifica se já existem ideias com aqueles IDs
    public boolean verifyIdeiaUp(String ideiasUp) {
        String[] partes = ideiasUp.split(",");
        for(int i = 0; i < partes.length; ++i) {
            int idIdeia = Integer.parseInt(partes[i]);
            String getTopico = "SELECT IDIDEIA FROM IDEIA "
                    + "WHERE IDIDEIA = " + idIdeia;
            try {
                ResultSet rs = statement.executeQuery(getTopico);
                if(!rs.next()) return false;
            }catch (SQLException e) {
                System.err.println("Connection Failed verifing Ideia Existe! Check output console");
                return false;
            }
        }
        return true;
    }

    // Muda o preço dum User_Share de uma Ideia
    public void changeSharePrice(int pessoaID, int ideiaID, int newPrice) {
        if(newPrice < 0) {
            System.err.println("Preço inválido");
            return;
        }
        String updatePrice =  "UPDATE USER_SHARE SET PRICE = " + newPrice
                + " WHERE IDUSER = " + pessoaID + " AND IDIDEIA = " + ideiaID;
        try {
            statement.executeUpdate(updatePrice);
        } catch (SQLException e) {
            System.err.println("Connection Failed Changing Price To Share! Check output console " + e);
            RollBack();
        }
        try {
            DBConn.commit();
        } catch (SQLException e) {
            System.err.println("Connection Failed Commiting! Check output console " + e);
            RollBack();
        }
    }

    // Vai buscar o ID dum User_Share com o respectvo IDUSER e IDIDEIA
    public long getAttributeByPessoaIdeia(int pessoaID, int ideiaID) {
        String getAtributo = "SELECT IDUSER_SHARE FROM USER_SHARE "
                + "WHERE IDIDEIA = " +ideiaID + " AND IDUSER = " + pessoaID;
        try{
            ResultSet rs = statement.executeQuery(getAtributo);
            rs.next();
            long what = rs.getLong(1);
            return what;
        } catch (SQLException e) {System.err.println("Connection Failed Finding AttributeID! Check output console " + e);}
        return 0;
    }

    // Adiciona uma ideia
    public boolean addNewIdeia(String type, String ideiaText, String upId, String topicos, int person, int startShares, int value, String dataString, NamedByteArray file) throws RemoteException {
        int attributeID =  incrementAttributeID();
        int ideiaID =  incrementIdeiaID();
        if(verifyIdeiaMensagem(ideiaText)) {
            System.err.println("Uma ideia com a mesma mensagem já existe");
            return false;
        }
        if(!verifyTopico(topicos)) {
            System.err.println("Um dos tópicos não existe!");
            return false;
        }
        if(!upId.isEmpty() && !verifyIdeiaUp(upId)) {
            System.err.println("Uma das ideias não existe!");
            return false;
        }

        String newName = "";
        String[] partes;
        if(file != null){
            newName = file.getName();
            partes = newName.split("\\.");
            newName = ideiaID+"."+partes[partes.length-1];
            file.saveToFile(newName);
        }

        partes = topicos.split(",");
        String insertIdeia = "INSERT INTO IDEIA VALUES"
                + "(" + ideiaID + ",'" + ideiaText + "','" +
                type + "'," + startShares + ",'" + newName + "')";
        String insertUser_Share = "INSERT INTO USER_SHARE VALUES"
                + "(" + attributeID + "," + ideiaID + "," + person +
                "," + value + ",'" + startShares + "')";

        String insertIdeia_Ideia = "";
        try {
            statement.executeUpdate(insertIdeia);
            statement.executeUpdate(insertUser_Share);
        }catch (SQLException e) {
            System.err.println("Connection Failed creating idea! Check output console " + e);
            RollBack();
            return false;
        }

        for(int i = 0; i < partes.length; ++i) {
            String insertTopico_Ideia = "INSERT INTO TOPICO_IDEIA VALUES"
                    + "(" + partes[i] + "," + ideiaID + ")";
            try {
                statement.addBatch(insertTopico_Ideia);
            }catch (SQLException e) {
                System.err.println("Connection Failed Add Batch idea! Check output console " + e);
                RollBack();
                return false;
            }
        }
        try {
            statement.executeBatch();
        }catch (SQLException e) {
            System.err.println("Connection Failed Add Batch idea! Check output console " + e);
            RollBack();
            return false;
        }

        if(upId.isEmpty()) {
            insertIdeia_Ideia = "INSERT INTO IDEIA_IDEIA VALUES(NULL" +
                    "," +  ideiaID + ")";
            try {
                statement.addBatch(insertIdeia_Ideia);
            }catch (SQLException e) {
                System.err.println("Connection Failed Add Batch idea! Check output console " + e);
                RollBack();
                return false;
            }
        }
        else {
            partes = upId.split(",");
            for(int i = 0; i < partes.length; ++i) {
                insertIdeia_Ideia = "INSERT INTO IDEIA_IDEIA VALUES(" + Integer.parseInt(partes[i]) +
                        "," +  ideiaID + ")";
                try {
                    statement.addBatch(insertIdeia_Ideia);
                }catch (SQLException e) {
                    System.err.println("Connection Failed Add Batch idea! Check output console " + e);
                    RollBack();
                    return false;
                }
            }
        }
        try {
            statement.executeBatch();
        }catch (SQLException e) {
            System.err.println("Connection Failed Add Batch idea! Check output console " + e);
            RollBack();
            return false;
        }
        addTransactionDescription(person, "Utilizador " + person + " criou ideia e possui 100% das shares dessa ideia", dataString);
        try {
            DBConn.commit();
        } catch (SQLException e) {
            System.err.println("Connection Failed Commiting Add Ideia! Check output console " + e);
            RollBack();
            return false;
        }
        return true;
    }

    public boolean removeIdeia(int pessoaID, int ideiaID) {
        long attributeID = getAttributeByPessoaIdeia(pessoaID, ideiaID);
        if(attributeID == 0) {
            System.err.println("Não conseguiu encontrar a ideia a remover");
            return false;
        }
        if(!veriffyUser_ShareTotality(pessoaID, ideiaID)) {
            System.err.println("Não possui todas as shares da ideia a remover");
            return false;
        }
        String removeTopico_Ideia = "DELETE FROM TOPICO_IDEIA "
                + "WHERE IDIDEIA = " + ideiaID;
        String removeIdeia_Ideia = "DELETE FROM IDEIA_IDEIA "
                + "WHERE IDIDEIADOWN = " + ideiaID;
        String removeAttribute = "DELETE FROM USER_SHARE "
                + "WHERE IDIDEIA = " + ideiaID + " AND IDUSER = " + pessoaID;
        String removeIdeia = "DELETE FROM IDEIA "
                + "WHERE IDIDEIA = " + ideiaID;
        try{
            statement.executeUpdate(removeTopico_Ideia);
            statement.executeUpdate(removeIdeia_Ideia);
            statement.executeUpdate(removeAttribute);
            statement.executeUpdate(removeIdeia);
        } catch (SQLException e) {
            System.err.println("Connection Failed deleting Ideia! Check output console " + e);
            RollBack();
            return false;
        }
        try {
            DBConn.commit();
        } catch (SQLException e) {
            System.err.println("Connection Failed Commiting! Check output console " + e);
            RollBack();
            return false;
        }
        return true;
    }

    private boolean veriffyUser_ShareTotality(int pessoaID, int ideiaID) {
        String getUser_ShareNr = "SELECT NR_SHARE FROM USER_SHARE " +
                "WHERE IDUSER = " + pessoaID + " AND IDIDEIA = "+ ideiaID;
        String getTotal_Shares = "SELECT TOTAL_SHARE FROM IDEIA " +
                "WHERE IDIDEIA = " + ideiaID;
        try{
            ResultSet rs1 = statement.executeQuery(getUser_ShareNr);
            rs1.next();
            int nrShares = rs1.getInt(1);
            ResultSet rs2 = statement.executeQuery(getTotal_Shares);
            rs2.next();
            int totalShares = rs2.getInt(1);
            if(nrShares == totalShares) return true;
            return false;
        } catch(SQLException e) {
            System.err.println("Connection Failed verifiyng Total Shares for removal! Check output console " + e);
            return false;
        }
    }

    public boolean addPendingTransaction(int userID, int ideiaID, int nr_shares, int newPrice, int maxPrice, String dataString) throws RemoteException {
        if(!verifyExistingPendingTransaction(userID, ideiaID)) {
            if(nr_shares > 0){
                String addPendingTransaction = "INSERT INTO PENDING_TRANSACTION VALUES (" +
                        userID + ", " + ideiaID + ", " + newPrice + ", " + maxPrice + ", " + nr_shares + ")";
                //System.out.println(addPendingTransaction);
                try{
                    statement.executeUpdate(addPendingTransaction);
                    DBConn.commit();
                    return true;
                } catch(SQLException e) {
                    System.err.println("Connection Failed adding Pending Transaction! Check output console " + e);
                    RollBack();
                    return false;
                }
            }
        }
        else{
            String updatePendingTransaction;
            updatePendingTransaction = "UPDATE PENDING_TRANSACTION " +
                    "SET NR_SHARE = " + nr_shares + ", NEWPRICE = " + newPrice + ", MAXPRICE = "+ maxPrice +
                    " WHERE IDUSER = "  + userID + " AND IDIDEIA = " + ideiaID;
            if(nr_shares == 0)   updatePendingTransaction = "DELETE FROM PENDING_TRANSACTION " +
                    "WHERE IDUSER = " + userID + "AND IDIDEIA = " + ideiaID;
            //System.out.println(updatePendingTransaction);
            try{
                statement.executeUpdate(updatePendingTransaction);
                DBConn.commit();
                addTransactionDescription(userID, "Utilizador " + userID + " mandou pedido de compra de " +nr_shares + " shares da ideia " +
                        ideiaID + " pelo preço de " + maxPrice, dataString);
                return true;
            } catch(SQLException e) {
                System.err.println("Connection Failed updating Pending Transaction! Check output console " + e);
                RollBack();
            }
        }
        return false;
    }

    private boolean verifyExistingPendingTransaction(int userID, int ideiaID) {
        String verifyExistingPendingTransaction = "SELECT COUNT(*) FROM PENDING_TRANSACTION " +
                "WHERE IDUSER = " + userID + " AND IDIDEIA = " + ideiaID;
        //System.out.println(verifyExistingPendingTransaction);
        try{
            ResultSet rs = statement.executeQuery(verifyExistingPendingTransaction);
            if(rs.next()){
                if(rs.getInt(1) == 0){
                    return false;
                }
            }
        } catch(SQLException e) {
            System.err.println("Connection Failed verifying existing Pending Transaction! Check output console " + e);
        }
        return true;
    }

    public String popPendingTransaction(int userID, int ideiaID) {
        String getPendingTransaction = "DELETE FROM PENDING_TRANSACTION " +
                "WHERE IDUSER = " + userID + " AND IDIDEIA = " + ideiaID;
        try{
            statement.executeUpdate(getPendingTransaction);
            DBConn.commit();
            return "Pedido para a compra de shares realizado com sucesso";
        } catch(SQLException e) {
            System.err.println("Connection Failed Popping Pending Transaction! Check output console " + e);
            RollBack();
            return "none";
        }
    }

    public boolean verifyExecutePendingTransaction(int ideiaID) throws RemoteException {
        String verifyTransaction = "SELECT * FROM PENDING_TRANSACTION " +
                "WHERE IDIDEIA = " + ideiaID;
        int idCompra, idIdeia, maxPrice, newPrice, nr_share, idVende;
        boolean cond = false;
        try{
            ResultSet rs = statement.executeQuery(verifyTransaction);
            while(rs.next()) {
                idCompra = rs.getInt(1);
                idIdeia = rs.getInt(2);
                newPrice = rs.getInt(3);
                maxPrice = rs.getInt(4);
                nr_share = rs.getInt(5);
                String dataString = dateFormat.format(new Date());
                idVende = getIDVendeByIdeia(idIdeia, maxPrice, idCompra);
                int aux, dif_aux;
                while(idVende > 0){
                    if((aux = minimo_share(idIdeia,idVende, nr_share)) > 0){
                        pendingTransaction(idVende, idCompra, idIdeia, aux, newPrice, maxPrice, dataString);
                        dif_aux = nr_share - aux;
                        cond = true;
                        if(dif_aux == 0) {
                            //System.out.println("DELETE " + dif_aux);
                            String updatePendingTransaction = "DELETE FROM PENDING_TRANSACTION " +
                                    "WHERE IDUSER = " + idCompra + " AND IDIDEIA = " + ideiaID;
                            try{
                                statement.executeUpdate(updatePendingTransaction);
                                DBConn.commit();
                            } catch(SQLException e) {
                                System.err.println("Connection Failed Removing User_share with 0! Check output console " + e);
                                RollBack();
                                cond = false;
                            }
                        }
                        else{
                            //System.out.println("UPDATE " + dif_aux);
                            String actualizaPrice = "UPDATE PENDING_TRANSACTION SET NR_SHARE = " + dif_aux +
                                    " WHERE IDIDEIA = " + idIdeia + " AND IDUSER = " + idCompra;
                            try{
                                statement.executeUpdate(actualizaPrice);
                                DBConn.commit();
                            } catch(SQLException e) {
                                System.err.println("Connection Failed Actualizar Preço! Check output console " + e);
                                RollBack();
                                cond = false;
                            }
                        }
                    }
                    dataString = dateFormat.format(new Date());
                    idVende = getIDVendeByIdeia(idIdeia, maxPrice, idCompra);
                }
            }
            //System.out.println("Foram vistos e satisfeitos, dentro do possivel, todos os pedidos de compra e venda para a ideia " + ideiaID);
            return cond;
        } catch(SQLException e) {
            System.err.println("Connection Failed verifying Execute Pending Transaction! Check output console " + e);
            RollBack();
            cond = false;
        } catch (RemoteException e) {
            System.err.println("Remote Failure Executing Pending Transaction! Check output console " + e);
            cond = false;
        }
        return cond;
    }

    public int minimo_share(int idIdeia, int idVende, int nr_share) {
        String getShareSale = "SELECT NR_SHARE FROM USER_SHARE "
                + "WHERE IDIDEIA = " + idIdeia + " AND IDUSER = " + idVende;
        try{
            ResultSet rs = statement.executeQuery(getShareSale);
            rs.next();
            int sharesVenda = rs.getInt(1);
            //System.out.println(nr_share + " -> " + sharesVenda);
            if(sharesVenda <= nr_share) return sharesVenda;
            return nr_share;
        } catch(SQLException e) {
            System.err.println("Connection Failed verifying Execute Pending Transaction! Check output console " + e);
            RollBack();
        }
        return 0;
    }

    private int getIDVendeByIdeia(int idIdeia, int precoMax, int idUser)  {
        String getVendedorByIDeia = "SELECT IDUSER FROM USER_SHARE "
                + "WHERE IDIDEIA = " + idIdeia + " AND PRICE <= " + precoMax + " AND IDUSER != " + idUser;
        try {
            ResultSet rs = statement.executeQuery(getVendedorByIDeia);
            if(rs.next())
                return rs.getInt(1);
            return 0;
        } catch(SQLException e) {
            System.err.println("Connection Failed Retrieving IDVendedor for executing Pending Transaction! Check output console " + e);
            return 0;
        }
    }
    // Começa uma operação de compra
    public synchronized boolean pendingTransaction(int idVende, int idCompra, int idIdeia, int sharesToBuy,
                                                   int newPrice, int precoCompra, String dataString) throws RemoteException {
        boolean isPossible = verifyTransaction(idVende, idCompra, idIdeia, sharesToBuy, precoCompra, dataString);
        if(isPossible){
            if(transaccaoAcceptedIdeia(idVende, idCompra, idIdeia, sharesToBuy, newPrice, precoCompra, dataString));
            try {
                DBConn.commit();
                return true;
            } catch (SQLException e) {System.err.println("Connection Failed Commiting! Check output console " + e);
                RollBack();
                return false;
            }
        }
        else {
            System.out.println("Venda não realizada");
            return false;
        }
    }

    // Verifica se a Compra é Possivel de se realizar
    private boolean verifyTransaction(int idVende, int idCompra, int ideiaTransaction, int sharesToBuy, int precoCompra, String dataString) {
        if(alreadyTransaction(idVende, idCompra, dataString)) {
            System.err.println("Já realizou esta operação");
            return false;
        }

        if(!didPriceChange(idVende, ideiaTransaction, precoCompra)) {
            System.err.println("O preço mudou  do lado do vendedor para concretizar a venda");
            return false;
        }
        if(!isThereEnoughShares(idVende, ideiaTransaction, sharesToBuy)) {
            System.err.println("Não há shares suficientes  do lado do vendedor para concretizar a venda");
            return false;
        }
        if(!isThereEnoughMoney(idVende, idCompra, ideiaTransaction, sharesToBuy)) {
            System.err.println("Não há dinheiro suficiente do lado do comprador para concretizar a venda");
            return false;
        }
        return true;
    }

    private boolean alreadyTransaction(int idVende, int idCompra, String dataString) {
        String getTransacao = "SELECT * FROM TRANSACCOES "
                +"WHERE DATA = '" + dataString + "' AND IDUSER = " + idCompra;
        try{
            ResultSet rs = statement.executeQuery(getTransacao);
            if(rs.next()) return true;
            return false;
        }catch (SQLException e) {
            System.err.println("Connection Failed Verify Transaction table! Check output console " + e);
        }
        return false;
    }

    //verifica se o preço da ideia não mudou
    private boolean didPriceChange(int idVende, int ideiaTransation, int precoCompra) {
        String precoDaIdeia = "SELECT PRICE FROM USER_SHARE "
                + "WHERE IDUSER = " + idVende + " AND IDIDEIA = " + ideiaTransation;
        try{
            ResultSet rs = statement.executeQuery(precoDaIdeia);
            rs.next();
            if(rs.getInt(1) <= precoCompra) return true;
        }catch (SQLException e) {
            System.err.println("Connection Failed Verify Same Price Shares! Check output console " + e);
        }
        return false;
    }

    // Verifica se há shares suficientes do lado do vendedor
    private boolean isThereEnoughShares(int idVende, int ideiaTransation, int sharesToBuy) {
        String numeroSharesIdeia = "SELECT NR_SHARE FROM USER_SHARE "
                + "WHERE IDUSER = " + idVende + " AND IDIDEIA = " + ideiaTransation;
        try{
            ResultSet rs = statement.executeQuery(numeroSharesIdeia);
            rs.next();
            if(rs.getInt(1) >= sharesToBuy) return true;
        }catch (SQLException e) {
            System.err.println("Connection Failed Verify Enough Shares! Check output console " + e);
        }
        return false;
    }

    // Verifica se há dinheiro suficiente do lado do comprador
    private boolean isThereEnoughMoney(int idVende, int idCompra, int ideiaTransaction, int sharesToBuy) {
        long idAttribute = getAttributeByPessoaIdeia(idVende, ideiaTransaction);
        String moneyUserCompra = "SELECT DINHEIRO  FROM UTILIZADOR "
                + "WHERE IDUSER = " + idCompra;
        String moneyNeeded = "SELECT PRICE * " + sharesToBuy + " FROM USER_SHARE "
                + "WHERE IDUSER_SHARE = "+ idAttribute;
        try {
            ResultSet rsUserMoney = statement.executeQuery(moneyUserCompra);
            rsUserMoney.next();
            int buyerMoney = rsUserMoney.getInt(1);
            ResultSet rsMoneyNeeded = statement.executeQuery(moneyNeeded);
            rsMoneyNeeded.next();
            int neededMoney = rsMoneyNeeded.getInt(1);
            if(buyerMoney >= neededMoney) return true;

        } catch (SQLException e) {
            System.err.println("Connection Failed Verify Enough Money! Check output console " + e);
        }
        return false;
    }


    // Executa a transacção tendo esta sido aceite
    private boolean transaccaoAcceptedIdeia(int idVende, int idCompra, int ideiaTransaction, int sharesToBuy, int newPrice, int precoCompra, String dataString) {
        boolean hasAttributeIdea = existingAttribute(idCompra, ideiaTransaction);
        boolean didItWork = true;
        if(hasAttributeIdea) {
            if(!bounceMoneyAndShares(idVende, idCompra, ideiaTransaction, sharesToBuy, newPrice))
                didItWork = false;
        }
        else {
            if(!createNewAttributeTransaction(idCompra, ideiaTransaction, newPrice) ||
                    !bounceMoneyAndShares(idVende, idCompra, ideiaTransaction, sharesToBuy, newPrice))
                didItWork = false;
        }
        if(!addTransactionDescription(idVende, "Utilizador vendeu " + sharesToBuy+" shares da ideia " + ideiaTransaction + " por " + precoCompra + " DEIcoins ao utilizador " + idCompra, dataString) ||
                !addTransactionDescription(idCompra, "Utilizador comprou  " + sharesToBuy+" shares da ideia " + ideiaTransaction + " por " + precoCompra + " DEIcoins ao utilizador " + idVende, dataString))
            didItWork = false;
        if(!verifyRemoveAttributeSeller(idVende, ideiaTransaction))
            didItWork = false;
        return didItWork;
    }

    // Verifica se já existe um User_Share do comprador para aquela ideia
    private boolean existingAttribute(int idCompra, int ideiaTransaction) {
        long idAttribute = this.getAttributeByPessoaIdeia(idCompra, ideiaTransaction);
        if(idAttribute == 0) {
            System.out.println("Atributo não existente. A criar novo atributo");
            return false;
        }
        return true;
    }

    // Transaciona o dinheiro e as Shares entre o vendedor e o comprador
    private boolean bounceMoneyAndShares(int idVende, int idCompra, int ideiaTransaction, int sharesToBuy, int newPrice) {
        long idAttributeToBuy = getAttributeByPessoaIdeia(idVende, ideiaTransaction);

        // 1-Descobrir o pre�o das shares a comprar
        String getAttributePriceSeller = "SELECT PRICE FROM USER_SHARE "
                + "WHERE IDUSER_SHARE = " + idAttributeToBuy;
        int preco = 0;
        try{
            ResultSet rsPrice= statement.executeQuery(getAttributePriceSeller);
            rsPrice.next();
            preco = rsPrice.getInt("PRICE");
        } catch (SQLException e) {
            System.err.println("Connection Failed Verify Shares' Price! Check output console " + e);
            RollBack();
            return false;
        }

        // 2-Retirar shares ao atributo do vendedor
        String addAttributeSharesSeller = "UPDATE USER_SHARE SET NR_SHARE = NR_SHARE - " + sharesToBuy
                + "WHERE IDUSER_SHARE = " + idAttributeToBuy;
        try{
            statement.executeUpdate(addAttributeSharesSeller);
        } catch (SQLException e) {
            System.err.println("Connection Failed Retrieve Shares From Seller! Check output console " + e);
            RollBack();
            return false;
        }

        // 3-Adicionar dinheiro ao vendedor
        String depositMoneySeller = "UPDATE UTILIZADOR SET DINHEIRO = DINHEIRO + " + preco*sharesToBuy +
                " WHERE IDUSER = " + idVende;
        try{
            statement.executeUpdate(depositMoneySeller);
        } catch (SQLException e) {
            System.err.println("Connection Failed Add Money To Seller! Check output console " + e);
            RollBack();
            return false;
        }

        // 4-Descobrir o atributo do comprador da respectiva ideia
        long idAttributeToSell = this.getAttributeByPessoaIdeia(idCompra, ideiaTransaction);

        // 5-Retirar dinheiro ao comprador
        String retrieveMoneyBuyer = "UPDATE UTILIZADOR SET UTILIZADOR.DINHEIRO = UTILIZADOR.DINHEIRO - " + preco*sharesToBuy
                + " WHERE IDUSER = " + idCompra;
        try{
            statement.executeUpdate(retrieveMoneyBuyer);
        } catch (SQLException e) {
            System.err.println("Connection Failed retrieve Money from Buyer! Check output console " + e);
            RollBack();
            return false;
        }

        // 6-Adicionar Shares ao atributo do comprador
        String addAttributeSharesBuyer = "UPDATE USER_SHARE SET NR_SHARE = NR_SHARE + " + sharesToBuy
                + "WHERE IDUSER_SHARE = " + idAttributeToSell;
        try{
            statement.executeUpdate(addAttributeSharesBuyer);
        } catch (SQLException e) {
            System.err.println("Connection Failed Add Shares To Buyer! Check output console " + e);
            RollBack();
            return false;
        }

        // 7-Actualizar novo pre�o das shares do comprador
        String updateSharesAttributeBuyer = "UPDATE USER_SHARE SET USER_SHARE.PRICE = " + newPrice
                + "WHERE IDUSER_SHARE = "+ idAttributeToSell;
        try{
            statement.executeUpdate(updateSharesAttributeBuyer);
        } catch (SQLException e) {
            System.err.println("Connection Failed Update Shares' Price Buyer! Check output console " + e);
            RollBack();
            return false;
        }
        return true;
    }

    //Cria um novo User_Share para o comprador com NR_Share a zero
    private boolean createNewAttributeTransaction(int idCompra, int ideiaTransaction, int newPrice) {
        int attributeID =  incrementAttributeID();
        String insertUser_Share = "INSERT INTO USER_SHARE VALUES"
                + "(" + attributeID + "," + ideiaTransaction + "," + idCompra +
                "," + newPrice + "," + 0 + ")";
        try{
            statement.executeUpdate(insertUser_Share);
            return true;
        } catch (SQLException e) {
            System.err.println("Connection Failed Add New Attribute To Buyer! Check output console " + e);
            RollBack();
            return false;
        }
    }

    //Verifica se um User_Share não tem shares nenhumas e remove-o
    private boolean verifyRemoveAttributeSeller(int idVende, int ideiaTransaction) {
        // 1-Verificar se o atributo foi a zero
        long idAttributeToBuy = this.getAttributeByPessoaIdeia(idVende, ideiaTransaction);
        String getAttributeShares = "SELECT NR_SHARE FROM USER_SHARE "
                + "WHERE IDUSER_SHARE = " + idAttributeToBuy;
        try{
            ResultSet rs = statement.executeQuery(getAttributeShares);
            rs.next();
            int sharesQuantity = rs.getInt("NR_SHARE");
            if(sharesQuantity == 0)
                if(removeAttribute(idVende, ideiaTransaction, idAttributeToBuy))
                    return true;
                else
                    return false;
            return true;
        } catch (SQLException e) {
            System.err.println("Connection Failed Verify Remove Attribute From Buyer! Check output console " + e);
            RollBack();
            return false;
        }
    }

    //Remove um User_Share
    private boolean removeAttribute(int idVende, int ideiaTransaction, long idAttributeToBuy) {
        String removeFromAttributes = "DELETE FROM USER_SHARE "
                + "WHERE IDUSER_SHARE = " + idAttributeToBuy;
        try{
            statement.executeUpdate(removeFromAttributes);
            return true;
        } catch (SQLException e) {
            System.err.println("Connection Failed Removing Attribute From Buyer! Check output console " + e);
            RollBack();
            return false;
        }
    }

    public Mensagem retrieveAnexos(int id_ideia, String path) throws RemoteException {
        String command = "SELECT ANEXO FROM IDEIA WHERE IDIDEIA = " + id_ideia;
        Mensagem msg = new Mensagem(15,0);
        msg.addList(path);
        try{
            ResultSet rs = statement.executeQuery(command);
            if(rs.next()){
                String fich_name = rs.getString(1);
                if(!"".equals(fich_name) && fich_name != null){
                    //System.out.println(fich_name);
                    msg.addFile(fich_name);
                }
            }
        } catch (SQLException e) {System.err.println("Connection Failed Removing Attribute From Buyer! Check output console " + e);}
        return msg;
    }
}
