import java.util.LinkedList;
import java.util.Random;

public class Fila {

    private final LinkedList<Persona> personas = new LinkedList<>();
    private final int capacidadMaxima;

    public Fila(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public boolean estaLlena() {
        return personas.size() >= capacidadMaxima;
    }

    public boolean estaVacia() {
        return personas.isEmpty();
    }

    public int tamano() {
        return personas.size();
    }

    public void agregarAlFinal(Persona persona) {
        personas.addLast(persona);
    }

    public void agregarAlFrente(Persona persona) {
        personas.addFirst(persona);
    }

    public void agregarDespuesDe(Persona persona, int indice) {
        personas.add(indice + 1, persona);
    }

    public int indiceUltimoPreferente() {
        int indice = -1;
        for (int i = 0; i < personas.size(); i++) {
            if (personas.get(i).isPreferente()) {
                indice = i;
            }
        }
        return indice;
    }

    public int indiceAleatorio(Random random) {
        return random.nextInt(personas.size());
    }

    public Persona atenderFrente() {
        return personas.pollFirst();
    }

    public boolean removerPersona(Persona persona) {
        return personas.remove(persona);
    }

    public LinkedList<Persona> getPersonas() {
        return personas;
    }
}