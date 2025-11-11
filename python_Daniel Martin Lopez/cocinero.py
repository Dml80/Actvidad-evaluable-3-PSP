# cocinero.py
# Clase que hereda de threading.Thread y representa un hilo cocinero que procesa pedidos

import threading
import time
import random
from datetime import datetime
from pedido import Pedido

class Cocinero(threading.Thread):
    # Constructor que inicializa el cocinero con su nombre, lista de pedidos y locks
    def __init__(self, nombre, lista_pedidos, lock_pedidos, lock_archivo):
        super().__init__()
        self.nombre = nombre
        self.lista_pedidos = lista_pedidos
        self.lock_pedidos = lock_pedidos
        self.lock_archivo = lock_archivo
        self.daemon = False
    
    # Metodo principal del hilo que ejecuta el procesamiento de pedidos
    def run(self):
        while True:
            pedido = None
            
            # Seccion critica: tomar un pedido de la lista de forma sincronizada
            with self.lock_pedidos:
                if len(self.lista_pedidos) == 0:
                    break  # No hay mas pedidos, termina el hilo
                pedido = self.lista_pedidos.pop(0)  # Obtiene el primer pedido
            
            # Mostrar el inicio de la preparacion
            self.mostrar_inicio(pedido)
            
            # Simular la preparacion del pedido con un retardo aleatorio (1-3 segundos)
            tiempo_preparacion = 1 + (2 * random.random())
            time.sleep(tiempo_preparacion)
            
            # Mostrar la finalizacion de la preparacion
            self.mostrar_finalizacion(pedido)
            
            # Seccion critica: escribir en el archivo log de forma sincronizada
            with self.lock_archivo:
                try:
                    with open("log_pedidos.txt", "a", encoding="utf-8") as archivo:
                        hora = datetime.now().strftime('%H:%M:%S')
                        archivo.write(f"{self.nombre} proceso {pedido} a las {hora}\n")
                except IOError as e:
                    print(f"Error al escribir en el archivo: {e}")
    
    # Metodo para mostrar visualmente el inicio de la preparacion
    def mostrar_inicio(self, pedido):
        print(f"[INICIANDO] {self.nombre} comienza a preparar {pedido}")
    
    # Metodo para mostrar visualmente la finalizacion de la preparacion
    def mostrar_finalizacion(self, pedido):
        print(f"[COMPLETADO] {self.nombre} termino de preparar {pedido}")