package src.p03.c01;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La clase ActividadEntradaPuerta implementa la interfaz Runnable
 * para simular la actividad de entrada de visitantes a través de una puerta del parque.
 * 
 * @author Rordrigo Pascual Arnaiz
 */
public class ActividadEntradaPuerta implements Runnable{
	
    	// Número total de entradas que se realizarán en la puerta
		private static final int NUMENTRADAS = 20;
		private String puerta;  // Identificador de la puerta
		private IParque parque; // Instancia del parque sobre el que se realizarán las entradas

		/**
	     * Constructor de la clase ActividadEntradaPuerta.
	     * @param puerta Identificador de la puerta por la que ingresarán los visitantes.
	     * @param parque Instancia del parque sobre el que se realizarán las entradas.
	     */
		public ActividadEntradaPuerta(String puerta, IParque parque) {
			this.puerta = puerta;
			this.parque = parque;
		}

		/**
	     * Método run que se ejecutará cuando se inicie el hilo.
	     * Simula las entradas de visitantes al parque a través de la puerta especificada.
	     */
		@Override
		public void run() {
	        // Ciclo para simular las entradas a través de la puerta
			for (int i = 0; i < NUMENTRADAS; i ++) {
				try {
	                // Llamada al método para registrar la entrada al parque
					parque.entrarAlParque(puerta);
	                // Simulación de un tiempo de espera aleatorio antes de la siguiente entrada
					TimeUnit.MILLISECONDS.sleep(new Random().nextInt(5)*1000);
				} catch (InterruptedException e) {
					Logger.getGlobal().log(Level.INFO, "Entrada interrumpida");
					Logger.getGlobal().log(Level.INFO, e.toString());
					return;
				}
			}
		}

}
