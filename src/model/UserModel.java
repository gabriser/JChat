package model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import exception.ListSize0Exception;

public class UserModel {
	
	/**
	 * Metodo para conectar un usuario.
	 * @param user {@link User} Objeto de negocio con el campo nick.
	 * @return boolean TRUE si ha conectado, FALSE en caso de error.
	 */
	public static boolean connect(User user) {
	    try (Connection conn = DatabaseConnection.getConnection();
	         CallableStatement stmt = conn.prepareCall("{ call connect(?) }")) {
	        stmt.setString(1, user.getNick());
	        stmt.execute();
	        return true;
	    } catch (SQLException e) {
	    	JOptionPane.showMessageDialog(null, e.getMessage(), "Error en UserModel.connect()", JOptionPane.ERROR_MESSAGE);
	    	e.printStackTrace();
	    	return false;
	    }
	}
	
	/**
	 * Metodo para desconectar un usuario. Desconecta a partir del userhost (ya que solo puede haber uno).
	 */
	public static void disconnect() {
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall("{ call disconnect() }")) {
            stmt.execute();
        } catch (SQLException e) {
        	JOptionPane.showMessageDialog(null, e.getMessage(), "Error en UserModel.disconnect()", JOptionPane.ERROR_MESSAGE);
        	e.printStackTrace();
        }
    }
	
	/**
	 * Metodo para recuperar una lista de usuarios conectados.
	 * @return {@link ArrayList} Una lista de usuarios.
	 */
	public static ArrayList<User> getConnectedUsers() {
	    ArrayList<User> connectedUsers = new ArrayList<>();
	    try (Connection conn = DatabaseConnection.getConnection();
	         CallableStatement stmt = conn.prepareCall("{ call getConnectedUsers() }");
	         ResultSet rs = stmt.executeQuery()) {
	        
	        while (rs.next()) {
	            String nick = rs.getString("nick");
	            Timestamp dateCon = rs.getTimestamp("date_con");
	            connectedUsers.add(new User(nick, null, dateCon, 0)); // solo nick y date_con (es lo que devuelve el procedure)
	        }
	    } catch (SQLException e) {
	    	JOptionPane.showMessageDialog(null, e.getMessage(), "Error en UserModel.getConnectedUsers()", JOptionPane.ERROR_MESSAGE);
	    	e.printStackTrace();
	    }
	    
	    return connectedUsers;
	}
	
}
