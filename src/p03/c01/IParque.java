package src.p03.c01;

/**
 * IParque
 * La interfaz IParque define los métodos necesarios para gestionar la entrada y salida de visitantes del parque.
 * 
 * @author Rordrigo Pascual Arnaiz
 */
public interface IParque {
	
	/**
     * Registra la entrada de un visitante al parque a través de la puerta especificada.
     * @param puerta Identificador de la puerta por la que ingresa el visitante.
     */
	public abstract void entrarAlParque(String puerta);

	/**
     * Registra la salida de un visitante del parque a través de la puerta especificada.
     * @param puerta Identificador de la puerta por la que sale el visitante.
     */
	public abstract void salidaDelParque(String puerta);

}
