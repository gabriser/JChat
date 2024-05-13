package controller;

import java.util.ArrayList;

import model.User;
import model.UserModel;
import view.HomeView;

public class HomeController {

	public void show() {
		
		// recuperar usuarios conectados
		ArrayList<User> arrUsuariosConectados = UserModel.getConnectedUsers();
		
		// abrir ventana HomeView
		HomeView homeView = new HomeView(arrUsuariosConectados);
		homeView.setVisible(true);
		
	}
	
}
