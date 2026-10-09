public class ListaEnlazada {
    private Nodo cabeza;

    public ListaEnlazada() {
        cabeza = null;
    }

    public void agregar(int valor) {
        Nodo nuevo = new Nodo(valor);
        if (cabeza == null) {
            cabeza = nuevo;
            return;
        }
        Nodo actual = cabeza;
        while (actual.siguiente != null) {
            actual = actual.siguiente;
        }
        actual.siguiente = nuevo;
    }

    public boolean estaVacia() {
        return cabeza == null;
    }

    public void eliminarRepetidos() {
        Nodo dummy = new Nodo(0);
        dummy.siguiente = cabeza;
        Nodo anterior = dummy;
        Nodo actual = cabeza;
        while (actual != null) {
            if (actual.siguiente != null && actual.siguiente.valor == actual.valor) {
                int repetido = actual.valor;
                while (actual != null && actual.valor == repetido) {
                    actual = actual.siguiente;
                }
                anterior.siguiente = actual;
            } else {
                anterior = actual;
                actual = actual.siguiente;
            }
        }
        cabeza = dummy.siguiente;
    }

    public void eliminarRepetidosSinDummy() {
        while (cabeza != null && cabeza.siguiente != null && cabeza.valor == cabeza.siguiente.valor) {
            int repetido = cabeza.valor;
            while (cabeza != null && cabeza.valor == repetido) {
                cabeza = cabeza.siguiente;
            }
        }
        if (cabeza == null) {
            return;
        }
        Nodo anterior = cabeza;
        Nodo actual = cabeza.siguiente;
        while (actual != null) {
            if (actual.siguiente != null && actual.siguiente.valor == actual.valor) {
                int repetido = actual.valor;
                while (actual != null && actual.valor == repetido) {
                    actual = actual.siguiente;
                }
                anterior.siguiente = actual;
            } else {
                anterior = actual;
                actual = actual.siguiente;
            }
        }
    }

    public static ListaEnlazada fusionar(ListaEnlazada a, ListaEnlazada b) {
        Nodo dummy = new Nodo(0);
        Nodo cola = dummy;
        Nodo x = a.cabeza;
        Nodo y = b.cabeza;
        while (x != null && y != null) {
            if (x.valor <= y.valor) {
                cola.siguiente = x;
                x = x.siguiente;
            } else {
                cola.siguiente = y;
                y = y.siguiente;
            }
            cola = cola.siguiente;
        }
        if (x != null) {
            cola.siguiente = x;
        } else {
            cola.siguiente = y;
        }
        a.cabeza = null;
        b.cabeza = null;
        ListaEnlazada resultado = new ListaEnlazada();
        resultado.cabeza = dummy.siguiente;
        return resultado;
    }

    @Override
    public String toString() {
        if (cabeza == null) {
            return "null";
        }
        String texto = "";
        Nodo actual = cabeza;
        while (actual != null) {
            texto = texto + actual.valor;
            if (actual.siguiente != null) {
                texto = texto + " -> ";
            }
            actual = actual.siguiente;
        }
        return texto;
    }
}
