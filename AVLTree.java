// Clase que representa un árbol AVL (auto-balanceado)
public class AVLTree {
    Node raiz; // Nodo raíz del árbol

    // Inserta un valor manteniendo el balance del árbol
    public Node insertar(Node nodo, int valor) {
        if (nodo == null)
            return new Node(valor); // Nuevo nodo si está vacío

        if (valor < nodo.valor)
            nodo.izquierda = insertar(nodo.izquierda, valor);
        else if (valor > nodo.valor)
            nodo.derecha = insertar(nodo.derecha, valor);
        else
            return nodo; // Ignora duplicados

        nodo.altura = 1 + Math.max(getAltura(nodo.izquierda), getAltura(nodo.derecha));
        int balance = getFactorBalance(nodo);

        // Rebalanceo si es necesario (4 casos)
        if (balance > 1 && valor < nodo.izquierda.valor)
            return rotarDerecha(nodo);
        if (balance < -1 && valor > nodo.derecha.valor)
            return rotarIzquierda(nodo);
        if (balance > 1 && valor > nodo.izquierda.valor) {
            nodo.izquierda = rotarIzquierda(nodo.izquierda);
            return rotarDerecha(nodo);
        }
        if (balance < -1 && valor < nodo.derecha.valor) {
            nodo.derecha = rotarDerecha(nodo.derecha);
            return rotarIzquierda(nodo);
        }

        return nodo;
    }

    // Retorna la altura de un nodo
    private int getAltura(Node nodo) {
        return nodo == null ? 0 : nodo.altura;
    }

    // Calcula el factor de balance
    private int getFactorBalance(Node nodo) {
        return nodo == null ? 0 : getAltura(nodo.izquierda) - getAltura(nodo.derecha);
    }

    // Rotación simple a la derecha
    private Node rotarDerecha(Node y) {
        Node x = y.izquierda;
        Node T2 = x.derecha;

        x.derecha = y;
        y.izquierda = T2;

        y.altura = 1 + Math.max(getAltura(y.izquierda), getAltura(y.derecha));
        x.altura = 1 + Math.max(getAltura(x.izquierda), getAltura(x.derecha));

        return x;
    }

    // Rotación simple a la izquierda
    private Node rotarIzquierda(Node x) {
        Node y = x.derecha;
        Node T2 = y.izquierda;

        y.izquierda = x;
        x.derecha = T2;

        x.altura = 1 + Math.max(getAltura(x.izquierda), getAltura(x.derecha));
        y.altura = 1 + Math.max(getAltura(y.izquierda), getAltura(y.derecha));

        return y;
    }

    // Muestra el árbol de forma jerárquica en consola
    public void printTree(Node raiz) {
        if (raiz == null) {
            System.out.println("(árbol vacío)");
            return;
        }
        printTreeVisual(raiz, "", true);
    }

    // Visualización recursiva con prefijos
    private void printTreeVisual(Node nodo, String prefijo, boolean esUltimo) {
        if (nodo != null) {
            System.out.print(prefijo);
            System.out.print(esUltimo ? "\\-- " : "|-- ");
            System.out.println(nodo.valor + " (h=" + nodo.altura + ", b=" + getFactorBalance(nodo) + ")");

            String nuevoPrefijo = prefijo + (esUltimo ? "    " : "|   ");

            boolean tieneHijoIzquierdo = nodo.izquierda != null;
            boolean tieneHijoDerecho = nodo.derecha != null;

            if (tieneHijoIzquierdo || tieneHijoDerecho) {
                if (tieneHijoIzquierdo && tieneHijoDerecho) {
                    printTreeVisual(nodo.izquierda, nuevoPrefijo, false);
                    printTreeVisual(nodo.derecha, nuevoPrefijo, true);
                } else if (tieneHijoIzquierdo) {
                    printTreeVisual(nodo.izquierda, nuevoPrefijo, true);
                } else {
                    printTreeVisual(nodo.derecha, nuevoPrefijo, true);
                }
            }
        }
    }

    // Busca un valor y muestra la ruta hasta él
    public String buscarConRuta(Node nodo, int valor) {
        return buscarConRutaRec(nodo, valor, "raiz");
    }

    // Búsqueda recursiva con trazado de ruta
    private String buscarConRutaRec(Node nodo, int valor, String ruta) {
        if (nodo == null)
            return "Valor no encontrado.";

        if (valor == nodo.valor)
            return "Valor encontrado en la posicion: " + ruta + " (altura = " + nodo.altura + ")";

        if (valor < nodo.valor)
            return buscarConRutaRec(nodo.izquierda, valor, ruta + " → izquierda");
        else
            return buscarConRutaRec(nodo.derecha, valor, ruta + " → derecha");
    }

    // Elimina un nodo manteniendo balance
    public Node eliminar(Node nodo, int valor) {
        if (nodo == null)
            return null;

        if (valor < nodo.valor)
            nodo.izquierda = eliminar(nodo.izquierda, valor);
        else if (valor > nodo.valor)
            nodo.derecha = eliminar(nodo.derecha, valor);
        else {
            // Nodo con 0 o 1 hijo
            if (nodo.izquierda == null || nodo.derecha == null) {
                nodo = (nodo.izquierda != null) ? nodo.izquierda : nodo.derecha;
            } else {
                // Nodo con 2 hijos: reemplazar por el menor del subárbol derecho
                Node sucesor = obtenerMin(nodo.derecha);
                nodo.valor = sucesor.valor;
                nodo.derecha = eliminar(nodo.derecha, sucesor.valor);
            }
        }

        if (nodo == null)
            return null;

        // Recalcular altura y balancear
        nodo.altura = 1 + Math.max(getAltura(nodo.izquierda), getAltura(nodo.derecha));
        int balance = getFactorBalance(nodo);

        // Casos de rebalanceo
        if (balance > 1 && getFactorBalance(nodo.izquierda) >= 0)
            return rotarDerecha(nodo);
        if (balance > 1 && getFactorBalance(nodo.izquierda) < 0) {
            nodo.izquierda = rotarIzquierda(nodo.izquierda);
            return rotarDerecha(nodo);
        }
        if (balance < -1 && getFactorBalance(nodo.derecha) <= 0)
            return rotarIzquierda(nodo);
        if (balance < -1 && getFactorBalance(nodo.derecha) > 0) {
            nodo.derecha = rotarDerecha(nodo.derecha);
            return rotarIzquierda(nodo);
        }

        return nodo;
    }

    // Retorna el nodo con el valor mínimo (más a la izquierda)
    private Node obtenerMin(Node nodo) {
        while (nodo.izquierda != null)
            nodo = nodo.izquierda;
        return nodo;
    }
}
