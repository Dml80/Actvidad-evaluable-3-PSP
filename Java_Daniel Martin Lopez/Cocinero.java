// Cocinero.java
// Clase que extiende Thread y representa un hilo cocinero que procesa pedidos

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

class Cocinero extends Thread {
    private String nombre;
    private List<Pedido> listaPedidos;
    private Object lockPedidos;
    private Object lockArchivo;
    
    // Constructor que inicializa el cocinero con su nombre, lista de pedidos y locks
    public Cocinero(String nombre, List<Pedido> listaPedidos, Object lockPedidos, Object lockArchivo) {
        this.nombre = nombre;
        this.listaPedidos = listaPedidos;
        this.lockPedidos = lockPedidos;
        this.lockArchivo = lockArchivo;
    }
    
    // Metodo principal del hilo que ejecuta el procesamiento de pedidos
    @Override
    public void run() {
        while (true) {
            Pedido pedido = null;
            
            // Seccion critica: tomar un pedido de la lista de forma sincronizada
            synchronized (lockPedidos) {
                if (listaPedidos.isEmpty()) {
                    break; // No hay mas pedidos, termina el hilo
                }
                pedido = listaPedidos.remove(0); // Obtiene el primer pedido
            }
            
            // Simular la preparacion del pedido con un retardo aleatorio
            try {
                mostrarInicio(pedido);
                Thread.sleep((long) (Math.random() * 2000 + 1000)); // 1-3 segundos
                mostrarFinalizacion(pedido);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            // Seccion critica: escribir en el archivo log de forma sincronizada
            synchronized (lockArchivo) {
                try (FileWriter fw = new FileWriter("log_pedidos.txt", true);
                     PrintWriter pw = new PrintWriter(fw)) {
                    pw.println(nombre + " proceso " + pedido + " a las " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                } catch (IOException e) {
                    System.err.println("Error al escribir en el archivo: " + e.getMessage());
                }
            }
        }
    }
    
    // Metodo para mostrar visualmente el inicio de la preparacion
    private void mostrarInicio(Pedido pedido) {
        System.out.println("[INICIANDO] " + nombre + " comienza a preparar " + pedido);
    }
    
    // Metodo para mostrar visualmente la finalizacion de la preparacion
    private void mostrarFinalizacion(Pedido pedido) {
        System.out.println("[COMPLETADO] " + nombre + " termino de preparar " + pedido);
    }
}