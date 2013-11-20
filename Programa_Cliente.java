import javax.swing.*;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author Paulo Pereira | João Silva
 * 
 */

public class Programa_Cliente extends javax.swing.JFrame {
    //menu_login
    private javax.swing.JButton jButton_ip;
    private javax.swing.JButton jButton_login;
    private javax.swing.JButton jButton_reg;
    private javax.swing.JLabel jLabel_ip1;
    private javax.swing.JLabel jLabel_ip2;
    private javax.swing.JLabel jLabel_port;
    private javax.swing.JLabel jLabel_ligar;
    private javax.swing.JLabel jLabel_pass;
    private javax.swing.JLabel jLabel_user;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPasswordField jPasswordField_pass;
    private javax.swing.JTextField jTextField_ip1;
    private javax.swing.JTextField jTextField_ip2;
    private javax.swing.JTextField jTextField_port;
    private javax.swing.JTextField jTextField_user;
    //menu_principal
    private javax.swing.JButton jButton_buy_search;
    private javax.swing.JButton jButton_creat;
    private javax.swing.JButton jButton_desco;
    private javax.swing.JButton jButton_search;
    private javax.swing.JButton jButton_topico;
    private javax.swing.JButton jButton_pedido;
    private javax.swing.JComboBox jComboBox_creat;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel5_creat_nota1;
    private javax.swing.JLabel jLabel_buy_error;
    private javax.swing.JLabel jLabel_buy_id;
    private javax.swing.JLabel jLabel_creat_anexo;
    private javax.swing.JLabel jLabel_creat_aviso;
    private javax.swing.JLabel jLabel_creat_idea;
    private javax.swing.JLabel jLabel_creat_msg;
    private javax.swing.JLabel jLabel_creat_nota2;
    private javax.swing.JLabel jLabel_creat_preco;
    private javax.swing.JLabel jLabel_creat_share;
    private javax.swing.JLabel jLabel_creat_topic;
    private javax.swing.JLabel jLabel_msg_fundo;
    private javax.swing.JLabel jLabel_new_topic_error;
    private javax.swing.JLabel jLabel_pedido;
    private javax.swing.JLabel jLabel_pedido_id;
    private javax.swing.JLabel jLabel_pedido_preco;
    private javax.swing.JLabel jLabel_pedido_share;
    private javax.swing.JLabel jLabel_search_erro;
    private javax.swing.JLabel jLabel_search_name;
    private javax.swing.JLabel jLabel_search_topic;
    private javax.swing.JLabel jLabel_topico;
    private javax.swing.JPanel jPanel_topic;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel_topic_idea;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel_buy;
    private javax.swing.JPanel jPanel_buy_idea;
    private javax.swing.JPanel jPanel_fundo;
    private javax.swing.JPanel jPanel_idea;
    private javax.swing.JPanel jPanel_new_idea;
    private javax.swing.JPanel jPanel_new_topic;
    private javax.swing.JPanel jPanel_pedidos;
    private javax.swing.JPanel jPanel_our_idea;
    private javax.swing.JPanel jPanel_search;
    private javax.swing.JPanel jPanel_search_panel;
    private javax.swing.JPanel jPanel_search_topic;
    private javax.swing.JPanel jPanel_see_topic;
    private javax.swing.JPanel jPanel_see_user;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane_idea;
    private javax.swing.JScrollPane jScrollPane_see_user;
    private javax.swing.JScrollPane jScrollPane_topico;
    private javax.swing.JTabbedPane jTabbedPane_menu;
    private javax.swing.JTextArea jTextArea_creat_msg;
    private javax.swing.JTextField jTextField_buy_id;
    private javax.swing.JTextField jTextField_creat_anexo;
    private javax.swing.JTextField jTextField_creat_idea;
    private javax.swing.JTextField jTextField_creat_preco;
    private javax.swing.JTextField jTextField_creat_share;
    private javax.swing.JTextField jTextField_creat_topic;
    private javax.swing.JTextField jTextField_search_topic;
    private javax.swing.JTextField jTextField_topico;
    private javax.swing.JTextField jTextField_pedido_id;
    private javax.swing.JTextField jTextField_pedido_share;
    private javax.swing.JTextField jTextField_pedido_preco;
    private java.awt.Label label_text_user_hist;
    private java.awt.Label label_text_user_id;
    private java.awt.Label label_text_user_money;
    private java.awt.Label label_text_user_name;
    private java.awt.Label label_user_id;
    private java.awt.Label label_user_money;
    private java.awt.Label label_user_name;
    private javax.swing.JTextArea jTextArea_see_user;


	private static final long serialVersionUID = 1L;
	private String ip_server1 = "localhost";
	private String ip_server2 = "localhost";
	private int port = 7000;
    private String server;
    private Socket s = null;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private ReceiveMsg thread_r;
    private SendMsg thread_s;
    private SendNtf thread_n;
    private int user_id;
    private String user_nome;
    private boolean login;

    Programa_Cliente() {
    	iniciar_janela();
        server = ip_server1;
        new Menu_Login();
    }
    
