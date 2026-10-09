public class Ejemplo {

    static ListaEnlazada crear(int[] valores) {
        ListaEnlazada lista = new ListaEnlazada();
        for (int i = 0; i < valores.length; i++) {
            lista.agregar(valores[i]);
        }
        return lista;
    }

    public static void main(String[] args) {
        int[][] casos = {
            {1, 1, 2, 3, 3, 4},
            {1, 1, 1},
            {1, 2, 2},
            {1, 2, 3},
            {5, 5, 6, 6},
            {}
        };

        System.out.println("=== Reto base: eliminarRepetidos ===");
        for (int i = 0; i < casos.length; i++) {
            ListaEnlazada conDummy = crear(casos[i]);
            ListaEnlazada sinDummy = crear(casos[i]);
            String entrada = conDummy.toString();
            conDummy.eliminarRepetidos();
            sinDummy.eliminarRepetidosSinDummy();
            System.out.println("Entrada: " + entrada);
            System.out.println("  Con dummy: " + conDummy);
            System.out.println("  Sin dummy: " + sinDummy);
        }

        int[][][] pares = {
            {{1, 4, 7}, {2, 3, 8, 9}},
            {{}, {2, 3}},
            {{}, {}},
            {{1, 1}, {1}}
        };

        System.out.println();
        System.out.println("=== Reto extendido: fusionar ===");
        for (int i = 0; i < pares.length; i++) {
            ListaEnlazada a = crear(pares[i][0]);
            ListaEnlazada b = crear(pares[i][1]);
            System.out.println("a: " + a + "   b: " + b);
            ListaEnlazada resultado = ListaEnlazada.fusionar(a, b);
            System.out.println("  Resultado: " + resultado);
            System.out.println("  a despues: " + a + "   b despues: " + b);
        }
    }
}
