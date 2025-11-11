// Cocina.java
// Clase principal que gestiona la cocina, crea los cocineros y coordina los pedidos

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Cocina {
    public static void main(String[] args) {
        // Inicializar el archivo log eliminando contenido anterior
        try (FileWriter fw = new FileWriter("log_pedidos.txt", false)) {
            fw.write("=== LOG DE PEDIDOS ===\n");
            fw.write("Fecha y hora de inicio del procesamiento\n");
            fw.write("============================================\n");
        } catch (IOException e) {
            System.err.println("Error al crear el archivo log: " + e.getMessage());
        }
        
        // Crear la lista de pedidos con al menos 6 pedidos
        List<Pedido> listaPedidos = new ArrayList<>();
        listaPedidos.add(new Pedido(1, "Tortilla Española"));
        listaPedidos.add(new Pedido(2, "Entrecot"));
        listaPedidos.add(new Pedido(3, "Lenguado a la Plancha"));
        listaPedidos.add(new Pedido(4, "Paella Valenciana"));
        listaPedidos.add(new Pedido(5, "Salmon al Horno"));
        listaPedidos.add(new Pedido(6, "Salmorejo"));
        listaPedidos.add(new Pedido(7, "Patatas Bravas"));
        listaPedidos.add(new Pedido(8, "Croquetas Caseras"));
        
        // Crear objetos de sincronizacion para proteger recursos compartidos
        Object lockPedidos = new Object(); // Lock para acceso a la lista de pedidos
        Object lockArchivo = new Object(); // Lock para escritura en el archivo
        
        // Mostrar encabezado en consola
        mostrarEncabezado(listaPedidos.size());
        
        // Crear los tres cocineros (hilos)
        Cocinero cocinero1 = new Cocinero("Cocinero-1", listaPedidos, lockPedidos, lockArchivo);
        Cocinero cocinero2 = new Cocinero("Cocinero-2", listaPedidos, lockPedidos, lockArchivo);
        Cocinero cocinero3 = new Cocinero("Cocinero-3", listaPedidos, lockPedidos, lockArchivo);
        
        // Iniciar el procesamiento de todos los cocineros
        cocinero1.start();
        cocinero2.start();
        cocinero3.start();
        
        // Esperar a que todos los hilos terminen su ejecucion
        try {
            cocinero1.join();
            cocinero2.join();
            cocinero3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Mostrar mensaje de finalizacion
        mostrarFinalizacion();
    }
    
    // Metodo para mostrar el encabezado inicial con informacion de la cocina
    private static void mostrarEncabezado(int totalPedidos) {
        System.out.println("\n================================================");
        System.out.println("       SISTEMA DE GESTION DE COCINA");
        System.out.println("================================================");
        System.out.println("Total de pedidos a procesar: " + totalPedidos);
        System.out.println("Numero de cocineros disponibles: 3");
        System.out.println("================================================\n");
    }
    
    // Metodo para mostrar el mensaje de finalizacion
    private static void mostrarFinalizacion() {
        System.out.println("\n================================================");
        System.out.println("Todos los pedidos han sido procesados");
        System.out.println("================================================");
        System.out.println("Revise el archivo log_pedidos.txt para detalles");
        System.out.println("================================================\n");
    }
}