package view;

import java.awt.BorderLayout;
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
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controller.ChatController;
import exception.ListSize0Exception;
import model.User;
import model.UserModel;

public class HomeView extends JFrame {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Create the application.
	 */
	public HomeView(ArrayList<User> arrUsuariosConectados) {
		initialize(arrUsuariosConectados);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(ArrayList<User> arrUsuariosConectados) {
		
		this.setBounds(100, 100, 500, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("JChat!");
		this.setResizable(false);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(HomeView.class.getResource("/assets/JChat!_logo.png")));
		
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
        
        JMenuItem menuItemSalir = new JMenuItem("Salir");
        menuItemSalir.setFont(latoFont);
        menuItemSalir.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		System.exit(0);
        	}
        });
        menuItemSalir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
        
        menuPrograma.add(menuItemAcercaDe);
        menuPrograma.add(menuItemSalir);
		
		// diseño grafico
		JPanel pnlTop = new JPanel();
		pnlTop.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.getContentPane().add(pnlTop, BorderLayout.NORTH);
		pnlTop.setLayout(new GridLayout(3, 1, 10, 10));
		
		JLabel lblBienvenido = new JLabel("Bienvenido, escribe un usuario:");
		lblBienvenido.setFont(latoFont);
		lblBienvenido.setHorizontalAlignment(SwingConstants.CENTER);
		pnlTop.add(lblBienvenido);
		
		JTextField txtField = new JTextField();
		txtField.setFont(latoFont);
		pnlTop.add(txtField);
		txtField.setColumns(10);
		
		MyButton btnConectar = new MyButton("Iniciar sessión");
		pnlTop.add(btnConectar);
		
		JPanel pnlCenter = new JPanel();
		pnlCenter.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.getContentPane().add(pnlCenter, BorderLayout.SOUTH);
		pnlCenter.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlCenTop = new JPanel();
		pnlCenter.add(pnlCenTop, BorderLayout.NORTH);
		
		JLabel lblUsuariosConectados = new JLabel("Usuarios conectados: "+arrUsuariosConectados.size());
		lblUsuariosConectados.setFont(latoFont);
		pnlCenTop.add(lblUsuariosConectados);
		
		JPanel pnlCenCenter = new JPanel();
		pnlCenCenter.setLayout(new GridLayout(0, 2, 10, 10));
		pnlCenter.add(pnlCenCenter, BorderLayout.CENTER);
		
		
		// bucle para pintar los usuarios conectados
		for (User user : arrUsuariosConectados) {
		    MyUserContainer pnlUser = new MyUserContainer(user.getNick());
		    pnlCenCenter.add(pnlUser);
		}
		
		// logica de boton conectar
		btnConectar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				String usuario = txtField.getText().trim();
				
				// si el texto es vacio
				if (usuario.length() == 0) {
					JOptionPane.showMessageDialog(null, "Por favor, escribe un usuario", "Aviso", JOptionPane.INFORMATION_MESSAGE);
				} else {
					// si el nickname esta pillado
					ArrayList<User> usuariosConectados = UserModel.getConnectedUsers();
					
					boolean nickEnUso = false;
		            for (User user : usuariosConectados) {
		                if (user.getNick().equals(usuario)) {
		                    nickEnUso = true;
		                    break;
		                }
		            }
		            
		            if (nickEnUso) {
		            	JOptionPane.showMessageDialog(null, "El nick '"+usuario+"' ya esta en uso, elige otro", "Aviso", JOptionPane.INFORMATION_MESSAGE);
		            } else {
		            	
		            	// ahora si conectar el usuario
		            	boolean isConectado = UserModel.connect(new User(usuario, null, null, 0));
		            	
		            	// abrir ventana de chat
		            	if (isConectado) {
		            		JOptionPane.showMessageDialog(null, "Bienvenido al chat: "+usuario, "Aviso", JOptionPane.INFORMATION_MESSAGE);
		            		
		            		ChatController cChat= new ChatController();
			            	cChat.show();
		            	}
		            	
		            }
				}
				
			}
		});
		
	}
	
}
