// Clase que representa un nodo del árbol AVL
public class Node {
    int valor; // Valor almacenado en el nodo
    Node izquierda, derecha; // Referencia al hijo izquierdo y derecho
    int altura; // Altura del nodo dentro del árbol

    public Node(int valor) {
        this.valor = valor;        // Asigna el valor al nodo
        this.altura = 1;          // Altura inicial del nodo es 1 (cuando no tiene hijos)
    }
}
