package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import controller.ChatController;
import exception.ListSize0Exception;
import model.Message;
import model.MessageModel;
import model.User;
import model.UserModel;

public class ChatView extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private Timer timer; // temporizador
	
	/*
	 * PD: He intentado hacer un Timer para que se refresquen los mensajes y usuarios,
	 * aunque no lo he dejado puesto por que no conseguia que funcionara todo a la vez,
	 * tenia errores de elementos no inicializados pese a estar definidos y la ventana
	 * no aparecia. Por ahora los mensajes y usuarios si puedes refrescarlos, pero solo
	 * es automatico al enviar un nuevo mensaje y al pulsar el boton 'refrescar mensajes'
	 * aunque tambien refresca usuarios pero el nombre seria muy largo con '... y usuarios'.
	 */
	
	/**
	 * Create the application.
	 */
	public ChatView() {
		initialize();
		//iniciarTimer();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		this.setBounds(100, 100, 500, 700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Chat de JChat!");
		this.setResizable(false);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(HomeView.class.getResource("/assets/JChat!_logo.png")));
		
		// recuperar usuarios conectados para la primera vez
		ArrayList<User> arrUsuariosConectados = null;
		try {
			arrUsuariosConectados = ChatController.recuperarUsuariosConectados();
		} catch (ListSize0Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Aviso", JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();
		}
		
		// paleta de fuentes y tamaño
		Font latoFont = null;
		try {
			latoFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/assets/lato/Lato-Regular.ttf")).deriveFont(15f);
		} catch (FontFormatException | IOException e) {
			JOptionPane.showMessageDialog(null, "No se ha podido cargar la fuente del programa. Cargando fuente por defecto", "IOException o FontFormatException", JOptionPane.ERROR_MESSAGE);
		}
		
		// barra de menu con opciones
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		
        JMenu menuPrograma = new JMenu("Menu");
        menuPrograma.setFont(latoFont);
        menuBar.add(menuPrograma);
        
        JMenuItem menuItemAcercaDe = new JMenuItem("Acerca de");
        menuItemAcercaDe.setFont(latoFont);
        menuItemAcercaDe.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		AboutView acercaDeDialog = new AboutView(new JFrame());
                acercaDeDialog.setVisible(true);
        	}
        });
        menuItemAcercaDe.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
        
        JMenuItem menuItemSalir = new JMenuItem("Cerrar sessión");
        menuItemSalir.setFont(latoFont);
        menuItemSalir.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		// desconectar antes de cerrar la ventana
        		UserModel.disconnect();
        		dispose();
        	}
        });
        menuItemSalir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
        
        menuPrograma.add(menuItemAcercaDe);
        menuPrograma.add(menuItemSalir);
        
        // diseño grafico
        JPanel pnlTop = new JPanel();
		pnlTop.setBackground(new Color(222, 221, 218));
		pnlTop.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.getContentPane().add(pnlTop, BorderLayout.NORTH);
		pnlTop.setLayout(new GridLayout(1, 2, 10, 10));
		
		MyButton btnDesconectar = new MyButton("Cerrar sessión");
		pnlTop.add(btnDesconectar);
		
		MyButton btnRecargar = new MyButton("Recargar mensajes");
		pnlTop.add(btnRecargar);
		
		JPanel pnlRight = new JPanel();
		pnlRight.setPreferredSize(new Dimension(200, 10));
		pnlRight.setBackground(new Color(222, 221, 218));
		pnlRight.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.getContentPane().add(pnlRight, BorderLayout.EAST);
		pnlRight.setLayout(new BorderLayout(0, 0));
		
		JLabel lblUsuariosConectados = new JLabel("Usuarios conectados: "+arrUsuariosConectados.size());
		lblUsuariosConectados.setHorizontalAlignment(SwingConstants.CENTER);
		pnlRight.add(lblUsuariosConectados, BorderLayout.NORTH);
		
		JPanel pnlRightCenter = new JPanel();
		pnlRightCenter.setLayout(new GridLayout(11, 1, 10, 10));
		pnlRightCenter.setBorder(new EmptyBorder(10, 10, 10, 10));
		pnlRight.add(pnlRightCenter, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane(pnlRightCenter);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		pnlRight.add(scrollPane, BorderLayout.CENTER);
		
		JPanel pnlBottom = new JPanel();
		pnlBottom.setBackground(new Color(222, 221, 218));
		pnlBottom.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.getContentPane().add(pnlBottom, BorderLayout.SOUTH);
		pnlBottom.setLayout(new GridLayout(2, 1, 10, 10));
		
		JTextField txtEscribeMensaje = new JTextField();
		pnlBottom.add(txtEscribeMensaje);
		txtEscribeMensaje.setColumns(10);
		
		MyButton btnEnviarMensaje = new MyButton("Enviar mensaje");
		pnlBottom.add(btnEnviarMensaje);
		
		JPanel pnlCenter = new JPanel();
		pnlCenter.setBorder(new EmptyBorder(10, 10, 10, 10));
		pnlCenter.setLayout(new GridLayout(20, 1, 10, 10));
		this.getContentPane().add(pnlCenter, BorderLayout.CENTER);
		
		JScrollPane scrollPane2 = new JScrollPane(pnlCenter); // añadir el scroll
		scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.getContentPane().add(scrollPane2, BorderLayout.CENTER);
		
		// bucle para pintar los usuarios conectados
		for (User user : arrUsuariosConectados) {
			MyUserContainer pnlUser = new MyUserContainer(user.getNick());
		    pnlUser.setPreferredSize(new Dimension(140, 35));
		    
		    pnlRightCenter.add(pnlUser);
		   
		}
		
		// logica boton desconectar
		btnDesconectar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// desconectar antes de cerrar la ventana
        		UserModel.disconnect();
        		dispose();
			}
		
		});
		
		// logica boton recargar mensajes (y usuarios)
		btnRecargar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				recargarMensajes(pnlCenter);
				
				recargarUsuarios(pnlRightCenter, lblUsuariosConectados);
			
			}
		
		});
		
		// logica boton enviar mensaje
		btnEnviarMensaje.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				String mensaje = txtEscribeMensaje.getText().trim();
				
				// si el texto es vacio
				if (mensaje.length() == 0) {
					JOptionPane.showMessageDialog(null, "Por favor, escribe un mensaje", "Aviso", JOptionPane.INFORMATION_MESSAGE);
				} else {
					
					// enviar el mensaje
					MessageModel.send(new Message(0, null, mensaje, null));
					
					JOptionPane.showMessageDialog(null, "Mensaje enviado", "Aviso", JOptionPane.INFORMATION_MESSAGE);
					
					// vaciar campo de texto
					txtEscribeMensaje.setText("");
					
					// hacer automaticamente el "recargar mensajes y usuarios" despues de enviar
					recargarMensajes(pnlCenter);
					recargarUsuarios(pnlRightCenter, lblUsuariosConectados);
					
					
				}
				
			}
		});

	}
	
	/**
	 * Metodo privado interno para recargar mensajes junto a su panel.
	 * @param pnlCenter JPanel panel contenedor de los mensajes.
	 */
	private void recargarMensajes(JPanel pnlCenter) {
		// recargar mensajes
		ArrayList<Message> arrNuevosMensajes = null;
		try {
			arrNuevosMensajes = ChatController.recuperarMensajes();
		} catch (ListSize0Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Aviso", JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();
		}
		
		if (arrNuevosMensajes == null) arrNuevosMensajes = new ArrayList<Message>(); // para evitar 'Cannot invoke "java.util.ArrayList.size()" because "arrNuevosMensajes" is null'
		
		// bucle solo si hay mensajes nuevos
		if (arrNuevosMensajes.size() != 0) {
			
			JOptionPane.showMessageDialog(null, "Hay "+arrNuevosMensajes.size()+" mensajes nuevos", "Aviso", JOptionPane.INFORMATION_MESSAGE);
			
			for (Message message : arrNuevosMensajes) {
				MyMessageContainer pnlMessage = new MyMessageContainer(message.getMessage(), message.getNick(), message.getTs().toString());
				pnlMessage.setPreferredSize(new Dimension(250, 100));
				pnlCenter.add(pnlMessage);
			}
			
		}
		
		// repintar el panel de mensajes
		pnlCenter.revalidate();
	}
	
	/**
	 * Metodo privado interno para recargar usuarios junto a su panel y label de usuarios conectados.
	 * @param pnlRightCenter JPanel panel contenedor de los usuarios conectados.
	 */
	private void recargarUsuarios(JPanel pnlRightCenter, JLabel lblUsuariosConectados) {
		// recargar usuarios
		ArrayList<User> arrNuevosUsuarios = null;
		try {
			arrNuevosUsuarios = ChatController.recuperarUsuariosConectados();
		} catch (ListSize0Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Aviso", JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();
		}
		
		// vaciar el panel de los usuarios actuales para actualizar
		pnlRightCenter.removeAll();
		
		// bucle de usuarios nuevos
		for (User user : arrNuevosUsuarios) {
			MyUserContainer pnlUser = new MyUserContainer(user.getNick());
		    pnlUser.setPreferredSize(new Dimension(140, 35));
		    
		    pnlRightCenter.add(pnlUser);
		}
		
		// repintar el label de usuarios conectados
		lblUsuariosConectados.setText("Usuarios conectados: "+arrNuevosUsuarios.size());
		
		// repintar el panel de usuarios
		pnlRightCenter.revalidate();
	}
	
	/**
     * Método para iniciar el temporizador.
     */
    /*private void iniciarTimer() {
        int delay = 10000; // 10 segundos en milisegundos
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                //recargarMensajes();
                //recargarUsuarios();
            }
        };
        timer = new Timer(delay, taskPerformer);
        timer.setRepeats(true);
        timer.start();
    }*/
	
}
