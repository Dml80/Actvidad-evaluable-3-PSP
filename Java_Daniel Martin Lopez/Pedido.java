// Pedido.java
// Clase que representa la estructura de un pedido con ID y nombre del plato

class Pedido {
    private int id;
    private String nombrePlato;
    
    // Constructor que inicializa un pedido con su ID y nombre del plato
    public Pedido(int id, String nombrePlato) {
        this.id = id;
        this.nombrePlato = nombrePlato;
    }
    
    // Obtiene el ID del pedido
    public int getId() {
        return id;
    }
    
    // Obtiene el nombre del plato del pedido
    public String getNombrePlato() {
        return nombrePlato;
    }
    
    // Retorna una representacion en texto del pedido
    @Override
    public String toString() {
        return "Pedido #" + id + ": " + nombrePlato;
    }
}