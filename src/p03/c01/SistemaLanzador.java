package src.p03.c01;

/**
 * SistemaLanzador.java
 * Este archivo contiene la clase principal que inicia la simulación del parque.
 * 
 * @author Rordrigo Pascual Arnaiz
 */
public class SistemaLanzador {
	public static void main(String[] args) {
		
		IParque parque = new Parque(); // Creación de un objeto Parque para simular el parque

		char letra_puerta = 'A'; // Definición de la letra inicial para las puertas

		System.out.println("¡Parque abierto!");
		
		// Ciclo para crear hilos de entrada y salida para cada puerta del parque
		for (int i = 0; i < 5; i++) { 
			
            // Generación del identificador de la puerta
			String puerta = ""+((char) (letra_puerta++));
			
			// Creación de hilos de entrada
			ActividadEntradaPuerta entradas = new ActividadEntradaPuerta(puerta, parque);
			new Thread (entradas).start();
			
			// Creacion de hilos de salida 

			ActividadSalidaPuerta salida = new ActividadSalidaPuerta(puerta, parque);
			new Thread (salida).start();
			
			
		}
		
	}	
}