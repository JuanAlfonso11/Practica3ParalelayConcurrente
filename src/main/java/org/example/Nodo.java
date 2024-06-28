package org.example;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.example.Message;

public class Nodo {
    private int id;
    private List<Nodo> vecinos;
    private BlockingQueue<Message> messageQueue = new LinkedBlockingQueue<>();

    public Nodo(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setVecinos(List<Nodo> vecinos) {
        this.vecinos = vecinos;
    }

    public void sendMessage(Nodo toNodo, Message message) {
        toNodo.receiveMessage(message);
    }

    public void receiveMessage(Message message) {
        messageQueue.add(message);
    }

    public Message retriveMessage() throws InterruptedException {
        return messageQueue.take();
    }

    public void run() {
        // Aquí puedes definir lo que debe hacer el nodo mientras está corriendo
    }
}
