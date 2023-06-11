package server.game;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;

public class DataBase {
    private final HashMap<String, HashMap<String, HashSet<String>>> data;

    // Função auxiliar para criar os grupos
    private HashSet<String> createHashSet(String... elementos) {
        HashSet<String> hashSet = new HashSet<>();
        Collections.addAll(hashSet, elementos);

        return hashSet;
    }

    public DataBase() {
        data = new HashMap<>();

        // Comida
        HashMap<String, HashSet<String>> comida = new HashMap<>();
        comida.put("pizza", createHashSet("Piza", "piza", "pizz"));
        comida.put("hambúrguer", createHashSet("Hamburguer", "hamburgerr"));
        comida.put("sorvete", createHashSet("sorvett", "Sorvete"));
        data.put("comida", comida);

        // Objeto
        HashMap<String, HashSet<String>> objeto = new HashMap<>();
        objeto.put("cadeira", createHashSet("Cadeeira", "kadira", "cadeirra"));
        objeto.put("mesa", createHashSet("Meza", "mesaa", "Messa"));
        objeto.put("livro", createHashSet("Livrro", "livvro", "liivro"));
        data.put("objeto", objeto);

        // Esporte
        HashMap<String, HashSet<String>> esporte = new HashMap<>();
        esporte.put("futebol", createHashSet("Futbol", "futbolll", "futebool"));
        esporte.put("basquete", createHashSet("Basket", "basquetee", "baskett"));
        esporte.put("natação", createHashSet("Nataçao", "nataaçao", "nattacao"));
        data.put("esportes", esporte);
    }
    public HashMap<String, HashMap<String, HashSet<String>>> getData() {
        return this.data;
    }

}