    private void iniciar_janela() {
    	setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(750, 700));
        setMinimumSize(new java.awt.Dimension(750, 700));
        setResizable(false);
        setLocationRelativeTo(null);		//para a janela aparecer no centro do ecra
        pack();
    }
    
    public static void main(String args[]) {
    	/* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
    	try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Programa_Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Programa_Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Programa_Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Programa_Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    	
    	java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
            	new Programa_Cliente().setVisible(true);
            }
        });
    }

    public class Menu_Login {

        Menu_Login() {
            initComponents();
            login = true;

            jTextField_ip1.setText(ip_server1);
            jTextField_ip2.setText(ip_server2);
            jTextField_port.setText(Integer.toString(port));
            jLabel_ip1.setVisible(false);
            jLabel_ip2.setVisible(false);
            jLabel_port.setVisible(false);
            jTextField_ip1.setVisible(false);
            jTextField_ip2.setVisible(false);
            jTextField_port.setVisible(false);
            jLabel_ligar.setVisible(false);
        }

        private String stringHexa(byte[] bytes) {
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                int parteAlta = ((bytes[i] >> 4) & 0xf) << 4;
                int parteBaixa = bytes[i] & 0xf;
                if (parteAlta == 0) s.append('0');
                s.append(Integer.toHexString(parteAlta | parteBaixa));
            }
            return s.toString();
        }

        public byte[] gerarHash(String frase, String algoritmo) {
            try {
                MessageDigest md = MessageDigest.getInstance(algoritmo);
                md.update(frase.getBytes());
                return md.digest();
            } catch (NoSuchAlgorithmException e) {
                return null;
            }
        }

        private void jButton_ipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ipActionPerformed
            if(jLabel_ligar.isVisible())
                jLabel_ligar.setVisible(false);
            if(jLabel_ip1.isVisible() == false){
                jLabel_ip1.setVisible(true);
                jLabel_ip2.setVisible(true);
                jLabel_port.setVisible(true);
                jTextField_ip1.setVisible(true);
                jTextField_ip2.setVisible(true);
                jTextField_port.setVisible(true);
            }
            else{
                jLabel_ip1.setVisible(false);
                jLabel_ip2.setVisible(false);
                jLabel_port.setVisible(false);
                jTextField_ip1.setVisible(false);
                jTextField_ip2.setVisible(false);
                jTextField_port.setVisible(false);
            }
        }

        @SuppressWarnings("deprecation")
        private void jButton_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_loginActionPerformed
            if(!"".equals(jTextField_ip1.getText().replace(" ","")) && !"".equals(jTextField_ip2.getText().replace(" ","")) && !"".equals(jTextField_port.getText().replace(" ",""))){
                if(!ip_server1.equals(jTextField_ip1.getText()) || !ip_server2.equals(jTextField_ip2.getText()) || !Integer.toString(port).equals(jTextField_port.getText())){
                    ip_server1 = jTextField_ip1.getText();
                    ip_server2 = jTextField_ip2.getText();
                    server = ip_server1;
                    port = tryParse(jTextField_port.getText());
                }
                if(s == null) {
                    criaLigacao();

                    if(thread_s==null && s != null){
                        thread_s = new SendMsg();
                        thread_n = new SendNtf();
                        thread_r = new ReceiveMsg();
                    }
                }

                if(s != null){
                    if(!"".equals(jTextField_user.getText().replace(" ","")) && !"".equals(jPasswordField_pass.getText().replace(" ",""))){
                        jLabel_ligar.setText("A ligar...");
                        jLabel_ligar.setVisible(true);

                        if(login == true){
                            Mensagem msg = new Mensagem(1,0);
                            msg.addList(jTextField_user.getText());
                            msg.addList(stringHexa(gerarHash(jPasswordField_pass.getText(), "MD5")));
                            thread_s.addList(msg);
                        }
                        else{
                            Mensagem msg = new Mensagem(2,0);
                            msg.addList(jTextField_user.getText());
                            msg.addList(stringHexa(gerarHash(jPasswordField_pass.getText(), "MD5")));
                            thread_s.addList(msg);
                        }
                    }
                    else{
                        jLabel_ligar.setText("Não é possível fazer a ligação! Pelo menos um dos campos está em branco.");
                        jLabel_ligar.setVisible(true);
                    }
                }
                else{
                    criaLigacao();

                    if(thread_s==null && s != null){
                        thread_s = new SendMsg();
                        thread_n = new SendNtf();
                        thread_r = new ReceiveMsg();
                    }
                }
            }
            else{
                jLabel_ligar.setText("Pelo menos um dos campos necessários para o estabelecimento da ligação está em branco.");
                jLabel_ligar.setVisible(true);
            }
        }

        private Integer tryParse(String text) {
            try {
                return new Integer(text);
            } catch (NumberFormatException e) {
                return 0;
            }
        }

        private void criaLigacao() {
            int cont2 = 3, cont;
            while(cont2 > 0){
                cont = 5;
                while(cont > 0){
                    try {
                        if(s != null)
                            s.close();
                        s = new Socket(server, port);
                        s.setSoTimeout(5000);
                        oos = new ObjectOutputStream(s.getOutputStream());
                        ois = new ObjectInputStream(s.getInputStream());
                        cont = 0;
                        cont2 = 0;
                    } catch (UnknownHostException e) {
                        System.out.println("Erro no Sock");
                        cont--;
                        if(cont == 0){
                            if(server.equals(ip_server1))
                                server = ip_server2;
                            else
                                server = ip_server1;
                            cont2--;
                        }
                    } catch (EOFException e) {
                        System.out.println("Erro no EOF");
                        cont--;
                        if(cont == 0){
                            if(server.equals(ip_server1))
                                server = ip_server2;
                            else
                                server = ip_server1;
                            cont2--;
                        }
                    } catch (IOException e) {
                        System.out.println("Erro na ligação");
                        cont--;
                        if(cont == 0){
                            if(server.equals(ip_server1))
                                server = ip_server2;
                            else
                                server = ip_server1;
                            cont2--;
                        }
                    }
                }
            }
        }

        private void jButton_regActionPerformed() {//GEN-FIRST:event_jButton_loginActionPerformed
            if(jLabel_ligar.isVisible())
                jLabel_ligar.setVisible(false);

            if (login) {
                if(jLabel_ip1.isVisible()){
                    jLabel_ip1.setVisible(false);
                    jLabel_ip2.setVisible(false);
                    jLabel_port.setVisible(false);
                    jTextField_ip1.setVisible(false);
                    jTextField_ip2.setVisible(false);
                    jTextField_port.setVisible(false);
                }
                login = false;
                jButton_ip.setEnabled(false);
                jButton_reg.setText("Login");
                jButton_login.setText("Registar");
                jTextField_user.setText("");
                jPasswordField_pass.setText("");
            } else {
                login = true;
                jButton_ip.setEnabled(true);
                jButton_login.setText("Ligar");
                jButton_reg.setText("Registar");
                jTextField_user.setText("");
                jPasswordField_pass.setText("");
            }
        }

        private void initComponents() {
            jButton_ip = new javax.swing.JButton();
            jPanel1 = new javax.swing.JPanel();
            jLabel_pass = new javax.swing.JLabel();
            jLabel_user = new javax.swing.JLabel();
            jPasswordField_pass = new javax.swing.JPasswordField();
            jTextField_user = new javax.swing.JTextField();
            jPanel2 = new javax.swing.JPanel();
            jLabel_ip1 = new javax.swing.JLabel();
            jLabel_ip2 = new javax.swing.JLabel();
            jLabel_port = new javax.swing.JLabel();
            jTextField_ip1 = new javax.swing.JTextField();
            jTextField_ip2 = new javax.swing.JTextField();
            jTextField_port = new javax.swing.JTextField();
            jPanel4 = new javax.swing.JPanel();
            jButton_login = new javax.swing.JButton();
            jPanel3 = new javax.swing.JPanel();
            jLabel_ligar = new javax.swing.JLabel();
            jButton_reg = new javax.swing.JButton();

            jButton_ip.setText("Configurar IP");
            jButton_ip.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton_ipActionPerformed(evt);
                }
            });

            jPanel1.setMaximumSize(new java.awt.Dimension(250, 96));
            jPanel1.setMinimumSize(new java.awt.Dimension(250, 96));

            jLabel_pass.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            jLabel_pass.setText("Palavra-passe:");

            jLabel_user.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            jLabel_user.setText("Nome do Utilizador:");
            jLabel_user.setToolTipText("");

            jPasswordField_pass.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

            jTextField_user.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

            javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
            jPanel1.setLayout(jPanel1Layout);
            jPanel1Layout.setHorizontalGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(44, 44, 44)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel_pass)
                                            .addComponent(jLabel_user))
                                    .addGap(33, 33, 33)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField_user)
                                            .addComponent(jPasswordField_pass))
                                    .addContainerGap())
            );
            jPanel1Layout.setVerticalGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel_user, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jTextField_user))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel_pass)
                                            .addComponent(jPasswordField_pass))
                                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );

            jPanel2.setMaximumSize(new java.awt.Dimension(375, 86));
            jPanel2.setMinimumSize(new java.awt.Dimension(375, 86));

            jLabel_ip1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            jLabel_ip1.setText("IP Server1:");

            jTextField_ip1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

            jLabel_ip2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            jLabel_ip2.setText("IP Server2:");

            jTextField_ip2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

            jLabel_port.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            jLabel_port.setText("Porta:");

            jTextField_port.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N



            javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
            jPanel2.setLayout(jPanel2Layout);
            jPanel2Layout.setHorizontalGroup(
                    jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(44, 44, 44)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel_ip2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel_ip1, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                                            .addComponent(jLabel_port, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTextField_ip1, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
                                            .addComponent(jTextField_ip2)
                                            .addComponent(jTextField_port))
                                    .addContainerGap())
            );
            jPanel2Layout.setVerticalGroup(
                    jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel_ip1)
                                            .addComponent(jTextField_ip1))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel_ip2)
                                            .addComponent(jTextField_ip2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jTextField_port, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel_port))
                                    .addGap(80, 80, 80))
            );

            jPanel4.setMaximumSize(new java.awt.Dimension(538, 86));
            jPanel4.setMinimumSize(new java.awt.Dimension(538, 86));
            jPanel4.setPreferredSize(new java.awt.Dimension(538, 86));

            jButton_login.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
            jButton_login.setText("Ligar");
            jButton_login.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton_loginActionPerformed(evt);
                }
            });

            javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
            jPanel4.setLayout(jPanel4Layout);
            jPanel4Layout.setHorizontalGroup(
                    jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addGap(223, 223, 223)
                                    .addComponent(jButton_login)
                                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            jPanel4Layout.setVerticalGroup(
                    jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(jButton_login)
                                    .addGap(0, 0, Short.MAX_VALUE))
            );

            jPanel3.setMaximumSize(new java.awt.Dimension(538, 86));
            jPanel3.setMinimumSize(new java.awt.Dimension(538, 86));
            jPanel3.setPreferredSize(new java.awt.Dimension(538, 86));

            jLabel_ligar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            jLabel_ligar.setText("A ligar...");

            javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
            jPanel3.setLayout(jPanel3Layout);
            jPanel3Layout.setHorizontalGroup(
                    jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel_ligar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE)
            );
            jPanel3Layout.setVerticalGroup(
                    jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel_ligar)
                                    .addGap(0, 0, Short.MAX_VALUE))
            );

            jButton_reg.setText("Registar");
            jButton_reg.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton_regActionPerformed();
                }
            });

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                    .addGap(80, 80, 80))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                            .addComponent(jButton_ip, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(jButton_reg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                    .addGap(20, 20, 20))))
            );
            layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(jButton_ip)
                                    .addGap(26, 26, 26)
                                    .addComponent(jButton_reg)
                                    .addGap(93, 93, 93)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(1, 1, 1)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addContainerGap())
            );
        }
    }

    public class Menu_Principal{

        Menu_Principal(String id, String nome) {
            user_id = tryParse(id);
            user_nome = nome;

            initComponents();


            thread_s.addList(new Mensagem(4,user_id));
        }

        @SuppressWarnings("unchecked")
        private void initComponents() {
            jLabel16 = new javax.swing.JLabel();
            jPanel20 = new javax.swing.JPanel();
            jTabbedPane_menu = new javax.swing.JTabbedPane();
            jPanel_see_topic = new javax.swing.JPanel();
            jScrollPane_topico = new javax.swing.JScrollPane();
            jPanel_topic = new javax.swing.JPanel();
            jScrollPane_idea = new javax.swing.JScrollPane();
            jPanel_topic_idea = new javax.swing.JPanel();
            jPanel_new_idea = new javax.swing.JPanel();
            jPanel8 = new javax.swing.JPanel();
            jLabel_creat_topic = new javax.swing.JLabel();
            jLabel_creat_idea = new javax.swing.JLabel();
            jLabel_creat_msg = new javax.swing.JLabel();
            jComboBox_creat = new javax.swing.JComboBox();
            jTextField_creat_topic = new javax.swing.JTextField();
            jTextField_creat_idea = new javax.swing.JTextField();
            jLabel5_creat_nota1 = new javax.swing.JLabel();
            jScrollPane3 = new javax.swing.JScrollPane();
            jTextArea_creat_msg = new javax.swing.JTextArea();
            jLabel_creat_anexo = new javax.swing.JLabel();
            jTextField_creat_anexo = new javax.swing.JTextField();
            jLabel_creat_nota2 = new javax.swing.JLabel();
            jButton_creat = new javax.swing.JButton();
            jLabel_creat_aviso = new javax.swing.JLabel();
            jLabel_creat_share = new javax.swing.JLabel();
            jTextField_creat_share = new javax.swing.JTextField();
            jLabel_creat_preco = new javax.swing.JLabel();
            jTextField_creat_preco = new javax.swing.JTextField();
            jPanel_buy_idea = new javax.swing.JPanel();
            jLabel_buy_id = new javax.swing.JLabel();
            jTextField_buy_id = new javax.swing.JTextField();
            jButton_buy_search = new javax.swing.JButton();
            jScrollPane8 = new javax.swing.JScrollPane();
            jPanel_buy = new javax.swing.JPanel();
            jLabel_buy_error = new javax.swing.JLabel();
            jPanel_pedidos = new javax.swing.JPanel();
            jLabel_pedido = new javax.swing.JLabel();
            jLabel_pedido_id = new javax.swing.JLabel();
            jLabel_pedido_share = new javax.swing.JLabel();
            jLabel_pedido_preco = new javax.swing.JLabel();
            jTextField_pedido_id = new javax.swing.JTextField();
            jTextField_pedido_share = new javax.swing.JTextField();
            jTextField_pedido_preco = new javax.swing.JTextField();
            jButton_pedido = new javax.swing.JButton();
            jPanel_new_topic = new javax.swing.JPanel();
            jPanel6 = new javax.swing.JPanel();
            jLabel_topico = new javax.swing.JLabel();
            jTextField_topico = new javax.swing.JTextField();
            jButton_topico = new javax.swing.JButton();
            jLabel_new_topic_error = new javax.swing.JLabel();
            jPanel_our_idea = new javax.swing.JPanel();
            jScrollPane5 = new javax.swing.JScrollPane();
            jPanel_idea = new javax.swing.JPanel();
            jPanel_search_topic = new javax.swing.JPanel();
            jLabel_search_topic = new javax.swing.JLabel();
            jTextField_search_topic = new javax.swing.JTextField();
            jButton_search = new javax.swing.JButton();
            jPanel_search_panel = new javax.swing.JPanel();
            jLabel_search_name = new javax.swing.JLabel();
            jScrollPane6 = new javax.swing.JScrollPane();
            jPanel_search = new javax.swing.JPanel();
            jLabel_search_erro = new javax.swing.JLabel();
            jPanel_see_user = new javax.swing.JPanel();
            label_text_user_name = new java.awt.Label();
            label_text_user_id = new java.awt.Label();
            label_text_user_money = new java.awt.Label();
            label_user_name = new java.awt.Label();
            label_user_id = new java.awt.Label();
            label_user_money = new java.awt.Label();
            label_text_user_hist = new java.awt.Label();
            jScrollPane_see_user = new javax.swing.JScrollPane();
            jTextArea_see_user = new javax.swing.JTextArea();
            jPanel_fundo = new javax.swing.JPanel();
            jButton_desco = new javax.swing.JButton();
            jLabel_msg_fundo = new javax.swing.JLabel();

            jLabel16.setText("jLabel16");

            javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
            jPanel20.setLayout(jPanel20Layout);
            jPanel20Layout.setHorizontalGroup(
                    jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGap(0, 100, Short.MAX_VALUE)
            );
            jPanel20Layout.setVerticalGroup(
                    jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGap(0, 100, Short.MAX_VALUE)
            );

            jTabbedPane_menu.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    jTabbedPane_menuMouseClicked(evt);
                }
            });

            jScrollPane_topico.setViewportView(jPanel_topic);
            jScrollPane_idea.setViewportView(jPanel_topic_idea);

            javax.swing.GroupLayout jPanel_see_topicLayout = new javax.swing.GroupLayout(jPanel_see_topic);
            jPanel_see_topic.setLayout(jPanel_see_topicLayout);
            jPanel_see_topicLayout.setHorizontalGroup(
                    jPanel_see_topicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_see_topicLayout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(jScrollPane_topico, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jScrollPane_idea, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
                                    .addContainerGap())
            );
            jPanel_see_topicLayout.setVerticalGroup(
                    jPanel_see_topicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_see_topicLayout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(jPanel_see_topicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jScrollPane_idea, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                            .addComponent(jScrollPane_topico, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
            );

            jTabbedPane_menu.addTab("Visualizar Tópicos", jPanel_see_topic);

            jLabel_creat_topic.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            jLabel_creat_topic.setText("ID do(s) Tópico(s):");

            jLabel_creat_idea.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            jLabel_creat_idea.setText("ID da(s) Ideia(s):");

            jLabel_creat_msg.setText("Mensagem:");

            jComboBox_creat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Neutro", "A Favor", "Contra"}));

            jTextField_creat_topic.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            jTextField_creat_topic.setText("<(id_topico1),(id_topico2),...>");
            jTextField_creat_topic.setMaximumSize(new java.awt.Dimension(249, 28));
            jTextField_creat_topic.setMinimumSize(new java.awt.Dimension(249, 28));

            jTextField_creat_idea.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            jTextField_creat_idea.setText("<(id_ideia1),(id_ideia2),...>");
            jTextField_creat_idea.setMaximumSize(new java.awt.Dimension(225, 28));
            jTextField_creat_idea.setMinimumSize(new java.awt.Dimension(225, 28));

            jLabel5_creat_nota1.setText("No caso da ideia estar apenas associada ao tópico, introduzir \"none\".");

            jTextArea_creat_msg.setColumns(20);
            jTextArea_creat_msg.setRows(5);
            jScrollPane3.setViewportView(jTextArea_creat_msg);

            jLabel_creat_anexo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            jLabel_creat_anexo.setText("Anexo(s):");

            jTextField_creat_anexo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            jTextField_creat_anexo.setText("<(Path_Anexo)>");
            jTextField_creat_anexo.setMaximumSize(new java.awt.Dimension(293, 28));
            jTextField_creat_anexo.setMinimumSize(new java.awt.Dimension(293, 28));

            jLabel_creat_nota2.setText("No caso da ideia não possuir anexos, introduzir \"none\".");

            jButton_creat.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
            jButton_creat.setText("Enviar");
            jButton_creat.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton_creatActionPerformed(evt);
                }
            });

            jLabel_creat_aviso.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
            jLabel_creat_aviso.setForeground(new java.awt.Color(102, 102, 102));
            jLabel_creat_aviso.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            jLabel_creat_aviso.setText("Error 404!");

            jLabel_creat_share.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            jLabel_creat_share.setText("Número de Shares:");

            jTextField_creat_share.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            jTextField_creat_share.setText("100");

            jLabel_creat_preco.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            jLabel_creat_preco.setText("Preço por Share:");

            jTextField_creat_preco.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            jTextField_creat_preco.setText("2");

            javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
            jPanel8.setLayout(jPanel8Layout);
            jPanel8Layout.setHorizontalGroup(
                    jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                                    .addGap(91, 91, 91)
                                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jButton_creat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGroup(jPanel8Layout.createSequentialGroup()
                                                    .addGap(8, 8, 8)
                                                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jLabel_creat_topic)
                                                            .addComponent(jLabel_creat_idea)
                                                            .addGroup(jPanel8Layout.createSequentialGroup()
                                                                    .addComponent(jComboBox_creat, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addGap(18, 18, 18)
                                                                    .addComponent(jLabel_creat_msg))
                                                            .addComponent(jLabel_creat_share, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(jLabel_creat_anexo)
                                                            .addComponent(jLabel_creat_preco, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel_creat_nota2, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
                                            .addComponent(jLabel5_creat_nota1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel_creat_aviso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jTextField_creat_anexo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jTextField_creat_idea, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jTextField_creat_topic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jScrollPane3)
                                            .addComponent(jTextField_creat_share)
                                            .addComponent(jTextField_creat_preco))
                                    .addGap(49, 49, 49))
            );
            jPanel8Layout.setVerticalGroup(
                    jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jButton_creat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel_creat_aviso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGap(28, 28, 28)
                                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel_creat_topic)
                                            .addComponent(jTextField_creat_topic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel_creat_idea)
                                            .addComponent(jTextField_creat_idea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel5_creat_nota1)
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(jComboBox_creat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel_creat_msg))
                                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jTextField_creat_anexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel_creat_anexo))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel_creat_nota2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel_creat_share)
                                            .addGroup(jPanel8Layout.createSequentialGroup()
                                                    .addComponent(jTextField_creat_share, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                            .addComponent(jTextField_creat_preco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(jLabel_creat_preco))))
                                    .addContainerGap(21, Short.MAX_VALUE))
            );

            javax.swing.GroupLayout jPanel_new_ideaLayout = new javax.swing.GroupLayout(jPanel_new_idea);
            jPanel_new_idea.setLayout(jPanel_new_ideaLayout);
            jPanel_new_ideaLayout.setHorizontalGroup(
                    jPanel_new_ideaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_new_ideaLayout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addContainerGap())
            );
            jPanel_new_ideaLayout.setVerticalGroup(
                    jPanel_new_ideaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );

            jTabbedPane_menu.addTab("Submeter Ideia", jPanel_new_idea);

            jLabel_buy_id.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            jLabel_buy_id.setText("ID da Ideia");

            jTextField_buy_id.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

            jButton_buy_search.setText("Pesquisar");
            jButton_buy_search.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton_buy_searchActionPerformed(evt);
                }
            });
            jScrollPane8.setViewportView(jPanel_buy);

            jLabel_buy_error.setForeground(new java.awt.Color(102, 102, 102));
            jLabel_buy_error.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            jLabel_buy_error.setText("");

            jPanel_pedidos.setBorder(javax.swing.BorderFactory.createEtchedBorder());
            jLabel_pedido.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
            jLabel_pedido.setText("Pedido do Compra de Shares");
            jLabel_pedido_id.setText("ID da Ideia");
            jLabel_pedido_share.setText("Nº de Shares");
            jLabel_pedido_preco.setText("Preço(C/N)");
            jButton_pedido.setText("OK");
            jButton_pedido.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton_pedidoActionPerformed(evt);
                }
            });

            javax.swing.GroupLayout jPanel_pedidosLayout = new javax.swing.GroupLayout(jPanel_pedidos);
            jPanel_pedidos.setLayout(jPanel_pedidosLayout);
            jPanel_pedidosLayout.setHorizontalGroup(
                    jPanel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_pedidosLayout.createSequentialGroup()
                                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_pedidosLayout.createSequentialGroup()
                                                    .addGroup(jPanel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                            .addGroup(jPanel_pedidosLayout.createSequentialGroup()
                                                                    .addGap(10, 10, 10)
                                                                    .addGroup(jPanel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                            .addComponent(jLabel_pedido_id)
                                                                            .addComponent(jLabel_pedido_share)
                                                                            .addComponent(jLabel_pedido_preco))
                                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                    .addGroup(jPanel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                            .addComponent(jTextField_pedido_preco, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                                                                            .addGroup(jPanel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                                    .addComponent(jTextField_pedido_share, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                                                                                    .addComponent(jTextField_pedido_id))))
                                                            .addComponent(jLabel_pedido))
                                                    .addContainerGap())
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_pedidosLayout.createSequentialGroup()
                                                    .addComponent(jButton_pedido, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(48, 48, 48))))
            );
            jPanel_pedidosLayout.setVerticalGroup(
                    jPanel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_pedidosLayout.createSequentialGroup()
                                    .addComponent(jLabel_pedido)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel_pedido_id)
                                            .addComponent(jTextField_pedido_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel_pedido_share)
                                            .addComponent(jTextField_pedido_share, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel_pedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel_pedido_preco)
                                            .addComponent(jTextField_pedido_preco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                                    .addComponent(jButton_pedido, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addContainerGap())
            );

            javax.swing.GroupLayout jPanel_buy_ideaLayout = new javax.swing.GroupLayout(jPanel_buy_idea);
            jPanel_buy_idea.setLayout(jPanel_buy_ideaLayout);
            jPanel_buy_ideaLayout.setHorizontalGroup(
                    jPanel_buy_ideaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_buy_ideaLayout.createSequentialGroup()
                                    .addGroup(jPanel_buy_ideaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel_buy_ideaLayout.createSequentialGroup()
                                                    .addContainerGap()
                                                    .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 722, Short.MAX_VALUE))
                                            .addGroup(jPanel_buy_ideaLayout.createSequentialGroup()
                                                    .addGap(50, 50, 50)
                                                    .addGroup(jPanel_buy_ideaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jPanel_buy_ideaLayout.createSequentialGroup()
                                                                    .addGap(120, 120, 120)
                                                                    .addComponent(jButton_buy_search))
                                                            .addComponent(jLabel_buy_error, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addGroup(jPanel_buy_ideaLayout.createSequentialGroup()
                                                                    .addComponent(jLabel_buy_id)
                                                                    .addGap(75, 75, 75)
                                                                    .addComponent(jTextField_buy_id, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(jPanel_pedidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addContainerGap())
            );
            jPanel_buy_ideaLayout.setVerticalGroup(
                    jPanel_buy_ideaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_buy_ideaLayout.createSequentialGroup()
                                    .addGroup(jPanel_buy_ideaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel_buy_ideaLayout.createSequentialGroup()
                                                    .addGap(16, 16, 16)
                                                    .addComponent(jPanel_pedidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(17, 17, 17))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_buy_ideaLayout.createSequentialGroup()
                                                    .addContainerGap()
                                                    .addGroup(jPanel_buy_ideaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                            .addComponent(jLabel_buy_id)
                                                            .addComponent(jTextField_buy_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(jLabel_buy_error, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addComponent(jButton_buy_search)
                                                    .addGap(37, 37, 37)))
                                    .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
                                    .addContainerGap())
            );

            jTabbedPane_menu.addTab("Comprar Ideias", jPanel_buy_idea);

            jPanel6.setMaximumSize(new java.awt.Dimension(574, 320));
            jPanel6.setMinimumSize(new java.awt.Dimension(574, 320));

            jLabel_topico.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            jLabel_topico.setText("Nome do Tópico:");

            jTextField_topico.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

            jButton_topico.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
            jButton_topico.setText("Criar Tópico");
            jButton_topico.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton_topicoActionPerformed(evt);
                }
            });

            jLabel_new_topic_error.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
            jLabel_new_topic_error.setForeground(new java.awt.Color(102, 102, 102));
            jLabel_new_topic_error.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            jLabel_new_topic_error.setText("Error 404!");

            javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
            jPanel6.setLayout(jPanel6Layout);
            jPanel6Layout.setHorizontalGroup(
                    jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                    .addGap(93, 93, 93)
                                    .addComponent(jLabel_topico)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 127, Short.MAX_VALUE)
                                    .addComponent(jTextField_topico, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(69, 69, 69))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                                    .addComponent(jButton_topico)
                                                    .addGap(279, 279, 279))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                                    .addComponent(jLabel_new_topic_error, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(134, 134, 134))))
            );
            jPanel6Layout.setVerticalGroup(
                    jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                    .addGap(81, 81, 81)
                                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel_topico)
                                            .addComponent(jTextField_topico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 112, Short.MAX_VALUE)
                                    .addComponent(jButton_topico)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel_new_topic_error, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addContainerGap())
            );

            javax.swing.GroupLayout jPanel_new_topicLayout = new javax.swing.GroupLayout(jPanel_new_topic);
            jPanel_new_topic.setLayout(jPanel_new_topicLayout);
            jPanel_new_topicLayout.setHorizontalGroup(
                    jPanel_new_topicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );
            jPanel_new_topicLayout.setVerticalGroup(
                    jPanel_new_topicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_new_topicLayout.createSequentialGroup()
                                    .addGap(57, 57, 57)
                                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addContainerGap(179, Short.MAX_VALUE))
            );

            jTabbedPane_menu.addTab("Criar Novo Tópico", jPanel_new_topic);
            jScrollPane5.setViewportView(jPanel_idea);

            javax.swing.GroupLayout jPanel_our_ideaLayout = new javax.swing.GroupLayout(jPanel_our_idea);
            jPanel_our_idea.setLayout(jPanel_our_ideaLayout);
            jPanel_our_ideaLayout.setHorizontalGroup(
                    jPanel_our_ideaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_our_ideaLayout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 722, Short.MAX_VALUE)
                                    .addContainerGap())
            );
            jPanel_our_ideaLayout.setVerticalGroup(
                    jPanel_our_ideaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_our_ideaLayout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
                                    .addContainerGap())
            );

            jTabbedPane_menu.addTab("As nossas Ideias", jPanel_our_idea);

            jLabel_search_topic.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            jLabel_search_topic.setText("ID do Tópico");

            jTextField_search_topic.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

            jButton_search.setText("Pesquisar");
            jButton_search.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton_searchActionPerformed(evt);
                }
            });

            jLabel_search_name.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            jLabel_search_name.setText("name");

            javax.swing.GroupLayout jPanel_searchLayout = new javax.swing.GroupLayout(jPanel_search);
            jPanel_search.setLayout(jPanel_searchLayout);
            jPanel_searchLayout.setHorizontalGroup(
                    jPanel_searchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGap(0, 665, Short.MAX_VALUE)
            );
            jPanel_searchLayout.setVerticalGroup(
                    jPanel_searchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGap(0, 343, Short.MAX_VALUE)
            );

            jScrollPane6.setViewportView(jPanel_search);

            javax.swing.GroupLayout jPanel_search_panelLayout = new javax.swing.GroupLayout(jPanel_search_panel);
            jPanel_search_panel.setLayout(jPanel_search_panelLayout);
            jPanel_search_panelLayout.setHorizontalGroup(
                    jPanel_search_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_search_panelLayout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(jPanel_search_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel_search_name, javax.swing.GroupLayout.DEFAULT_SIZE, 667, Short.MAX_VALUE)
                                            .addComponent(jScrollPane6))
                                    .addContainerGap())
            );
            jPanel_search_panelLayout.setVerticalGroup(
                    jPanel_search_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_search_panelLayout.createSequentialGroup()
                                    .addGap(29, 29, 29)
                                    .addComponent(jLabel_search_name)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
                                    .addContainerGap())
            );

            jLabel_search_erro.setForeground(new java.awt.Color(102, 102, 102));
            jLabel_search_erro.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            jLabel_search_erro.setText("");

            javax.swing.GroupLayout jPanel_search_topicLayout = new javax.swing.GroupLayout(jPanel_search_topic);
            jPanel_search_topic.setLayout(jPanel_search_topicLayout);
            jPanel_search_topicLayout.setHorizontalGroup(
                    jPanel_search_topicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_search_topicLayout.createSequentialGroup()
                                    .addGroup(jPanel_search_topicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel_search_topicLayout.createSequentialGroup()
                                                    .addGap(309, 309, 309)
                                                    .addComponent(jButton_search))
                                            .addGroup(jPanel_search_topicLayout.createSequentialGroup()
                                                    .addGap(38, 38, 38)
                                                    .addComponent(jPanel_search_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel_search_topicLayout.createSequentialGroup()
                                                    .addGap(163, 163, 163)
                                                    .addGroup(jPanel_search_topicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                            .addComponent(jLabel_search_erro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addGroup(jPanel_search_topicLayout.createSequentialGroup()
                                                                    .addComponent(jLabel_search_topic)
                                                                    .addGap(101, 101, 101)
                                                                    .addComponent(jTextField_search_topic, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                    .addContainerGap(17, Short.MAX_VALUE))
            );
            jPanel_search_topicLayout.setVerticalGroup(
                    jPanel_search_topicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_search_topicLayout.createSequentialGroup()
                                    .addGap(42, 42, 42)
                                    .addGroup(jPanel_search_topicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel_search_topic)
                                            .addComponent(jTextField_search_topic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel_search_erro, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton_search)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jPanel_search_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addContainerGap())
            );

            jTabbedPane_menu.addTab("Procurar Tópico", jPanel_search_topic);

            label_text_user_name.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
            label_text_user_name.setText("Nome:");

            label_text_user_id.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
            label_text_user_id.setText("ID:");

            label_text_user_money.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
            label_text_user_money.setText("Dinheiro:");

            label_user_name.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
            label_user_name.setText("Username");

            label_user_id.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
            label_user_id.setText("id");

            label_user_money.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
            label_user_money.setText("money");

            label_text_user_hist.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
            label_text_user_hist.setText("Histórico:");

            jTextArea_see_user.setEditable(false);
            jTextArea_see_user.setColumns(20);
            jTextArea_see_user.setRows(5);
            jScrollPane_see_user.setViewportView(jTextArea_see_user);

            javax.swing.GroupLayout jPanel_see_userLayout = new javax.swing.GroupLayout(jPanel_see_user);
            jPanel_see_user.setLayout(jPanel_see_userLayout);
            jPanel_see_userLayout.setHorizontalGroup(
                    jPanel_see_userLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_see_userLayout.createSequentialGroup()
                                    .addGap(50, 50, 50)
                                    .addGroup(jPanel_see_userLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jScrollPane_see_user)
                                            .addGroup(jPanel_see_userLayout.createSequentialGroup()
                                                    .addGroup(jPanel_see_userLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                            .addComponent(label_text_user_hist, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                                                            .addComponent(label_text_user_name, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(label_text_user_id, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(label_text_user_money, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addGroup(jPanel_see_userLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                            .addComponent(label_user_name, javax.swing.GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE)
                                                            .addComponent(label_user_id, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(label_user_money, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                    .addContainerGap(41, Short.MAX_VALUE))
            );
            jPanel_see_userLayout.setVerticalGroup(
                    jPanel_see_userLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_see_userLayout.createSequentialGroup()
                                    .addGap(30, 30, 30)
                                    .addGroup(jPanel_see_userLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(jPanel_see_userLayout.createSequentialGroup()
                                                    .addGroup(jPanel_see_userLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                            .addComponent(label_text_user_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(label_user_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(label_text_user_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(label_user_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel_see_userLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(label_text_user_money, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label_user_money, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(label_text_user_hist, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jScrollPane_see_user, javax.swing.GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE)
                                    .addContainerGap())
            );

            jTabbedPane_menu.addTab("Utilizador", jPanel_see_user);

            jPanel_fundo.setMaximumSize(new java.awt.Dimension(157, 23));
            jPanel_fundo.setMinimumSize(new java.awt.Dimension(157, 23));

            jButton_desco.setText("Desconectar");
            jButton_desco.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton_descoActionPerformed(evt);
                }
            });

            jLabel_msg_fundo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            jLabel_msg_fundo.setText("");

            javax.swing.GroupLayout jPanel_fundoLayout = new javax.swing.GroupLayout(jPanel_fundo);
            jPanel_fundo.setLayout(jPanel_fundoLayout);
            jPanel_fundoLayout.setHorizontalGroup(
                    jPanel_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_fundoLayout.createSequentialGroup()
                                    .addComponent(jLabel_msg_fundo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jButton_desco)
                                    .addContainerGap())
            );
            jPanel_fundoLayout.setVerticalGroup(
                    jPanel_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_fundoLayout.createSequentialGroup()
                                    .addGap(0, 0, Short.MAX_VALUE)
                                    .addGroup(jPanel_fundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jButton_desco)
                                            .addComponent(jLabel_msg_fundo, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
            );

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(jPanel_fundo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addContainerGap())
                            .addComponent(jTabbedPane_menu)
            );
            layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                    .addComponent(jTabbedPane_menu)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jPanel_fundo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            );

            jLabel_new_topic_error.setVisible(false);
            jLabel_creat_aviso.setVisible(false);
            label_user_name.setText(user_nome);
            label_user_id.setText(Integer.toString(user_id));
        }

        private void jButton_descoActionPerformed(java.awt.event.ActionEvent evt) {
            //close all!
            jPanel_topic.removeAll();
            jPanel_topic_idea.removeAll();
            jPanel_buy.removeAll();
            jPanel_idea.removeAll();
            jPanel_new_idea.removeAll();
            jPanel_search.removeAll();
            jPanel_search_panel.removeAll();
            jPanel_see_topic.removeAll();
            jPanel_buy_idea.removeAll();
            jPanel_new_topic.removeAll();
            jPanel_our_idea.removeAll();
            jPanel_search_topic.removeAll();
            jPanel_fundo.removeAll();
            jTabbedPane_menu.removeAll();
            remove(jPanel_fundo);
            remove(jTabbedPane_menu);

            thread_s.addList(new Mensagem(16,user_id));
        }

        private void jButton_pedidoActionPerformed(java.awt.event.ActionEvent evt) {
            boolean cond = false;
            if(!"".equals(jTextField_pedido_id.getText().replace(" ","")) && !"".equals(jTextField_pedido_preco.getText().replace(" ","")) && !"".equals(jTextField_pedido_share.getText().replace(" ",""))){
                String[] aux = jTextField_pedido_preco.getText().split("/");
                int id = tryParse(jTextField_pedido_id.getText()), share = tryParse(jTextField_pedido_share.getText());
                if(aux.length == 2){
                    int preco_c = tryParse(aux[0]), preco_n = tryParse(aux[1]);
                    if("0".equals(jTextField_pedido_share.getText()) || share > 0)
                        cond = true;
                    if(id > 0 && preco_c > 0 && preco_n > 0 && cond == true){
                        Mensagem msg = new Mensagem(17,user_id);
                        msg.addList(Integer.toString(id));
                        msg.addList(Integer.toString(share));
                        msg.addList(Integer.toString(preco_n));
                        msg.addList(Integer.toString(preco_c));
                        thread_s.addList(msg);
                    }
                }
            }
            jTextField_pedido_id.setText("");
            jTextField_pedido_preco.setText("");
            jTextField_pedido_share.setText("");
        }

        private void jButton_topicoActionPerformed(java.awt.event.ActionEvent evt) {
            Mensagem msg = new Mensagem(3,user_id);
            msg.addList(jTextField_topico.getText());
            thread_s.addList(msg);
        }

        private void jButton_searchActionPerformed(java.awt.event.ActionEvent evt) {
            Mensagem msg = new Mensagem(13,user_id);
            msg.addList(jTextField_search_topic.getText());
            thread_s.addList(msg);
        }

        private void jButton_buy_searchActionPerformed(java.awt.event.ActionEvent evt) {
            ArrayList<javax.swing.JPanel> list_aux = new ArrayList<javax.swing.JPanel>();
            Mensagem aux_msg = new Mensagem(8,user_id);
            String id_ideia = jTextField_buy_id.getText();
            aux_msg.addList(id_ideia);
            thread_s.addList(aux_msg);
        }

        private void jButton_creatActionPerformed(java.awt.event.ActionEvent evt) {
            String topico = jTextField_creat_topic.getText();
            String ideia = jTextField_creat_idea.getText();
            String opiniao = (String)jComboBox_creat.getSelectedItem();
            String msg = jTextArea_creat_msg.getText();
            String file = jTextField_creat_anexo.getText();
            int nr_share = tryParse(jTextField_creat_share.getText());
            int preco_share = tryParse(jTextField_creat_preco.getText());

            if("".equals(topico.replace(" ", "")) || "".equals(ideia.replace(" ", "")) || "".equals(msg.replace(" ", "")) || "".equals(file.replace(" ", ""))|| nr_share <= 0 || preco_share <= 0){
                jLabel_creat_aviso.setText("Erro num dos campos.");
            }
            else{
                Mensagem msg_aux = new Mensagem(6,user_id);
                if("none".equals(ideia))
                    ideia = "";

                if(!"none".equals(file))
    			    msg_aux.addFile(file);

                //msg_aux.addList(opiniao);
                msg_aux.addList(msg);
                //msg_aux.addList(ideia);
                msg_aux.addList(topico);
                msg_aux.addList(Integer.toString(user_id));
                msg_aux.addList(Integer.toString(nr_share));
                msg_aux.addList(Integer.toString(preco_share));
                thread_s.addList(msg_aux);
            }
        }

        private Integer tryParse(String text) {
            try {
                return new Integer(text);
            } catch (NumberFormatException e) {
                return 0;
            }
        }

        private void jTabbedPane_menuMouseClicked(java.awt.event.MouseEvent evt) {
            int aux = jTabbedPane_menu.getSelectedIndex();
            System.out.println("Clicou na aba número " + aux);
            if(aux == 0){
                thread_s.addList(new Mensagem(4, user_id));
                jPanel_topic_idea.removeAll();
            }
            else if(aux == 4) {
                thread_s.addList(new Mensagem(11,user_id));
            }
            else if(aux == 6) {
                thread_s.addList(new Mensagem(10,user_id));
                jLabel_msg_fundo.setText("");
            }
        }
    }

    public class SendNtf extends  Thread{
        SendNtf() {
            this.start();
        }

        public void run() {
            while(true){
                try {
                    Thread.sleep(60000);
                    thread_s.addList(new Mensagem(14,user_id));
                } catch (InterruptedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }
    }

    public class SendMsg extends Thread{
        private CopyOnWriteArrayList<Mensagem> listaEspera;

        SendMsg() {
            listaEspera = new CopyOnWriteArrayList<Mensagem>();
            this.start();
        }

        public void run() {
            int cont = 3;
            Mensagem aux;
            while(true){
                if(listaEspera.size() > 0){
                    try {
                        aux = listaEspera.get(0);
                        System.out.println("Vou enviar " + aux);
                        oos.writeObject(aux);
                        oos.flush();
                        thread_r.addList(aux);
                        listaEspera.remove(aux);
                        cont = 3;
                    } catch (IOException e) {
                        cont--;
                        if(cont == 0){
                            if(server == ip_server1)
                                server = ip_server2;
                            else
                                server = ip_server1;
                            System.out.println("server: " + server);
                            criaLigacao();
                            cont = 3;
                        }
                    }

                }
            }
        }

        public void addList(Mensagem aux) {
            if(listaEspera.indexOf(aux)==-1)
                listaEspera.add(aux);
        }

        private void criaLigacao() {
            int cont = 5;
            while(cont > 0){
                try {
                    if(s != null)
                        s.close();
                    s = new Socket(server, port);
                    s.setSoTimeout(5000);
                    oos = new ObjectOutputStream(s.getOutputStream());
                    ois = new ObjectInputStream(s.getInputStream());
                    cont = 0;
                } catch (UnknownHostException e) {
                    System.out.println("Erro no Sock");
                } catch (EOFException e) {
                    System.out.println("Erro no EOF");
                } catch (IOException e) {
                    System.out.println("Erro na ligação");
                    cont--;
                }
            }
        }
    }

    public class ReceiveMsg extends Thread{
        private CopyOnWriteArrayList<Mensagem> listaEspera;

        ReceiveMsg (){
            listaEspera = new CopyOnWriteArrayList<Mensagem>();
            this.start();
        }

        public void run() {
            boolean bool = true;
            Mensagem aux2 = null;
            int cond = 1;
            while(true){
                try{
                    aux2 = null;
                    aux2 = (Mensagem) ois.readObject();
                    cond = 10;
                    Output(aux2);
                }catch(SocketTimeoutException e){
                    if(listaEspera.size() > 0 && aux2 == null){
                        cond--;
                        if(cond <= 0){
                            thread_s.addList(listaEspera.get(0));
                            listaEspera.remove(0);
                            System.out.println("Apagou");
                            cond = 1;
                        }
                    }
                    aux2 = null;
                } catch (ClassNotFoundException e) {
                    if(listaEspera.size() > 0 && aux2 == null){
                        cond--;
                        if(cond <= 0){
                            thread_s.addList(listaEspera.get(0));
                            listaEspera.remove(0);
                            System.out.println("Apagou");
                            cond = 1;
                        }
                    }
                    aux2 = null;
                } catch (IOException e) {
                    if(bool){
                        System.out.println("O socket fechou " + e);
                        bool = false;
                    }
                    if(listaEspera.size() > 0 && aux2 == null){
                        cond--;
                        if(cond <= 0){
                            thread_s.addList(listaEspera.get(0));
                            listaEspera.remove(0);
                            System.out.println("Apagou");
                            cond = 1;
                        }
                    }
                    aux2 = null;

                }

            }
        }

        public void addList(Mensagem aux) {
            listaEspera.add(aux);
        }

        private Integer tryParse(String text) {
            try {
                return new Integer(text);
            } catch (NumberFormatException e) {
                return 0;
            }
        }

        private javax.swing.JPanel add_topic_visual(final int id, String nome) {
            javax.swing.JPanel jPanel_aux;
            javax.swing.JLabel jLabel_name;
            javax.swing.JLabel jLabel_text_id;
            javax.swing.JLabel jLabel_id;
            javax.swing.JButton jButton_visualiza;

            jPanel_aux = new javax.swing.JPanel();
            jLabel_name = new javax.swing.JLabel();
            jLabel_text_id = new javax.swing.JLabel();
            jLabel_id = new javax.swing.JLabel();
            jButton_visualiza = new javax.swing.JButton();

            jLabel_name.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            jLabel_name.setText(nome);
            jLabel_text_id.setText("ID: ");
            jLabel_id.setText(Integer.toString(id));

            jButton_visualiza.setText("Visualizar");
            jButton_visualiza.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton_visualizaActionPerformed(evt, id);
                }
            });

            jPanel_aux.setBorder(javax.swing.BorderFactory.createEtchedBorder());

            javax.swing.GroupLayout jPanel_auxLayout = new javax.swing.GroupLayout(jPanel_aux);
            jPanel_aux.setLayout(jPanel_auxLayout);
            jPanel_auxLayout.setHorizontalGroup(
                    jPanel_auxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_auxLayout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(jPanel_auxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel_auxLayout.createSequentialGroup()
                                                    .addComponent(jLabel_text_id)
                                                    .addGap(52, 52, 52)
                                                    .addComponent(jLabel_id, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGroup(jPanel_auxLayout.createSequentialGroup()
                                                    .addComponent(jLabel_name, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                                                    .addComponent(jButton_visualiza)
                                                    .addGap(26, 26, 26))))
            );
            jPanel_auxLayout.setVerticalGroup(
                    jPanel_auxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_auxLayout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(jPanel_auxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel_name)
                                            .addComponent(jButton_visualiza))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(jPanel_auxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel_text_id)
                                            .addComponent(jLabel_id))
                                    .addContainerGap(50, Short.MAX_VALUE))
            );
            return jPanel_aux;
        }

        private void jButton_visualizaActionPerformed(java.awt.event.ActionEvent evt, int id_topic) {
            Mensagem aux_msg = new Mensagem(5,user_id);
            aux_msg.addList(Integer.toString(id_topic));
            thread_s.addList(aux_msg);
        }

        private javax.swing.JPanel add_buy_idea(final int idideia, final int iduser, final int preco, int nr_share) {
            javax.swing.JPanel jPanel_idea_buy = new javax.swing.JPanel();
            javax.swing.JLabel jLabel_text_nr_share = new javax.swing.JLabel();
            javax.swing.JLabel jLabel_nr_share = new javax.swing.JLabel();
            javax.swing.JLabel jLabel_text_price_share = new javax.swing.JLabel();
            javax.swing.JLabel jLabel_price_share = new javax.swing.JLabel();
            javax.swing.JLabel jLabel_nr_buy_share = new javax.swing.JLabel();
            javax.swing.JLabel jLabel_new_price_share = new javax.swing.JLabel();
            final javax.swing.JTextField jTextField_nr_buy_share = new javax.swing.JTextField();
            final javax.swing.JTextField jTextField_new_price_share = new javax.swing.JTextField();
            javax.swing.JButton jButton_buy = new javax.swing.JButton();

            jPanel_idea_buy.setBorder(javax.swing.BorderFactory.createEtchedBorder());
            jLabel_text_nr_share.setText("Número de Shares");
            jLabel_nr_share.setText(Integer.toString(nr_share));
            jLabel_text_price_share.setText("Preço de cada Share");
            jLabel_price_share.setText(Integer.toString(preco));
            jLabel_nr_buy_share.setText("Número de Shares a comprar");
            jLabel_new_price_share.setText("Novo Preço de cada Share");
            jButton_buy.setText("Comprar");
            jButton_buy.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton_buyActionPerformed(evt, idideia, iduser, jTextField_nr_buy_share, jTextField_new_price_share, preco);
                }
            });

            javax.swing.GroupLayout jPanel_idea_buyLayout = new javax.swing.GroupLayout(jPanel_idea_buy);
            jPanel_idea_buy.setLayout(jPanel_idea_buyLayout);
            jPanel_idea_buyLayout.setHorizontalGroup(
                    jPanel_idea_buyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_idea_buyLayout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(jPanel_idea_buyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel_idea_buyLayout.createSequentialGroup()
                                                    .addComponent(jLabel_text_nr_share, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                                                    .addGap(18, 18, 18)
                                                    .addComponent(jLabel_nr_share, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel_idea_buyLayout.createSequentialGroup()
                                                    .addComponent(jLabel_text_price_share)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(jLabel_price_share, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGap(27, 27, 27)
                                    .addGroup(jPanel_idea_buyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel_nr_buy_share)
                                            .addComponent(jLabel_new_price_share))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel_idea_buyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTextField_nr_buy_share)
                                            .addComponent(jTextField_new_price_share, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(50, 50, 50)
                                    .addComponent(jButton_buy, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addContainerGap())
            );
            jPanel_idea_buyLayout.setVerticalGroup(
                    jPanel_idea_buyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_idea_buyLayout.createSequentialGroup()
                                    .addGroup(jPanel_idea_buyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel_idea_buyLayout.createSequentialGroup()
                                                    .addContainerGap()
                                                    .addGroup(jPanel_idea_buyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                            .addComponent(jLabel_text_nr_share)
                                                            .addComponent(jLabel_nr_share)
                                                            .addComponent(jLabel_nr_buy_share)
                                                            .addComponent(jTextField_nr_buy_share, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addGroup(jPanel_idea_buyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                            .addComponent(jLabel_text_price_share)
                                                            .addComponent(jLabel_price_share)
                                                            .addComponent(jLabel_new_price_share)
                                                            .addComponent(jTextField_new_price_share, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(jPanel_idea_buyLayout.createSequentialGroup()
                                                    .addGap(23, 23, 23)
                                                    .addComponent(jButton_buy)))
                                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            return jPanel_idea_buy;
        }

        private void jButton_buyActionPerformed(java.awt.event.ActionEvent evt, int idideia, int iduser, javax.swing.JTextField JText_nr_share, javax.swing.JTextField JText_new_price, int preco) {
            if(!"".equals(JText_nr_share.getText().replace(" ","")) && !"".equals(JText_new_price.getText().replace(" ",""))){
                if(tryParse(JText_nr_share.getText()) > 0 && tryParse(JText_new_price.getText()) > 0){
                    Mensagem msg_aux = new Mensagem(7,user_id);
                    //Mensagem msg_teste = new Mensagem(7,user_id);

                    msg_aux.addList(Integer.toString(iduser));
                    msg_aux.addList(Integer.toString(user_id));
                    msg_aux.addList(Integer.toString(idideia));
                    msg_aux.addList(JText_nr_share.getText());
                    msg_aux.addList(JText_new_price.getText());
                    msg_aux.addList(Integer.toString(preco));

                    /*msg_teste.addList(Integer.toString(iduser));
                    msg_teste.addList(Integer.toString(user_id));
                    msg_teste.addList(Integer.toString(idideia));
                    msg_teste.addList(JText_nr_share.getText());
                    msg_teste.addList(JText_new_price.getText());
                    msg_teste.addList(Integer.toString(preco));
                    msg_teste.setData(msg_aux.getData()); */

                    JText_nr_share.setText("");
                    JText_new_price.setText("");

                    thread_s.addList(msg_aux);
                    //thread_s.addList(msg_teste);
                }
            }
        }

        private void atualiza_jpanel(JPanel JPanel_aux, ArrayList<JPanel> List_aux) {
            JPanel_aux.removeAll();
            int i, length = List_aux.size();

            javax.swing.GroupLayout JPanel_auxLayout;
            JPanel_auxLayout = new javax.swing.GroupLayout(JPanel_aux);
            JPanel_aux.setLayout(JPanel_auxLayout);

            GroupLayout.ParallelGroup aux_group_h = JPanel_auxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
            for(i=0; i<length; i++)
                aux_group_h.addComponent(List_aux.get(i), javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
            GroupLayout.SequentialGroup aux_sequentialgroup_h = JPanel_auxLayout.createSequentialGroup();
            aux_sequentialgroup_h.addContainerGap();
            aux_sequentialgroup_h.addGroup(aux_group_h);
            aux_sequentialgroup_h.addContainerGap();

            JPanel_auxLayout.setHorizontalGroup(
                    JPanel_auxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(aux_sequentialgroup_h)
            );

            GroupLayout.SequentialGroup aux_Seq_vert = JPanel_auxLayout.createSequentialGroup();
            aux_Seq_vert.addContainerGap();
            for(i=0; i<length; i++){
                aux_Seq_vert.addComponent(List_aux.get(i), javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE);
                if(!(i == length-1)){
                    aux_Seq_vert.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
                }
            }
            aux_Seq_vert.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
            //aux_Seq_vert.addContainerGap(416, Short.MAX_VALUE);

            JPanel_auxLayout.setVerticalGroup(
                    JPanel_auxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(aux_Seq_vert)
            );

            JPanel_aux.revalidate();
            JPanel_aux.repaint();

            jTabbedPane_menu.revalidate();
            jTabbedPane_menu.repaint();

            revalidate();
            repaint();
        }

        private javax.swing.JPanel add_idea_visual(final int id, String msg, String opiniao) {
            javax.swing.JPanel jPanel_aux = new javax.swing.JPanel();
            javax.swing.JLabel jLabel_text_id = new javax.swing.JLabel();
            javax.swing.JLabel jLabel_id = new javax.swing.JLabel();
            javax.swing.JLabel jLabel_opiniao = new javax.swing.JLabel();
            javax.swing.JLabel jLabel_anexo = new javax.swing.JLabel();
            javax.swing.JScrollPane jScrollPane_msg = new javax.swing.JScrollPane();
            javax.swing.JTextArea jTextArea_msg = new javax.swing.JTextArea();
            final javax.swing.JTextField jTextField_anexo = new javax.swing.JTextField();
            javax.swing.JButton jButton_desc_anexos = new javax.swing.JButton();

            jLabel_text_id.setText("ID da Ideia");
            jLabel_id.setText(Integer.toString(id));
            jLabel_opiniao.setText(opiniao);
            jLabel_anexo.setText("Pretende guardar os Anexos:");
            jTextField_anexo.setText("<(Path)>");
            jTextArea_msg.setEditable(false);
            jTextArea_msg.setColumns(20);
            jTextArea_msg.setRows(5);
            jTextArea_msg.setText(msg);
            jScrollPane_msg.setViewportView(jTextArea_msg);

            jButton_desc_anexos.setText("Descarregar Anexos");
            jButton_desc_anexos.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton_desc_anexosActionPerformed(evt, id, jTextField_anexo);
                }
            });

            jPanel_aux.setBorder(javax.swing.BorderFactory.createEtchedBorder());

            javax.swing.GroupLayout jPanel_auxLayout = new javax.swing.GroupLayout(jPanel_aux);
            jPanel_aux.setLayout(jPanel_auxLayout);
            jPanel_auxLayout.setHorizontalGroup(
                    jPanel_auxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_auxLayout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(jPanel_auxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jScrollPane_msg)
                                            .addGroup(jPanel_auxLayout.createSequentialGroup()
                                                    .addGroup(jPanel_auxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jPanel_auxLayout.createSequentialGroup()
                                                                    .addComponent(jLabel_text_id)
                                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                    .addComponent(jLabel_id)
                                                                    .addGap(95, 95, 95)
                                                                    .addComponent(jLabel_opiniao))
                                                            .addGroup(jPanel_auxLayout.createSequentialGroup()
                                                                    .addComponent(jLabel_anexo)
                                                                    .addGap(18, 18, 18)
                                                                    .addComponent(jTextField_anexo, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                    .addGap(0, 0, Short.MAX_VALUE)))
                                    .addContainerGap())
                            .addGroup(jPanel_auxLayout.createSequentialGroup()
                                    .addGap(137, 137, 137)
                                    .addComponent(jButton_desc_anexos)
                                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            jPanel_auxLayout.setVerticalGroup(
                    jPanel_auxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_auxLayout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(jPanel_auxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel_text_id)
                                            .addComponent(jLabel_id)
                                            .addComponent(jLabel_opiniao))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jScrollPane_msg, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(jPanel_auxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel_anexo)
                                            .addComponent(jTextField_anexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jButton_desc_anexos)
                                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            return jPanel_aux;
        }

        private void jButton_desc_anexosActionPerformed(java.awt.event.ActionEvent evt, int idea_aux, javax.swing.JTextField jtext_anexo) {
            download_anexo(idea_aux, jtext_anexo.getText());
        }

        private javax.swing.JPanel add_our_idea(final int idIdeia, String msg, String opiniao, String totalShare, String preco, String nrShare, String topicos) {
            javax.swing.JPanel jPanel_aux = new javax.swing.JPanel();
            javax.swing.JLabel jLabel10 = new javax.swing.JLabel();
            javax.swing.JLabel jLabel11 = new javax.swing.JLabel();
            javax.swing.JLabel jLabel13 = new javax.swing.JLabel();
            javax.swing.JLabel jLabel14 = new javax.swing.JLabel();
            javax.swing.JLabel jLabel15 = new javax.swing.JLabel();
            javax.swing.JLabel jLabel18 = new javax.swing.JLabel();
            javax.swing.JLabel jLabel19 = new javax.swing.JLabel();
            javax.swing.JLabel jLabel20 = new javax.swing.JLabel();
            final javax.swing.JLabel jLabel21 = new javax.swing.JLabel();
            javax.swing.JScrollPane jScrollPane4 = new javax.swing.JScrollPane();
            javax.swing.JTextArea jTextArea2 = new javax.swing.JTextArea();
            final javax.swing.JTextField jTextField4 = new javax.swing.JTextField();
            final javax.swing.JTextField jTextField5 = new javax.swing.JTextField();
            javax.swing.JButton jButton_atualiza = new javax.swing.JButton();
            javax.swing.JButton jButton_apagar = new javax.swing.JButton();
            javax.swing.JButton jButton_anexo = new javax.swing.JButton();
            javax.swing.JTextArea jTextArea1 = new javax.swing.JTextArea();
            javax.swing.JScrollPane jScrollPane2 = new javax.swing.JScrollPane();

            jPanel_aux.setBorder(javax.swing.BorderFactory.createEtchedBorder());
            jLabel10.setText("ID da Ideia");
            jLabel11.setText("ID do Tópico(s)");
            jTextArea2.setEditable(false);
            jTextArea2.setColumns(20);
            jTextArea2.setRows(5);
            jTextArea2.setText(msg);
            jScrollPane4.setViewportView(jTextArea2);
            jLabel13.setText(opiniao);
            jLabel14.setText("Quantidade de Shares");
            jLabel15.setText(Integer.toString(idIdeia));

            jTextArea1.setText(topicos);
            jTextArea1.setEditable(false);
            jTextArea1.setColumns(20);
            jTextArea1.setRows(5);
            jTextArea1.setPreferredSize(new java.awt.Dimension(60, 60));
            jScrollPane2.setViewportView(jTextArea1);
            jScrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);


            jLabel18.setText(nrShare+"/"+totalShare);
            jLabel19.setText("Preço de cada Share");
            jLabel20.setText("Pretende guardar os Anexos:");
            jTextField4.setText(preco);
            jTextField5.setText("<(Path)>");
            jButton_atualiza.setText("Atualizar");
            jButton_atualiza.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton_atualizaActionPerformed(evt, idIdeia, jTextField4, jLabel21);
                }
            });
            jButton_apagar.setText("Apagar");
            jButton_apagar.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton_apagarActionPerformed(evt, idIdeia, jTextField4);
                }
            });
            jButton_anexo.setText("Descarregar");
            jButton_anexo.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton_desc_anexosActionPerformed(evt, idIdeia, jTextField5);
                }
            });

            jLabel21.setForeground(new java.awt.Color(102, 102, 102));
            jLabel21.setText("");
            javax.swing.GroupLayout jPanel_auxLayout = new javax.swing.GroupLayout(jPanel_aux);
            jPanel_aux.setLayout(jPanel_auxLayout);
            jPanel_auxLayout.setHorizontalGroup(
                    jPanel_auxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_auxLayout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(jPanel_auxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGroup(jPanel_auxLayout.createSequentialGroup()
                                                    .addGroup(jPanel_auxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                            .addGroup(jPanel_auxLayout.createSequentialGroup()
                                                                    .addComponent(jButton_atualiza)
                                                                    .addGap(29, 29, 29)
                                                                    .addComponent(jButton_apagar))
                                                            .addGroup(jPanel_auxLayout.createSequentialGroup()
                                                                    .addGroup(jPanel_auxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel_auxLayout.createSequentialGroup()
                                                                                    .addComponent(jLabel19)
                                                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                    .addComponent(jTextField4))
                                                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel_auxLayout.createSequentialGroup()
                                                                                    .addComponent(jLabel14)
                                                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                    .addComponent(jLabel18)))
                                                                    .addGap(35, 35, 35)
                                                                    .addComponent(jLabel20)))
                                                    .addGroup(jPanel_auxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jPanel_auxLayout.createSequentialGroup()
                                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                    .addComponent(jTextField5))
                                                            .addGroup(jPanel_auxLayout.createSequentialGroup()
                                                                    .addGap(33, 33, 33)
                                                                    .addComponent(jButton_anexo)
                                                                    .addGap(0, 0, Short.MAX_VALUE))))
                                            .addGroup(jPanel_auxLayout.createSequentialGroup()
                                                    .addGroup(jPanel_auxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jPanel_auxLayout.createSequentialGroup()
                                                                    .addComponent(jLabel10)
                                                                    .addGap(0, 0, Short.MAX_VALUE))
                                                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE))
                                                    .addGap(18, 18, 18)
                                                    .addGroup(jPanel_auxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
                                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addGroup(jPanel_auxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                            .addComponent(jScrollPane4)
                                                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addContainerGap())
            );
            jPanel_auxLayout.setVerticalGroup(
                    jPanel_auxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_auxLayout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(jPanel_auxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel10)
                                            .addGroup(jPanel_auxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(jLabel13)
                                                    .addComponent(jLabel15)))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel_auxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel11)
                                            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel_auxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel14)
                                            .addComponent(jLabel18)
                                            .addComponent(jLabel20)
                                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel_auxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel19)
                                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                                    .addGroup(jPanel_auxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jButton_atualiza)
                                            .addComponent(jButton_apagar)
                                            .addComponent(jButton_anexo))
                                    .addGap(4, 4, 4)
                                    .addComponent(jLabel21)
                                    .addContainerGap())
            );

            jScrollPane2.getAccessibleContext().setAccessibleName("");
            return jPanel_aux;
        }

        private void jButton_atualizaActionPerformed(java.awt.event.ActionEvent evt, int user_s_aux, javax.swing.JTextField JTextField_aux, javax.swing.JLabel jlabel_aux) {
            Mensagem msg = new Mensagem(12,user_id);
            if(tryParse(JTextField_aux.getText()) > 0){
                msg.addList(Integer.toString(user_s_aux));
                msg.addList(JTextField_aux.getText());
                thread_s.addList(msg);
                jlabel_aux.setText("");
            }
            else{
                jlabel_aux.setText("Erro no preço");
            }
        }

        private void jButton_apagarActionPerformed(java.awt.event.ActionEvent evt, int user_s_aux, javax.swing.JTextField JTextField_aux) {
            Mensagem msg = new Mensagem(9,user_id);
            msg.addList(Integer.toString(user_s_aux));
            thread_s.addList(msg);
        }

        private void cleanComponents_init_Menu_Login() {
            jPanel1.remove(jLabel_user);
            jPanel1.remove(jTextField_user);
            jPanel1.remove(jLabel_pass);
            jPanel1.remove(jPasswordField_pass);
            jPanel2.remove(jLabel_ip1);
            jPanel2.remove(jLabel_ip2);
            jPanel2.remove(jLabel_port);
            jPanel2.remove(jTextField_ip1);
            jPanel2.remove(jTextField_ip2);
            jPanel2.remove(jTextField_port);
            jPanel3.remove(jLabel_ligar);
            jPanel4.remove(jButton_login);
            remove(jPanel4);
            remove(jPanel3);
            remove(jPanel2);
            remove(jPanel1);
            remove(jButton_ip);
            remove(jButton_reg);
        }

        private void download_anexo(int idea_aux, String path) {
            if(path.endsWith("\\")){
                Mensagem msg = new Mensagem(15,user_id);
                msg.addList(Integer.toString(idea_aux));
                msg.addList(path);
                thread_s.addList(msg);
            }
        }

        private void Output(Mensagem msg){
            if(msg != null){
                ArrayList<javax.swing.JPanel> list_aux = new ArrayList<javax.swing.JPanel>();
                int tipo = msg.getTipo();

                //teste
                System.out.println(msg + " " + msg.getTipo());
                for(int i=0;i<listaEspera.size();i++)
                    System.out.println("\t"+listaEspera.get(i) + " " + listaEspera.get(i).getTipo());
                //teste

                if(tipo == -1 || tipo == 1){
                    if(tipo == 1){
                        cleanComponents_init_Menu_Login();
                        getContentPane().removeAll();
                        revalidate();
                        repaint();

                        new Menu_Principal(msg.popList(),msg.popList());

                        String str_aux = msg.popList();
                        if(!"".equals(str_aux)) {
                            jLabel_msg_fundo.setText("O utilizador " + str_aux.replace("||",", ").replace("|"," ") + " acabou de comprar shares suas.");
                        }
                    }
                    else{
                        jLabel_ligar.setText("Erro no User");
                        tipo = 1;
                    }
                    jTextField_user.setText("");
                    jPasswordField_pass.setText("");
                }
                else if(tipo == -2 || tipo == 2){
                    if(tipo == 2){
                        login = true;
                        jButton_ip.setEnabled(true);
                        jButton_login.setText("Ligar");
                        jButton_reg.setText("Registar");
                        jTextField_user.setText("");
                        jPasswordField_pass.setText("");
                        jLabel_ligar.setVisible(false);
                    }
                    else{
                        jLabel_ligar.setText("Erro no User");
                        tipo = 2;
                    }
                    jTextField_user.setText("");
                    jPasswordField_pass.setText("");
                }
                else if(tipo == -3 || tipo == 3){
                    if(tipo == -3){
                        jLabel_new_topic_error.setVisible(true);
                        jLabel_new_topic_error.setText("Erro no Tópico");
                        tipo = 3;
                    }
                    else
                        jLabel_new_topic_error.setVisible(false);
                    jTextField_topico.setText("");
                }
                else if(tipo == 4){
                    int i, length = msg.getListSize()/2;
                    for(i=0;i<length;i++) {
                        list_aux.add(add_topic_visual(tryParse(msg.popList()),msg.popList()));
                    }
                    atualiza_jpanel(jPanel_topic, list_aux);
                }
                else if(tipo == 5){
                    int i, length = msg.getListSize()/2;
                    for(i=0;i<length;i++) {
                        list_aux.add(add_idea_visual(tryParse(msg.popList()),msg.popList(),/*msg.popList()*/""));
                    }
                    atualiza_jpanel(jPanel_topic_idea, list_aux);
                }
                else if(tipo == -6 || tipo == 6){
                    if(tipo == -6){
                        jLabel_creat_aviso.setText("Erro no id do(s) tópico(s) ou da(s) ideia(s)");
                        tipo = 6;
                    }
                    jTextField_creat_topic.setText("<(id_topico1),(id_topico2),...>");
                    jTextField_creat_idea.setText("<(id_ideia1),(id_ideia2),...>");
                    jTextField_creat_anexo.setText("<(Path_Anexo)>");
                    jComboBox_creat.setSelectedIndex(0);
                    jTextArea_creat_msg.setText("");
                    jTextField_creat_share.setText("100");
                    jTextField_creat_preco.setText("2");
                }
                else if(tipo == -7 || tipo == 7){
                    if(tipo == -7){
                        jLabel_buy_error.setText("Erro na compra");
                        tipo = 7;
                    }
                    else{
                        jLabel_buy_error.setText("");
                    }
                    Mensagem msg_aux = new Mensagem(8,user_id);
                    msg_aux.addList(Integer.toString(msg.getIdUser()));
                    thread_s.addList(msg_aux);
                }
                else if(tipo == 8){
                    if(msg.getListSize() > 0){
                        jLabel_buy_error.setText("");
                        int i, length = msg.getListSize()/3;
                        for(i=0;i<length;i++) {
                            list_aux.add(add_buy_idea(msg.getIdUser(),tryParse(msg.popList()),tryParse(msg.popList()),tryParse(msg.popList())));
                        }
                        atualiza_jpanel(jPanel_buy, list_aux);
                    }
                    else{
                        jLabel_buy_error.setText("Ideia não encontrada");
                        jPanel_buy.removeAll();
                    }
                    jPanel_buy.repaint();
                    jPanel_buy.revalidate();
                    jTextField_buy_id.setText("");
                }
                else if(tipo == -9 || tipo == 9){
                    if(tipo == 9)
                        thread_s.addList(new Mensagem(11,user_id));
                    else
                        tipo = 9;
                }
                else if(tipo == 10){
                    label_user_money.setText(msg.popList());
                    String hist = "";
                    int length = msg.getListSize()/2;
                    for(int i=0;i<length;i++){
                        hist += msg.popList() + "\t" + msg.popList();
                    }
                    jTextArea_see_user.setText(hist);
                }
                else if(tipo == 11){
                    int i, length = msg.getListSize()/6;
                    for(i=0;i<length;i++) {
                        list_aux.add(add_our_idea(tryParse(msg.popList()),msg.popList(),/*msg.popList()*/"",msg.popList(),msg.popList(),msg.popList(),msg.popList()));
                    }
                    atualiza_jpanel(jPanel_idea, list_aux);
                }
                else if(tipo == 12){
                    thread_s.addList(new Mensagem(11,user_id));
                }
                else if(tipo == 13){
                    if(msg.getListSize() > 0){
                        jLabel_search_name.setText(msg.popList());
                        int i, length = msg.getListSize()/2;
                        for(i=0;i<length;i++) {
                            list_aux.add(add_idea_visual(tryParse(msg.popList()),msg.popList(),/*msg.popList()*/""));
                        }
                        atualiza_jpanel(jPanel_search, list_aux);
                    }
                    else{
                        jLabel_search_erro.setText("Erro no tópico");
                    }
                }
                else if(tipo == 14){
                    String str_aux = msg.popList();
                    if(!"".equals(str_aux))
                            jLabel_msg_fundo.setText("O utilizador " + str_aux.replace("||",", ").replace("|"," ") + " acabou de comprar shares suas.");
                }
                else if(tipo == 15){
                    if(msg.getFile() != null){
                        msg.getFile().saveToFile(msg.popList() + msg.getFile().getName());
                    }
                }
                else if(tipo == 16){
                    getContentPane().removeAll();
                    revalidate();
                    repaint();
                    user_id = 0;
                    user_nome = "";
                    new Menu_Login();
                }
                else if(tipo == 17){
                    if(msg.getListSize() > 0)
                        jLabel_msg_fundo.setText(msg.popList());
                }

                if(listaEspera.size() > 0){
                    while(listaEspera.size() > 0 && listaEspera.get(0).getTipo() != tipo){
                        thread_s.addList(listaEspera.get(0));
                        listaEspera.remove(0);
                    }
                    if(listaEspera.size() > 0){
                        listaEspera.remove(0);
                    }
                }
            }
        }
    }
}
