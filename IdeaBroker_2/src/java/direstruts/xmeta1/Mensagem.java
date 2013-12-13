package direstruts.xmeta1;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Mensagem implements Serializable {
	private static final long serialVersionUID = 1L;
	private ArrayList<String> list = new ArrayList<String>();
	private int tipo = 0;
	private int id_user = 0;
	private String dataString = "";
	private final static DateFormat dateFormat =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        private NamedByteArray file = null;
	
	/*tipo
	 * 1	Login (a esta msg estará acoplado as notificações offline)
	 * 2	Registar
	 * 3	Cria Topico
	 * 4	Receber Topico
	 * 5 	Receber Ideia
	 * 6	Criar Ideia
	 * 7	comprar Ideia 	
	 * 8	receber dados compra
	 * 9	remove ideia
	 * 10	receber os dados do utilizador
	 * 11	receber as nossas ideias
	 * 12	atualizar preço
	 * 13	recebe as ideias na pesquisa por tópico
	 * 14	pedir notificação
	 * 15   pedir ficheiro
	 * 16   pedir logout
	 * 17   fazer pedidos
	 * 18   compra de shares pelo root
	 * 19   retornar HoF
	 * 20	pesquisar ideias
	 ...
	 */
	
	Mensagem(int tipo, int id_user){
		this.tipo = tipo;
		this.id_user = id_user;
		Date data = new Date();
        dataString = dateFormat.format(data);
	}
	
	public int getIdUser(){
		return id_user;
	}
	
	public int getTipo(){
		return tipo;
	}
	
	public void addList(String aux) {
		list.add(aux);
	}
	
	public String popList() {
		String aux = list.get(0);
		list.remove(0);
		return aux;
	}
	
	public String getElemList(int aux) {
		return list.get(aux);
	}
	
	public int getListSize() {
		return list.size();
	}
	
	public String getData() {
		return dataString;
	}

    public void addFile(String path) {
        try {
            file = new NamedByteArray(path);
        } catch (FileNotFoundException e) {
            file = null;
            System.err.println("Ficheiro Não encontrado: " + e);
        }
    }

    public NamedByteArray getFile() {
        return file;
    }
}
