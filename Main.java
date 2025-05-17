import java.io.InputStreamReader; // Permite leer entrada con codificación específica
import java.io.PrintStream;       // Usado para configurar la salida con UTF-8
import java.nio.charset.StandardCharsets; // Proporciona codificaciones estándar
import java.util.Scanner;        // Permite leer entrada desde consola

public class Main {
    public static void main(String[] args) {
        AVLTree arbol = new AVLTree(); // Se instancia el árbol AVL

        try {
            try (Scanner sc = new Scanner(new InputStreamReader(System.in, StandardCharsets.UTF_8))) {
                System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8)); // Configura salida UTF-8

                while (true) {
                    System.out.print("Ingrese numero, secuencia o comando (buscar <n> / eliminar <n>): ");
                    String entrada = sc.nextLine().trim(); // Se lee la entrada del usuario

                    // Condición de salida
                    if (entrada.equalsIgnoreCase("exit") || entrada.equals("-1")) {
                        System.out.println("Finalizando programa...");
                        break;
                    }

                    // Comando: buscar <n>
                    if (entrada.toLowerCase().startsWith("buscar")) {
                        try {
                            String[] partes = entrada.split("\\s+");
                            if (partes.length < 2) throw new IllegalArgumentException();
                            int valor = Integer.parseInt(partes[1]);
                            // Llama a la función de búsqueda con ruta
                            String mensaje = arbol.buscarConRuta(arbol.raiz, valor);
                            System.out.println(mensaje);
                        } catch (Exception e) {
                            System.out.println("Comando inválido. Use: buscar <número>");
                        }
                        continue;
                    }

                    // Comando: eliminar <n>
                    if (entrada.toLowerCase().startsWith("eliminar")) {
                        try {
                            String[] partes = entrada.split("\\s+");
                            if (partes.length < 2) throw new IllegalArgumentException();
                            int valor = Integer.parseInt(partes[1]);
                            arbol.raiz = arbol.eliminar(arbol.raiz, valor); // Elimina el valor
                            System.out.println("Arbol AVL despues de eliminar:");
                            arbol.printTree(arbol.raiz);
                        } catch (Exception e) {
                            System.out.println("Comando invalido. Use: eliminar <número>");
                        }
                        continue;
                    }

                    // Entrada de números (ej. 10,20,30) o como lista [10,20] / (10,20)
                    try {
                        String limpia = entrada.replaceAll("[\\[\\]()]", ""); // Elimina símbolos
                        String[] partes = limpia.split(",");
                        boolean esSecuencia = partes.length > 1;

                        for (String parte : partes) {
                            int numero = Integer.parseInt(parte.trim());
                            arbol.raiz = arbol.insertar(arbol.raiz, numero); // Inserta valores
                        }

                        System.out.println(esSecuencia ? "Arbol AVL generado desde la secuencia:" : "Arbol AVL actualizado:");
                        arbol.printTree(arbol.raiz);
                    } catch (Exception e) {
                        System.out.println("Entrada inválida. Use números enteros o comandos validos.");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error general en la ejecucion del programa: " + e.getMessage());
        }
    }
}
