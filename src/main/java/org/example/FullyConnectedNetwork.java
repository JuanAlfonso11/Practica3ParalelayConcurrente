package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FullyConnectedNetwork implements NetworkTopology {
    private List<Nodo> nodos;
    private ExecutorService executor;
    private NetworkManager networkManager;

    public FullyConnectedNetwork(NetworkManager networkManager) {
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
    public void sendMessage(int fromNodeId, int toNodeId, Message message) {
        executor.submit(() -> {
            Nodo toNodo = nodos.get(toNodeId);
            if (toNodo != null) {
                toNodo.receiveMessage(message);
            }
        });
    }

    @Override
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
    public Message receiveMessage(int nodeId) {
        Nodo nodo = nodos.get(nodeId);
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
