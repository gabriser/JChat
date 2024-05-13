package app;

import javax.swing.JOptionPane;

import controller.HomeController;

public class MainApp {

	public static void main(String[] args) {
		
		/*
		 * M3 UF6 E02 Chat
		 */
		
		// El programa se ejecuta desde esta clase.
		
		/*
		 * --- --- --- --- ---
		 * Justificación de la coleccion ArrayList:
		 * - ArrayList permite elementos duplicados, pero en esta aplicación ya se encarga la base de datos de que no
		 * 	 existan duplicados (por ejemplo, no puede haber 2 usuarios con el mismo nick dentro de la bbdd).
		 * 
		 * - ArrayList mantiene el orden de insercción de los elementos, cosa que nos sirve para mentener el orden de
		 *   llegada de los nuevos mensajes en el chat.
		 *   
		 * - HashSet es mas rapida que ArrayList, pero esta colección tiene como inconveniente de que no mantiene el
		 *   orden de insercción de los elementos, al contrario que LinkedHashSet, pero esta tiene peor rendimiento.
		 *   
		 * - HashMap es similar a HashSet, pero esta coleccion guarda los elementos en orden segun el criterio que
		 *   queramos, pero en esta aplicación no queremos orden de por ejemplo alfabeticamente, sino de llegada de mensajes.
		 *   
		 * - LinkedList es mas segura que ArrayList al tener doble enlace, pero tiene peor rendimiento.
		 * 
		 * - He elegido ArrayList porque yo veo que necesito una lista no demaisado larga para guardar los mensajes y los
		 *   usuarios conectados, no necesito que sean unicos (osea, si, pero ya se encarga la base de datos), tampoco que
		 *   tengan clave/valor ni que esten ordenados alfabeticamente, sino que esten ordenados segun su llegada.
		 * 
		 * --- --- --- --- ---
		 */
		
		// Solo pongo un catch general porque hago varios try catch dentro de otras clases.
		// En otras clases controlo excepciones mas especificas junto a su propio JOptionPane.showMessageDialog.
		
		try {
			HomeController cHome = new HomeController();
			cHome.show();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Exception: Error Completo en la App", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

	}

}
