package src.p03.c01;

import java.util.Enumeration;
import java.util.Hashtable;

public class Parque implements IParque{


	private final int MAXIMO_AFORO=50;
	
	// TODO 
	private int contadorPersonasTotales;
	private Hashtable<String, Integer> contadoresPersonasPuerta;
	
	private Long tinicial;
	private Double tmedio;
	private Long tactual;
	
	public Parque() {
		contadorPersonasTotales = 0;
		contadoresPersonasPuerta = new Hashtable<String, Integer>();
		tinicial=System.currentTimeMillis();
		tmedio = (double) 0;
	}


	@Override
	public synchronized  void entrarAlParque(String puerta){
		
		this.comprobarAntesDeEntrar();
		
		
		
		// Si no hay entradas por esa puerta, inicializamos
		if (contadoresPersonasPuerta.get(puerta) == null){
			contadoresPersonasPuerta.put(puerta, 0);
		}
		

		// Aumentamos el contador total y el individual
		contadorPersonasTotales++;		
		contadoresPersonasPuerta.put(puerta, contadoresPersonasPuerta.get(puerta)+1);
		
		tactual = System.currentTimeMillis();
		tmedio = (tmedio + (tactual - tinicial)) / 2.0;
		
		
		// Imprimimos el estado del parque
		checkInvariante();
		
		imprimirInfo(puerta, "Entrada", this.tmedio.toString());

		notifyAll(); // han entrado pueden salir
		
		
	}
	
	
	@Override
	public synchronized  void salidaDelParque(String puerta) {
		
		this.comprobarAntesDeSalir();
		
		
		
		if (contadoresPersonasPuerta.get(puerta) == null) {
			contadorPersonasTotales--; // disminuir aforo
			contadoresPersonasPuerta.put(puerta, 1); // inicializamos puerta

		} else {
			contadorPersonasTotales--; // disminuir aforo
			contadoresPersonasPuerta.put(puerta, contadoresPersonasPuerta.get(puerta) + 1);
		}
		
		tactual = System.currentTimeMillis();
		tmedio = (tmedio + (tactual - tinicial)) / 2.0;

		checkInvariante();
		
		imprimirInfo(puerta, "Salida", this.tmedio.toString());
		
		notifyAll(); // ya han salido del parque pueden entrar
		
		
	}

	
	
	protected void checkInvariante() {
		assert sumarContadoresPuerta() == contadorPersonasTotales : "INV: La suma de contadores de las puertas debe ser igual al valor del contador del parte";
		assert contadorPersonasTotales >= 0 : "INV: El contador de personas totales no puede ser negativo";
	    assert !contadoresPersonasPuerta.isEmpty() : "INV: La tabla de contadores de personas por puerta no puede estar vacía";
		
	}

	protected void comprobarAntesDeEntrar(){
		while (contadorPersonasTotales == MAXIMO_AFORO ) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

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


	private int sumarContadoresPuerta() {
		int sumaContadoresPuerta = 0;
			Enumeration<Integer> iterPuertas = contadoresPersonasPuerta.elements();
			while (iterPuertas.hasMoreElements()) {
				sumaContadoresPuerta += iterPuertas.nextElement();
			}
		return sumaContadoresPuerta;
	}
	
	
	private void imprimirInfo (String puerta, String movimiento, String tmedio){
		System.out.println(movimiento + " al paraque por puerta " + puerta);
		System.out.println("--> Personas en el parque " + contadorPersonasTotales + " tiempo medio de estancia: "  + tmedio);
		
		// Iteramos por todas las puertas e imprimimos sus entradas
		for(String p: contadoresPersonasPuerta.keySet()){
			System.out.println("----> Por puerta " + p + " " + contadoresPersonasPuerta.get(p));
		}
		System.out.println(" ");
	}
	

}
