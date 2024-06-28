package org.example;
import java.util.HashMap;
import java.util.Map;

public class NetworkManager {
    private Map<Integer, Nodo> nodos = new HashMap<>();

    public void addNodo(Nodo nodo){
        nodos.put(nodo.getId(),nodo);
    }

    public Nodo getNodo(int id){
        return nodos.get(id);
    }

    public void sendMessage(int fromNodoId,int toNodoId,Message message){
        Nodo fromNodo = nodos.get(fromNodoId);
        Nodo toNodo = nodos.get(toNodoId);
      if(fromNodo != null && toNodo != null){
          fromNodo.sendMessage(toNodo,message);
      }
    }
}
