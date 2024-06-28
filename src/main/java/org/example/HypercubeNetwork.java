package org.example;

import org.example.Message;
import org.example.NetworkManager;
import org.example.NetworkTopology;
import org.example.Nodo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HypercubeNetwork implements NetworkTopology {
    private List<Nodo> nodos;
    private ExecutorService executor;
    private NetworkManager networkManager;

    public HypercubeNetwork(NetworkManager networkManager) {
        this.networkManager = networkManager;
    }

    @Override
    public void ConfigureNetwork(int numberOfNodos) {
        int dimensions = (int) (Math.log(numberOfNodos) / Math.log(2));
        if (Math.pow(2, dimensions) != numberOfNodos) {
            throw new IllegalArgumentException("Number of nodes must be a power of 2");
        }

        nodos = new ArrayList<>();
        for (int i = 0; i < numberOfNodos; i++) {
            Nodo nodo = new Nodo(i);
            nodos.add(nodo);
            networkManager.addNodo(nodo);
        }

        for (Nodo nodo : nodos) {
            List<Nodo> vecinos = new ArrayList<>();
            int id = nodo.getId();
            for (int d = 0; d < dimensions; d++) {
                int neighborId = id ^ (1 << d); // Flip the d-th bit to find the neighbor in dimension d
                vecinos.add(nodos.get(neighborId));
            }
            nodo.setVecinos(vecinos);
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
