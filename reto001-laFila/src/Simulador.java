import java.util.LinkedList;
import java.util.Random;

public class Simulador {

    private static final double PROBABILIDAD_LLEGADA = 0.6;
    private static final double PROBABILIDAD_APERTURA_CAJA = 0.4;
    private static final double PROBABILIDAD_PREFERENTE = 0.15;
    private static final double PROBABILIDAD_COLARSE = 0.15;
    private static final double PROBABILIDAD_ABANDONO = 0.3;
    private static final double PROBABILIDAD_DESISTIR_FILA_LLENA = 0.5;
    private static final int TIEMPO_MAXIMO_ESPERA = 8;
    private static final int INTERVALO_CHEQUEO_ABANDONO = 5;
    private static final int INTERVALO_ANUNCIO = 15;
    private static final int UMBRAL_ANUNCIO = 25;
    private static final int MINUTO_ACTIVACION_REGLAS_EXTENDIDAS = 20;

    private final Random random = new Random();
    private final Fila fila;
    private int contadorPersonas = 0;
    private int personasAtendidas = 0;

    public Simulador(int capacidadFila) {
        this.fila = new Fila(capacidadFila);
    }

    public void correrBase(int duracionMinutos) {
        for (int minuto = 1; minuto <= duracionMinutos; minuto++) {
            procesarLlegadaSimple(minuto);
            procesarAperturaCaja();
        }
        imprimirResultadoFinal(duracionMinutos);
    }

    public void correrExtendido(int duracionMinutos) {
        for (int minuto = 1; minuto <= duracionMinutos; minuto++) {
            if (minuto < MINUTO_ACTIVACION_REGLAS_EXTENDIDAS) {
                procesarLlegadaSimple(minuto);
            } else {
                procesarLlegadaExtendida(minuto);
                procesarAbandonos(minuto);
            }
            procesarAperturaCaja();
            verificarAnuncio(minuto);
            imprimirTamanoFila(minuto);
        }
        imprimirResultadoFinal(duracionMinutos);
    }

    private void procesarLlegadaSimple(int minuto) {
        if (random.nextDouble() < PROBABILIDAD_LLEGADA) {
            intentarIngreso(new Persona(++contadorPersonas, minuto, false));
        }
    }

    private void procesarLlegadaExtendida(int minuto) {
        if (random.nextDouble() >= PROBABILIDAD_LLEGADA) {
            return;
        }
        double tipo = random.nextDouble();
        if (tipo < PROBABILIDAD_PREFERENTE) {
            ingresarPreferente(minuto);
        } else if (tipo < PROBABILIDAD_PREFERENTE + PROBABILIDAD_COLARSE) {
            ingresarColado(minuto);
        } else {
            intentarIngreso(new Persona(++contadorPersonas, minuto, false));
        }
    }

    private void ingresarPreferente(int minuto) {
        if (fila.estaLlena() && random.nextDouble() < PROBABILIDAD_DESISTIR_FILA_LLENA) {
            return;
        }
        Persona persona = new Persona(++contadorPersonas, minuto, true);
        int indiceUltimoPreferente = fila.indiceUltimoPreferente();
        if (indiceUltimoPreferente == -1) {
            fila.agregarAlFrente(persona);
        } else {
            fila.agregarDespuesDe(persona, indiceUltimoPreferente);
        }
    }

    private void ingresarColado(int minuto) {
        if (fila.estaVacia()) {
            intentarIngreso(new Persona(++contadorPersonas, minuto, false));
            return;
        }
        if (fila.estaLlena() && random.nextDouble() < PROBABILIDAD_DESISTIR_FILA_LLENA) {
            return;
        }
        Persona persona = new Persona(++contadorPersonas, minuto, false);
        int indiceConocido = fila.indiceAleatorio(random);
        fila.agregarDespuesDe(persona, indiceConocido);
    }

    private void intentarIngreso(Persona persona) {
        if (fila.estaLlena() && random.nextDouble() < PROBABILIDAD_DESISTIR_FILA_LLENA) {
            return;
        }
        fila.agregarAlFinal(persona);
    }

    private void procesarAperturaCaja() {
        if (!fila.estaVacia() && random.nextDouble() < PROBABILIDAD_APERTURA_CAJA) {
            fila.atenderFrente();
            personasAtendidas++;
        }
    }

    private void procesarAbandonos(int minuto) {
        if (minuto % INTERVALO_CHEQUEO_ABANDONO != 0) {
            return;
        }
        LinkedList<Persona> aRemover = new LinkedList<>();
        for (Persona persona : fila.getPersonas()) {
            if (persona.tiempoEspera(minuto) > TIEMPO_MAXIMO_ESPERA
                    && random.nextDouble() < PROBABILIDAD_ABANDONO) {
                aRemover.add(persona);
            }
        }
        for (Persona persona : aRemover) {
            fila.removerPersona(persona);
        }
    }

    private void verificarAnuncio(int minuto) {
        if (minuto % INTERVALO_ANUNCIO == 0 && fila.tamano() > UMBRAL_ANUNCIO) {
            System.out.println("Minuto " + minuto + ": \"pasen por esta caja en orden de fila\"");
        }
    }

    private void imprimirTamanoFila(int minuto) {
        System.out.println("Minuto " + minuto + " - longitud de la fila: " + fila.tamano() + " metro(s)");
    }

    private void imprimirResultadoFinal(int duracionMinutos) {
        System.out.println("Simulacion finalizada tras " + duracionMinutos + " minutos");
        System.out.println("Personas atendidas: " + personasAtendidas);
        System.out.println("Personas que quedaron en fila: " + fila.tamano());
    }
}