package model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import exception.ListSize0Exception;

public class MessageModel {

	/**
	 * Metodo para enviar un mensaje, el usuario con userhost debe estar conectado.
	 * @param message {@link Message} Objeto de negocio con el campo message.
	 */
	public static void send(Message message) {
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall("{ call send(?) }")) {
            stmt.setString(1, message.getMessage());
            stmt.execute();
        } catch (SQLException e) {
        	JOptionPane.showMessageDialog(null, e.getMessage(), "Error en MessageModel.send()", JOptionPane.ERROR_MESSAGE);
        	e.printStackTrace();
        }
    }
	
	/**
	 * Metodo para recuperar todos los mensajes a partir del ultimo leido.
	 * @return {@link ArrayList} Lista con los mensajes.
	 * @throws ListSize0Exception En caso de no devolver ningun mensaje.
	 */
	public static ArrayList<Message> getMessages() throws ListSize0Exception {
        ArrayList<Message> messages = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall("{ call getMessages() }");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String nick = rs.getString("nick");
                String messageText = rs.getString("message");
                Timestamp ts = rs.getTimestamp("ts");
                messages.add(new Message(0, nick, messageText, ts));
            }
        } catch (SQLException e) {
        	JOptionPane.showMessageDialog(null, e.getMessage(), "Error en MessageModel.getMessages()", JOptionPane.ERROR_MESSAGE);
        	e.printStackTrace();
        }
        
        if (messages.size() == 0) {
        	throw new ListSize0Exception("No hay mensajes nuevos");
        }
        
        return messages;
    }
	
}
