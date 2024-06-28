package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SwitchedNetwork implements NetworkTopology {
    private List<Nodo> nodos;
    private ExecutorService executor;
    private NetworkManager networkManager;
    private Switch networkSwitch;

    public SwitchedNetwork(NetworkManager networkManager) {
        this.networkManager = networkManager;
        this.networkSwitch = new Switch();
    }

    @Override
    public void ConfigureNetwork(int numberOfNodos) {
        nodos = new ArrayList<>();
        for (int i = 0; i < numberOfNodos; i++) {
            Nodo nodo = new Nodo(i);
            nodos.add(nodo);
            networkManager.addNodo(nodo);
            networkSwitch.connectNodo(nodo);
        }
        executor = Executors.newFixedThreadPool(numberOfNodos);
    }

    @Override
    public void sendMessage(int fromNodeId, int toNodeId, Message message) {
        executor.submit(() -> {
            Nodo fromNodo = nodos.get(fromNodeId);
            Nodo toNodo = nodos.get(toNodeId);
            if (fromNodo != null && toNodo != null) {
                networkSwitch.sendMessage(fromNodo, toNodo, message);
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

class Switch {
    private List<Nodo> nodos;

    public Switch() {
        this.nodos = new ArrayList<>();
    }

    public void connectNodo(Nodo nodo) {
        nodos.add(nodo);
    }

    public void sendMessage(Nodo fromNodo, Nodo toNodo, Message message) {
        toNodo.receiveMessage(message);
    }
}
