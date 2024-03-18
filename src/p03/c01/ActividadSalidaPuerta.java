package src.p03.c01;

import java.util.Random;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La clase ActividadSalidaPuerta implementa la interfaz Runnable
 * para simular la actividad de salida de visitantes a través de una puerta del parque.
 * 
 * @author Rordrigo Pascual Arnaiz
 */
public class ActividadSalidaPuerta implements Runnable{

    // Número total de salidas que se realizarán en la puerta
	private static final int NUMSALIDAS= 20;
    // Identificador de la puerta
	private String puerta;
    // Instancia del parque sobre el que se realizarán las entradas
	private IParque parque;

	 /**
     * Constructor de la clase ActividadSalidaPuerta.
     * @param puerta Identificador de la puerta por la que ingresarán los visitantes.
     * @param parque Instancia del parque sobre el que se realizarán las salidas.
     */
	public ActividadSalidaPuerta(String puerta, IParque parque) {
		this.puerta = puerta;
		this.parque = parque;
	}

	/**
     * Método run que se ejecutará cuando se inicie el hilo.
     * Simula las salidas de visitantes al parque a través de la puerta especificada.
     */
	@Override
	public void run() {
		for (int i = 0; i < NUMSALIDAS; i ++) {
			try {
                // Llamada al método para registrar la salida al parque
				this.parque.salidaDelParque(puerta);
                // Simulación de un tiempo de espera aleatorio antes de la siguiente salida
				TimeUnit.MILLISECONDS.sleep(new Random().nextInt(5)*1000);
			} catch (InterruptedException e) {
				Logger.getGlobal().log(Level.INFO, "Salida interrumpida");
				Logger.getGlobal().log(Level.INFO, e.toString());
				return;
			}
		}
	}
}
