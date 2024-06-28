package org.example;

import org.example.Message;
import org.example.NetworkManager;
import org.example.NetworkTopology;
import org.example.Nodo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MeshNetwork implements NetworkTopology {
    private List<Nodo> nodos;
    private ExecutorService executor;
    private NetworkManager networkManager;

    public MeshNetwork(NetworkManager networkManager) {
        this.networkManager = networkManager;
    }

    @Override
    public void ConfigureNetwork(int numberOfNodos) {
        nodos = new ArrayList<>();
        for (int i = 0; i < numberOfNodos; i++) {
            Nodo nodo = new Nodo(i);
            nodos.add(nodo);
            networkManager.addNodo(nodo);
        }
        for (Nodo nodo : nodos) {
            nodo.setVecinos(new ArrayList<>(nodos));
        }
        executor = Executors.newFixedThreadPool(numberOfNodos);
    }

    @Override
    public void sendMessage(int DeNodoId, int ANodoId, Message message) {
        executor.submit(() -> {
            Nodo toNodo = nodos.get(ANodoId);
            if (toNodo != null) {
                toNodo.receiveMessage(message);
            }
        });
    }

    public void runNetwork() {
        for (Nodo nodo : nodos) {
            executor.submit(nodo::run);
        }
    }

    @Override
    public void shutdown() {
        executor.shutdown();
    }

    @Override
    public Message receiveMessage(int NodoId) {
        Nodo nodo = nodos.get(NodoId);
        if (nodo != null) {
            try {
                return nodo.retriveMessage();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
        return null;
    }
}
