package model;

import java.util.ArrayList;

import exception.ListSize0Exception;

public class Test {

	public static void main(String[] args) {
		
		// campo de prueba de modelos la app final es app.MainApp.java
		// clase de prueba de modelos, no es util para la app final
		
		// crear un usuario
        User usuario = new User("antonio_nick", null, null, 0);
        
        // conectar al usuario a la base de datos
        //UserModel.connect(usuario);
        
        // desconectar al usuario
        //UserModel.disconnect();
        
        // ver usuarios conectados
        ArrayList<User> usuariosConectados = UserModel.getConnectedUsers();
        
        for (User user : usuariosConectados) {
            System.out.println(user);
        }
        
        // crear un mensaje
        Message mensaje = new Message(0, null, "hola a todos", null);
        
        // enviar un mensaje (el usuario con userhost debe estar conectado)
        MessageModel.send(mensaje);
        
        // ver mensajes enviados (solo me muestra a partir del last_read, asi que no veras todos todos)
        ArrayList<Message> mensajesEnviados = null;
		try {
			mensajesEnviados = MessageModel.getMessages();
		} catch (ListSize0Exception e) {
			e.printStackTrace();
		}
        
        for (Message message : mensajesEnviados) {
            System.out.println(message);
        }

	}

}
