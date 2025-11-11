# pedido.py
# Clase que representa la estructura de un pedido con ID y nombre del plato

class Pedido:
    # Constructor que inicializa un pedido con su ID y nombre del plato
    def __init__(self, id_pedido, nombre_plato):
        self.id = id_pedido
        self.nombre_plato = nombre_plato
    
    # Retorna una representacion en texto del pedido
    def __str__(self):
        return f"Pedido #{self.id}: {self.nombre_plato}"
    
    # Retorna la representacion para debugging
    def __repr__(self):
        return self.__str__()