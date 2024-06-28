package org.example;

public class Main {
    public static void main(String[] args) {
        NetworkManager networkManager;
        long startTime, endTime;

        // Bus network
        networkManager = new NetworkManager();
        NetworkTopology busNetwork = new BusNetwork(networkManager);
        busNetwork.ConfigureNetwork(5);

        startTime = System.nanoTime();
        networkManager.sendMessage(0, 1, new Message("Hola desde el primer nodo"));
        networkManager.sendMessage(1, 2, new Message("Hola desde el segundo nodo"));

        try {
            System.out.println("Bus network");
            System.out.println(networkManager.getNodo(1).retriveMessage().getContent());
            System.out.println(networkManager.getNodo(2).retriveMessage().getContent());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        endTime = System.nanoTime();
        System.out.println("Bus Network execution time: " + (endTime - startTime) + " ns");

        // Ring network
        networkManager = new NetworkManager();
        NetworkTopology ringNetwork = new RingNetwork(networkManager);
        ringNetwork.ConfigureNetwork(5);
        ringNetwork.runNetwork();

        startTime = System.nanoTime();
        networkManager.sendMessage(0, 1, new Message("Hola desde el primer nodo"));
        networkManager.sendMessage(1, 2, new Message("Hola desde el segundo nodo"));

        try {
            System.out.println("Ring network");
            System.out.println(networkManager.getNodo(1).retriveMessage().getContent());
            System.out.println(networkManager.getNodo(2).retriveMessage().getContent());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ringNetwork.shutdown();
        endTime = System.nanoTime();
        System.out.println("Ring Network execution time: " + (endTime - startTime) + " ns");

        // Mesh network
        networkManager = new NetworkManager();
        NetworkTopology meshNetwork = new MeshNetwork(networkManager);
        meshNetwork.ConfigureNetwork(5);
        meshNetwork.runNetwork();

        startTime = System.nanoTime();
        networkManager.sendMessage(0, 1, new Message("Hola desde el primer nodo"));
        networkManager.sendMessage(1, 2, new Message("Hola desde el segundo nodo"));

        try {
            System.out.println("Mesh Network:");
            System.out.println(networkManager.getNodo(1).retriveMessage().getContent());
            System.out.println(networkManager.getNodo(2).retriveMessage().getContent());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        meshNetwork.shutdown();
        endTime = System.nanoTime();
        System.out.println("Mesh Network execution time: " + (endTime - startTime) + " ns");

        // Star network
        networkManager = new NetworkManager();
        NetworkTopology starNetwork = new StarNetwork(networkManager);
        starNetwork.ConfigureNetwork(5);
        starNetwork.runNetwork();

        startTime = System.nanoTime();
        starNetwork.sendMessage(0, 1, new Message("Hola desde el primer nodo"));
        starNetwork.sendMessage(1, 2, new Message("Hola desde el segundo nodo"));

        try {
            System.out.println("Star Network:");
            System.out.println(networkManager.getNodo(1).retriveMessage().getContent());
            System.out.println(networkManager.getNodo(2).retriveMessage().getContent());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        starNetwork.shutdown();
        endTime = System.nanoTime();
        System.out.println("Star Network execution time: " + (endTime - startTime) + " ns");

        // Hypercube network
        networkManager = new NetworkManager();
        NetworkTopology hypercubeNetwork = new HypercubeNetwork(networkManager);
        hypercubeNetwork.ConfigureNetwork(8);  // 8 nodes, since 8 is 2^3, making it a valid hypercube
        hypercubeNetwork.runNetwork();

        startTime = System.nanoTime();
        hypercubeNetwork.sendMessage(0, 1, new Message("Hola desde el primer nodo"));
        hypercubeNetwork.sendMessage(1, 2, new Message("Hola desde el segundo nodo"));

        try {
            System.out.println("Hypercube Network:");
            System.out.println(networkManager.getNodo(1).retriveMessage().getContent());
            System.out.println(networkManager.getNodo(2).retriveMessage().getContent());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        hypercubeNetwork.shutdown();
        endTime = System.nanoTime();
        System.out.println("Hypercube Network execution time: " + (endTime - startTime) + " ns");

        // Tree network
        networkManager = new NetworkManager();
        NetworkTopology treeNetwork = new TreeNetwork(networkManager);
        treeNetwork.ConfigureNetwork(7); // A binary tree with 7 nodes
        treeNetwork.runNetwork();

        startTime = System.nanoTime();
        treeNetwork.sendMessage(0, 1, new Message("Hola desde el primer nodo"));
        treeNetwork.sendMessage(1, 2, new Message("Hola desde el segundo nodo"));

        try {
            System.out.println("Tree Network:");
            System.out.println(networkManager.getNodo(1).retriveMessage().getContent());
            System.out.println(networkManager.getNodo(2).retriveMessage().getContent());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        treeNetwork.shutdown();
        endTime = System.nanoTime();
        System.out.println("Tree Network execution time: " + (endTime - startTime) + " ns");

        // Fully connected network
        networkManager = new NetworkManager();
        NetworkTopology fullyConnectedNetwork = new FullyConnectedNetwork(networkManager);
        fullyConnectedNetwork.ConfigureNetwork(4);
        fullyConnectedNetwork.runNetwork();

        startTime = System.nanoTime();
        fullyConnectedNetwork.sendMessage(0, 1, new Message("Hola desde el primer nodo"));
        fullyConnectedNetwork.sendMessage(1, 2, new Message("Hola desde el segundo nodo"));

        try {
            System.out.println("Fully Connected Network:");
            System.out.println(networkManager.getNodo(1).retriveMessage().getContent());
            System.out.println(networkManager.getNodo(2).retriveMessage().getContent());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        fullyConnectedNetwork.shutdown();
        endTime = System.nanoTime();
        System.out.println("Fully Connected Network execution time: " + (endTime - startTime) + " ns");

        // Switched network
        networkManager = new NetworkManager();
        NetworkTopology switchedNetwork = new SwitchedNetwork(networkManager);
        switchedNetwork.ConfigureNetwork(4);
        switchedNetwork.runNetwork();

        startTime = System.nanoTime();
        switchedNetwork.sendMessage(0, 1, new Message("Hola desde el primer nodo"));
        switchedNetwork.sendMessage(1, 2, new Message("Hola desde el segundo nodo"));

        try {
            System.out.println("Switched Network:");
            System.out.println(networkManager.getNodo(1).retriveMessage().getContent());
            System.out.println(networkManager.getNodo(2).retriveMessage().getContent());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        switchedNetwork.shutdown();
        endTime = System.nanoTime();
        System.out.println("Switched Network execution time: " + (endTime - startTime) + " ns");
    }
}
