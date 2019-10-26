package hash;

import javafx.util.Pair;

import java.util.*;

public class SimpleMapImpl<K, V> implements SimpleMap<K, V> {

    private Object[] table = null;
    private int tableLength = 16;
    private int size = 0;
    private final double extendRatio = 0.75;

    public V put(K key, V value) {
        if (table == null)
            table = new Object[tableLength];

        Node node = findNodeByKey(key);
        if (node != null){
            node.value = value;
            return node.value;
        } else{
            //вставляем едемент в начало
            Node newNode  = new Node(key, value);
            newNode.next = (Node)table[getNodeIndex(key)];
            table[getNodeIndex(key)] = newNode;
            size++;
            extend();
            return value;
        }
    }

    public V get(K key) {
        Node node = findNodeByKey(key);
        return node == null ? null : node.value;
    }

    public V remove(K key) {
        Node node = findPrevNodeByKey(key);

        if (table[getNodeIndex(key)] == null) {
            return null;
        } else if ((key == null && ((Node) table[getNodeIndex(key)]).key == null) || ((Node) table[getNodeIndex(key)]).key.equals(key)) {
            V result = ((Node) table[getNodeIndex(key)]).value;
            table[getNodeIndex(key)] = ((Node) table[getNodeIndex(key)]).next;
            size--;
            return result;
        } else if (node.next == null) {
            return null;
        } else {
            V result = node.next.value;
            node.next = node.next.next;
            size--;
            return result;
        }
    }

    public boolean contains(K key) {
        return findNodeByKey(key) != null;
    }

    public int size() {
        return this.size;
    }

    public Set<K> keySet() {
        Set<K> result = new HashSet<>();
        for (int i = 0; i < tableLength; i++) {
            Node node = (Node) table[i];
            while (node != null) {
                result.add(node.key);
                node = node.next;
            }
        }
        return result;
    }

    public Collection<V> values() {
        Collection<V> result = new ArrayList<V>();
        for (int i = 0; i < tableLength; i++) {
            Node node = (Node) table[i];
            while (node != null) {
                result.add(node.value);
                node = node.next;
            }
        }
        return result;
    }

    private int getNodeIndex(K key) {
        return key == null ? 0 : Math.abs(key.hashCode()) % tableLength;
    }

    private Node findNodeByKey(K key) {
        int index = getNodeIndex(key);
        Node node = (Node) table[index];
        if (node == null)
            return null;
        else if (nodeKeyEquals(node, key)) {
            return node;
        } else {
            node = findPrevNodeByKey(key);
            return node.next == null ? null : node.next;
        }
    }

    //the last node in the chain if not found
    //null if the first element in the chain
    private Node findPrevNodeByKey(K key) {
        int index = getNodeIndex(key);
        Node node = (Node) table[index];
        if (node == null || nodeKeyEquals(node, key))
            return null;
        else {
            while (node.next != null) {
                if (nodeKeyEquals(node.next, key))
                    return node;
                node = node.next;
            }
            return node;
        }
    }

    private boolean nodeKeyEquals(Node node, K key) {
        return node.key == null ? key == null : node.key.equals(key);
    }

    private void extend() {
        if (size > extendRatio * tableLength) {
            List<Pair<K, V>> values = new ArrayList<Pair<K, V>>();
            // storing all values
            for (int i = 0; i < tableLength; i++) {
                Node node = (Node) table[i];
                while (node != null) {
                    values.add(new Pair<K, V>(node.key, node.value));
                    node = node.next;
                }
            }
            //saving all values
            tableLength *= 2;
            table = new Object[tableLength];
            size = 0;
            for (Pair<K, V> pair : values) {
                this.put(pair.getKey(), pair.getValue());
            }
        }
    }

    private class Node {
        Node(K key, V value) {
            this.key = key;
            this.value = value;
            next = null;
        }

        private final K key;
        private V value;
        private Node next;
    }
}
