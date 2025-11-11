# cocina.py
# Clase principal que gestiona la cocina, crea los cocineros y coordina los pedidos

import threading
from pedido import Pedido
from cocinero import Cocinero

def main():
    # Inicializar el archivo log eliminando contenido anterior
    with open("log_pedidos.txt", "w", encoding="utf-8") as archivo:
        archivo.write("=== LOG DE PEDIDOS ===\n")
        archivo.write("Fecha y hora de inicio del procesamiento\n")
        archivo.write("============================================\n")
    
    # Crear la lista de pedidos con al menos 6 pedidos
    lista_pedidos = [
        Pedido(1, "Paella"),
        Pedido(2, "Pizza Margherita"),
        Pedido(3, "Sushi"),
        Pedido(4, "Hamburguesa"),
        Pedido(5, "Ensalada Cesar"),
        Pedido(6, "Pasta Carbonara"),
        Pedido(7, "Tacos"),
        Pedido(8, "Ramen")
    ]
    
    # Crear objetos Lock para sincronizacion de acceso a recursos compartidos
    lock_pedidos = threading.Lock()  # Lock para acceso a la lista de pedidos
    lock_archivo = threading.Lock()  # Lock para escritura en el archivo
    
    # Mostrar encabezado en consola
    mostrar_encabezado(len(lista_pedidos))
    
    # Crear los tres cocineros (hilos)
    cocinero1 = Cocinero("Cocinero-1", lista_pedidos, lock_pedidos, lock_archivo)
    cocinero2 = Cocinero("Cocinero-2", lista_pedidos, lock_pedidos, lock_archivo)
    cocinero3 = Cocinero("Cocinero-3", lista_pedidos, lock_pedidos, lock_archivo)
    
    # Iniciar el procesamiento de todos los cocineros
    cocinero1.start()
    cocinero2.start()
    cocinero3.start()
    
    # Esperar a que todos los hilos terminen su ejecucion
    cocinero1.join()
    cocinero2.join()
    cocinero3.join()
    
    # Mostrar mensaje de finalizacion
    mostrar_finalizacion()

# Funcion para mostrar el encabezado inicial con informacion de la cocina
def mostrar_encabezado(total_pedidos):
    print("\n" + "="*50)
    print("       SISTEMA DE GESTION DE COCINA")
    print("="*50)
    print(f"Total de pedidos a procesar: {total_pedidos}")
    print("Numero de cocineros disponibles: 3")
    print("="*50 + "\n")

# Funcion para mostrar el mensaje de finalizacion
def mostrar_finalizacion():
    print("\n" + "="*50)
    print("Todos los pedidos han sido procesados")
    print("="*50)
    print("Revise el archivo log_pedidos.txt para detalles")
    print("="*50 + "\n")

# Punto de entrada del programa
if __name__ == "__main__":
    main()