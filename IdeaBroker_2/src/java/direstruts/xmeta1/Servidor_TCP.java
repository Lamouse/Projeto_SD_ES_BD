package direstruts.xmeta1;

import java.io.*;
import java.net.*;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.CopyOnWriteArrayList;

public class Servidor_TCP {
	private String ip_server = "localhost";
        private String ip_RMI = "localhost";
	private int serverPort = 7000;
        private int serverRMI = 1099;
	private static CopyOnWriteArrayList<Integer> idUser = new CopyOnWriteArrayList<Integer>();
	private static CopyOnWriteArrayList<ObjectOutputStream> socketUser = new CopyOnWriteArrayList<ObjectOutputStream>();
	
	Servidor_TCP(){
        InputStreamReader isr = new InputStreamReader( System.in );
        BufferedReader stdin = new BufferedReader( isr );
        try {
            System.out.print( "Introduza o IP do outro servidor: " );
            ip_server = stdin.readLine();
            System.out.print( "Introduza o Porto do outro servidor: " );
            serverPort = tryParse(stdin.readLine());
            System.out.print( "Introduza o IP do servidor RMI: " );
            ip_RMI = stdin.readLine();
            System.out.print( "Introduza o Porto do servidor RMI: " );
            serverRMI = tryParse(stdin.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println( "\nServer TCP\n\tIP = " + ip_server + "\n\tPorto = " + serverPort + "\nServer RMI\n\tIP = " + ip_RMI + "\n\tPorto = " + serverRMI+"\n");

        cliente();
	}

    private Integer tryParse(String text) {
        try {
            return new Integer(text);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
	
	private void server(){
		new Server_UDP(serverPort);
	
		//ligar-se ao servidor rmi
		new Server_TCP(serverPort);
		//ficar � espera de clientess
	}

    private void cliente(){
        DatagramSocket aSocket = null;
        String msg = "Confirmação do Server2";
        byte [] m = msg.getBytes();
        int cont = 5;

        try {
            System.out.println("Estou a fazer o papel de servidor secundário.");
            aSocket = new DatagramSocket();
            InetAddress aHost = InetAddress.getByName(ip_server);
            DatagramPacket request = new DatagramPacket(m,m.length,aHost,serverPort);
            byte[] buffer = new byte[1000];
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            String s;
            while(cont != 0){
                aSocket.send(request);
                aSocket.setSoTimeout(500);
                try{
                    aSocket.receive(reply);
                    s=new String(reply.getData(), 0, reply.getLength());
                    System.out.println(s);
                    cont = 5;
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }catch(SocketTimeoutException e){
                    System.out.println("Time Out");
                    cont--;
                }
            }
        }catch (SocketException e){System.out.println("Socket: " + e.getMessage());
        }catch (IOException e){System.out.println("IO: " + e.getMessage());
        }finally {if(aSocket != null) aSocket.close();}

        server();
    }
	
	public static void main(String args[]){ 
		new Servidor_TCP();
	}
	
	class Cliente extends Thread {
		private Socket s;
		private ObjectOutputStream objectOutput;
		private ObjectInputStream objectInput;
		private ExecuteCommands srmi;
		
		Cliente(Socket s) {
			this.s = s;
			try {

				objectOutput = new ObjectOutputStream(s.getOutputStream());
				objectInput = new ObjectInputStream(s.getInputStream());
				this.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		private Integer tryParse(String text) {
			try {
				return new Integer(text);
			} catch (NumberFormatException e) {
				return 0;
			}
		}

        private double tryParseDouble(String text) {
            try {
                return Double.parseDouble(text);
            } catch (NumberFormatException e) {
                return (double)0;
            }
        }
		
		public void run() {
            boolean cond = false;
            do {
                try {
                    //srmi = (ExecuteCommands) Naming.lookup("ServerRMI");
                    srmi = (ExecuteCommands)Naming.lookup("rmi://" + ip_RMI + ":" + serverRMI + "/ServerRMI");
                    cond = false;
                    int tipo, aux;
                    Mensagem msg;
                    while(true){
                        Mensagem data = (Mensagem) objectInput.readObject();
                        if(data.getIdUser() != 0 && data.getTipo() != 1 && data.getTipo() != 2){
                            if(idUser.indexOf(data.getIdUser()) == -1){
                                idUser.add(data.getIdUser());
                                socketUser.add(objectOutput);
                            }
                            else if(socketUser.get(idUser.indexOf(data.getIdUser())) != objectOutput){
                                socketUser.set(idUser.indexOf(data.getIdUser()), objectOutput);
                            }
                        }

                        tipo = data.getTipo();

                        if(tipo == 1){
                            System.out.println("Confirmação do Login do User " + data.getElemList(0));
                            aux = srmi.hasUSer(data.getElemList(0), data.getElemList(1));
                            if(aux!=-1 && idUser.indexOf(aux) == -1){
                                msg = new Mensagem(1,data.getIdUser());
                                msg.addList(Integer.toString(aux));
                                msg.addList(data.getElemList(0));
                                msg.addList(srmi.popUserDataOffline(aux));
                            }else {
                                msg = new Mensagem(-1,data.getIdUser());
                            }
                            objectOutput.writeObject(msg);
                        }else if(tipo == 2){
                            System.out.println("Confirmação do Registo do User " + data.getElemList(0));
                            if(srmi.addPessoa(data.getElemList(0), data.getElemList(1)) == true)
                                msg = new Mensagem(2,data.getIdUser());
                            else
                                msg = new Mensagem(-2,data.getIdUser());
                            objectOutput.writeObject(msg);
                        }else if(tipo == 3){
                            System.out.println("Pedido do tópico " + data.getElemList(0));
                            if(srmi.addTopic(data.getElemList(0)) == true)
                                msg = new Mensagem(3,data.getIdUser());
                            else
                                msg = new Mensagem(-3,data.getIdUser());
                            objectOutput.writeObject(msg);
                        }else if(tipo == 4){
                            System.out.println("Pedido para a visualização dos tópicos");
                            msg = srmi.retrieveTopicos();
                            objectOutput.writeObject(msg);
                        }else if(tipo == 5){
                            System.out.println("Pedido para a visualização das ideias");
                            msg = srmi.retrieveIdeias(tryParse(data.popList()));
                            objectOutput.writeObject(msg);
                        }else if(tipo == 6) {
                            System.out.println("Pedido para a criação de ideia");
                            if(srmi.addNewIdeia(data.popList(), data.popList(), tryParse(data.popList()), tryParse(data.popList()), data.getData(), data.getFile()))
                                msg = new Mensagem(6,data.getIdUser());
                            else
                                msg = new Mensagem(-6,data.getIdUser());
                            objectOutput.writeObject(msg);
                        }else if(tipo == 7) {
                            System.out.println("Compra de Shares");
                            int id_ideia = tryParse(data.getElemList(2)), id_vende = tryParse(data.getElemList(0)), id_compra = tryParse(data.getElemList(1));
                            if(srmi.pendingTransaction(tryParse(data.popList()),tryParse(data.popList()),tryParse(data.popList()),tryParse(data.popList()),tryParseDouble(data.popList()),tryParseDouble(data.popList()), data.getData()) == true){
                                srmi.verifyExecutePendingTransaction(id_ideia);
                                msg = new Mensagem(7,id_ideia);
                            }else
                                msg = new Mensagem(-7,data.getIdUser());
                            objectOutput.writeObject(msg);
                        }else if(tipo == 8) {
                            System.out.println("Pedido das Shares da ideia " + data.getElemList(0));
                            msg = srmi.retrieveAttributes(data.getIdUser(),tryParse(data.popList()));
                            objectOutput.writeObject(msg);
                        }else if(tipo == 9) {
                            System.out.println("Pedido para apagar ideia");
                            if(srmi.removeIdeia(data.getIdUser(), tryParse(data.popList())))
                                msg = new Mensagem(9,0);
                            else
                                msg = new Mensagem(-9,0);
                            objectOutput.writeObject(msg);
                        }else if(tipo == 10) {
                            System.out.println("Pedido dos dados do utilizador");
                            msg = srmi.retrieveUserData(data.getIdUser());
                            objectOutput.writeObject(msg);
                        }else if(tipo == 11) {
                            System.out.println("Pedido das nossas ideias");
                            msg = srmi.retrieveIdeiasUser(data.getIdUser());
                            objectOutput.writeObject(msg);
                        }else if(tipo == 12) {
                            System.out.println("Pedido para a atualização de preço");
                            int id_ideia = tryParse(data.getElemList(1));
                            srmi.changeSharePrice(data.getIdUser(), tryParse(data.popList()), tryParseDouble(data.popList()));
                            srmi.verifyExecutePendingTransaction(id_ideia);
                            objectOutput.writeObject(new Mensagem(12,data.getIdUser()));
                        }else if(tipo == 13) {
                            System.out.println("Pedido para a pesquisa de ideias por tópico");
                            msg = srmi.retrieveIdeiasTopico(tryParse(data.popList()));
                            objectOutput.writeObject(msg);
                        }else if(tipo == 14) {
                            System.out.println("Pedido para a pesquisa de notificações");
                            msg = new Mensagem(14,0);
                            msg.addList(srmi.popUserDataOffline(data.getIdUser()));
                            objectOutput.writeObject(msg);
                        }else if(tipo == 15) {
                            System.out.println("Pedido para o download de Anexos");
                            msg = srmi.retrieveAnexos(tryParse(data.popList()),data.popList());
                            objectOutput.writeObject(msg);
                        }else if(tipo == 16) {
                            System.out.println("Pedido para Logout");
                            if(socketUser.indexOf(objectOutput) != -1){
                                idUser.remove(socketUser.indexOf(objectOutput));
                                socketUser.remove(objectOutput);
                            }
                            objectOutput.writeObject(new Mensagem(16,0));
                        }else if(tipo == 17) {
                            System.out.println("Pedido para a compra de shares");
                            Mensagem msg_aux = new Mensagem(17,0);
                            int idideia = tryParse(data.getElemList(0));
                            if(srmi.addPendingTransaction(data.getIdUser(),tryParse(data.popList()),tryParse(data.popList()),tryParseDouble(data.popList()),tryParseDouble(data.popList()),data.getData())==true){
                                System.out.println("COMPRA ACEITE");
                            }
                            objectOutput.writeObject(msg_aux);
                        }
                        else if(tipo == 18) {
                            System.out.println("Pedido para a compra de shares pelo root");
                            Mensagem msg_aux = new Mensagem(18,0);
                            srmi.buy_all(tryParse(data.popList()));
                            objectOutput.writeObject(msg_aux);
                        }
                        objectOutput.flush();
                    }
                } catch (java.rmi.ConnectException e) {
                    cond = true;
                    System.out.println("A conecção ao rmi falhou! " + e.getMessage());
                } catch (SocketException e) {
                    if(socketUser.indexOf(objectOutput) != -1){
                        idUser.remove(socketUser.indexOf(objectOutput));
                        socketUser.remove(objectOutput);
                    }
                    System.out.println("O cliente saiu. " + e.getMessage());
                } catch(EOFException e) {
                    if(socketUser.indexOf(objectOutput) != -1){
                        idUser.remove(socketUser.indexOf(objectOutput));
                        socketUser.remove(objectOutput);
                    }
                    System.out.println("O cliente fechou a ligação." + e.getMessage());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    if(socketUser.indexOf(objectOutput) != -1){
                        idUser.remove(socketUser.indexOf(objectOutput));
                        socketUser.remove(objectOutput);
                    }
                    System.out.println("O cliente saiu. " + e.getMessage());
                } catch (NotBoundException e) {
                    e.printStackTrace();
                }
            } while(cond);
		}
	}

	class Server_TCP extends Thread {
		private int serverPort;
		
		Server_TCP(int serverPort) {
			this.serverPort = serverPort;
			this.start();
		}
		
		public void run() {
			try{
				System.out.println("A espera de Clientes no porto "+serverPort);
	            ServerSocket listenSocket = new ServerSocket(serverPort);
	            while(true) {
	            	Socket s = listenSocket.accept();
	                new Cliente(s);
	            }
			}catch(IOException e) {
				System.out.println("Listen:" + e.getMessage());
			}
		}
	}

    class Server_UDP extends Thread {
        private int serverPort;

        Server_UDP(int serverPort) {
            this.serverPort = serverPort;
            this.start();
        }

        public void run() {
            DatagramSocket aSocket = null;
            String s;
            try{
                System.out.println("Estou a fazer o papel de servidor primário.");
                aSocket = new DatagramSocket(serverPort);
                byte[] buffer = new byte[1000];
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                while(true){
                    aSocket.receive(request);
                    s=new String(request.getData(), 0, request.getLength());
                    System.out.println("Server Recebeu: " + s);

                    s = "Confirmação do Server1";
                    byte [] m = s.getBytes();
                    request.setData(m);

                    DatagramPacket reply = new DatagramPacket(request.getData(),
                            request.getLength(), request.getAddress(), request.getPort());
                    aSocket.send(reply);
                }
            }catch (SocketException e){System.out.println("Socket: " + e.getMessage());
            }catch (IOException e) {System.out.println("IO: " + e.getMessage());
            }finally {if(aSocket != null) aSocket.close();}
        }
    }

}