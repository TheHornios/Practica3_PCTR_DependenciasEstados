package src.p03.c01;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Parque.java
 * La clase Parque implementa la interfaz IParque y simula la gestión de la entrada y salida de visitantes del parque.
 *
 * @author Rordrigo Pascual Arnaiz
 */
public class Parque implements IParque{


	private final int MAXIMO_AFORO=50;

    // Contador total de personas en el parque
	private int contadorPersonasTotales;
	private Hashtable<String, Integer> contadoresPersonasPuerta;
	
	private Long tiempoInicial;  // Tiempo inicial para cálculo del tiempo medio de estancia
	private Double tiempoMedio;  // Tiempo medio de estancia de los visitantes en el parque
	

	/**
	 * Constructor de la clase Parque.
	 * Inicializa los atributos y la tabla de contadores de personas por puerta.
	 */
	public Parque() {
		contadorPersonasTotales = 0;
		contadoresPersonasPuerta = new Hashtable<String, Integer>();
		tiempoInicial=System.currentTimeMillis();
		tiempoMedio = (double) 0;
	}


	/**
     * Registra la entrada de un visitante al parque a través de la puerta especificada.
     * @param puerta Identificador de la puerta por la que ingresa el visitante.
     */
	@Override
	public synchronized void entrarAlParque(String puerta){
		
		this.comprobarAntesDeEntrar();
		
		// Si no hay entradas por esa puerta, inicializamos
		if (contadoresPersonasPuerta.get(puerta) == null){
			contadoresPersonasPuerta.put(puerta, 0);
		}
		
		// Aumentamos el contador total y el individual
		contadorPersonasTotales++;		
		contadoresPersonasPuerta.put(puerta, contadoresPersonasPuerta.get(puerta)+1);
		
		tiempoMedio = (tiempoMedio + (System.currentTimeMillis() - tiempoInicial)) / 2.0;
		
		
		// Imprimimos el estado del parque
		checkInvariante();
		
		imprimirInfo(puerta, "Entrada", this.tiempoMedio.toString());

		notifyAll(); // han entrado pueden salir
		
		
	}
	
	
	/**
     * Registra la salida de un visitante del parque a través de la puerta especificada.
     * @param puerta Identificador de la puerta por la que sale el visitante.
     */
	@Override
	public synchronized  void salidaDelParque(String puerta) {
		
		this.comprobarAntesDeSalir();
		
		if (contadoresPersonasPuerta.get(puerta) == null) {
			contadorPersonasTotales--; // disminuir aforo
			contadoresPersonasPuerta.put(puerta, 1); // inicializamos puerta si no esta inicializado

		} else {
			contadorPersonasTotales--; // disminuir aforo
			contadoresPersonasPuerta.put(puerta, contadoresPersonasPuerta.get(puerta) + 1);
		}
		
		tiempoMedio = (tiempoMedio + (System.currentTimeMillis() - tiempoInicial)) / 2.0;

		checkInvariante();
		
		imprimirInfo(puerta, "Salida", this.tiempoMedio.toString());
		
		notifyAll(); // ya han salido del parque pueden entrar
	}

	
	/**
	 * Verifica si se cumplen las invariantes de la clase Parque.
	 * Las invariantes son condiciones que deben ser siempre verdaderas durante la ejecución del programa.
	 * Si alguna de las invariantes no se cumple, se lanza una excepción AssertionError con un mensaje descriptivo.
	 */
	protected void checkInvariante() {
		assert sumarContadoresPuerta() == contadorPersonasTotales : "INV: La suma de contadores de las puertas debe ser igual al valor del contador del parte";
		assert contadorPersonasTotales >= 0 : "INV: El contador de personas totales no puede ser negativo";
	    assert !contadoresPersonasPuerta.isEmpty() : "INV: La tabla de contadores de personas por puerta no puede estar vacía";
		
	}

	/**
	 * Verifica si se puede realizar la entrada al parque
	 */
	protected void comprobarAntesDeEntrar(){
		while (contadorPersonasTotales == MAXIMO_AFORO ) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Verifica si se puede realizar la salida del parque
	 */
	protected void comprobarAntesDeSalir(){
		while (contadorPersonasTotales == 0) {
			notifyAll();
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * Calcula la suma de los contadores de personas por puerta
	 * @return int
	 */
	private int sumarContadoresPuerta() {
		int sumaContadoresPuerta = 0;
			Enumeration<Integer> iterPuertas = contadoresPersonasPuerta.elements();
			while (iterPuertas.hasMoreElements()) {
				sumaContadoresPuerta += iterPuertas.nextElement();
			}
		return sumaContadoresPuerta;
	}
	
	
	/**
	 * Imprime información sobre la entrada o salida de visitantes del parque
	 */
	private void imprimirInfo (String puerta, String movimiento, String tiempoMedio){
		System.out.println(movimiento + " al paraque por puerta " + puerta);
		System.out.println("--> Personas en el parque " + contadorPersonasTotales + " tiempo medio de estancia: "  + tiempoMedio);
		
		// Iteramos por todas las puertas e imprimimos sus entradas
		for(String p: contadoresPersonasPuerta.keySet()){
			System.out.println("----> Por puerta " + p + " " + contadoresPersonasPuerta.get(p));
		}
		System.out.println(" ");
	}
	

}
