package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class MyUserContainer extends JPanel {

	public MyUserContainer(String text) {
		
		// paleta de colores
		Color bgColor = new Color(155, 155, 155);
		Color textColor = new Color(255,255, 255);
		
		// paleta de fuentes y tamaño
		Font latoFont = null;
		try {
			latoFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/assets/lato/Lato-Regular.ttf")).deriveFont(15f);
		} catch (FontFormatException | IOException e) {
			JOptionPane.showMessageDialog(null, "No se ha podido cargar la fuente del programa. Cargando fuente por defecto", "IOException o FontFormatException", JOptionPane.ERROR_MESSAGE);
		}
		
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setBackground(bgColor);
		this.setFont(latoFont);
		
		JLabel lblUser = new JLabel(text);
		lblUser.setForeground(textColor);
		this.add(lblUser);
		
	}
	
}
