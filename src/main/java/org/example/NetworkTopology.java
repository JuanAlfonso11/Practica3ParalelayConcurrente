package org.example;

public interface NetworkTopology {
    void ConfigureNetwork(int NumeroDeNodos);
    void sendMessage(int DeNodo, int ANodo, Message message);
    Message receiveMessage(int NodoId);
    void runNetwork();  // Añadido para manejar la ejecución de la red
    void shutdown();
}
