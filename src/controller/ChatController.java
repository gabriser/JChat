package controller;

import java.util.ArrayList;

import javax.swing.JFrame;

import exception.ListSize0Exception;
import model.Message;
import model.MessageModel;
import model.User;
import model.UserModel;
import view.ChatView;

public class ChatController {

	public void show() {
		
		// abrir ventana ChatView
		ChatView chatView = new ChatView();
		
		// no cerrar la aplicación al cerrar ChatView, solo esta ventana
		chatView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		chatView.setVisible(true);
		
	}
	
	/**
	 * Metodo utilizado desde la vista ChatView para poder llamar al metodo de UserModel.getConnectedUsers().
	 * @return {@link ArrayList} Una lista de usuarios conectados.
	 * @throws ListSize0Exception En caso de no devolver ningun usuario.
	 */
	public static ArrayList<User> recuperarUsuariosConectados() throws ListSize0Exception {
		// recuperar usuarios conectados
		ArrayList<User> arrUsuariosConectados =  UserModel.getConnectedUsers();
		return arrUsuariosConectados;
	}
	
	/**
	 * Metodo utilizado desde la vista ChatView para poder llamar al metodo MessageModel.getMessages().
	 * @return {@link ArrayList} Una lista de mensajes a partir del ulimo leido.
	 * @throws ListSize0Exception En caso de no devolver ningun mensaje.
	 */
	public static ArrayList<Message> recuperarMensajes() throws ListSize0Exception {
		// recuperar mensajes
		ArrayList<Message> arrMensajes = MessageModel.getMessages();
		return arrMensajes;
	}
	
}
