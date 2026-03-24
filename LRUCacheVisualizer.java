import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;

class Node {
    int key, value;
    Node prev, next;

    Node(int k, int v) {
        key = k;
        value = v;
    }
}

class LRUCache {
    private int capacity;
    private HashMap<Integer, Node> map;
    private Node head, tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();

        head = new Node(0,0);
        tail = new Node(0,0);

        head.next = tail;
        tail.prev = head;
    }

    private void remove(Node node){
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void insert(Node node){
        node.next = head.next;
        node.prev = head;

        head.next.prev = node;
        head.next = node;
    }

    public void put(int key,int value){
        if(map.containsKey(key)){
            remove(map.get(key));
        }

        Node node = new Node(key,value);
        insert(node);
        map.put(key,node);

        if(map.size()>capacity){
            Node lru = tail.prev;
            remove(lru);
            map.remove(lru.key);
        }
    }

    public int get(int key){
        if(!map.containsKey(key))
            return -1;

        Node node = map.get(key);
        remove(node);
        insert(node);

        return node.value;
    }

    public LinkedList<Node> getCache(){
        LinkedList<Node> list = new LinkedList<>();
        Node curr = head.next;

        while(curr!=tail){
            list.add(curr);
            curr = curr.next;
        }

        return list;
    }
}

class CachePanel extends JPanel {

    LinkedList<Node> nodes = new LinkedList<>();

    public void updateCache(LinkedList<Node> list){
        nodes = list;
        animate();
    }

    private void animate(){
        new Thread(() -> {
            try{
                for(int i=0;i<10;i++){
                    repaint();
                    Thread.sleep(30);
                }
            }catch(Exception e){}
        }).start();
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        int x = 30;
        int y = 60;

        for(Node node : nodes){

            g.setColor(new Color(100,180,255));
            g.fillRoundRect(x,y,80,50,15,15);

            g.setColor(Color.BLACK);
            g.drawString(node.key+" : "+node.value,x+15,y+30);

            x += 100;
        }

        g.setColor(Color.GRAY);
        g.drawString("MRU",30,30);

        g.drawString("LRU",x-100,30);
    }
}

public class LRUCacheVisualizer extends JFrame {

    private JTextField capacityField,keyField,valueField;
    private CachePanel cachePanel;
    private LRUCache cache;

    public LRUCacheVisualizer(){

        setTitle("LRU Cache Visualizer");
        setSize(700,300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel top = new JPanel();

        top.add(new JLabel("Capacity"));
        capacityField = new JTextField(5);
        top.add(capacityField);

        JButton init = new JButton("Initialize");
        top.add(init);

        top.add(new JLabel("Key"));
        keyField = new JTextField(5);
        top.add(keyField);

        top.add(new JLabel("Value"));
        valueField = new JTextField(5);
        top.add(valueField);

        JButton put = new JButton("PUT");
        JButton get = new JButton("GET");

        top.add(put);
        top.add(get);

        add(top,BorderLayout.NORTH);

        cachePanel = new CachePanel();
        cachePanel.setBackground(Color.WHITE);

        add(cachePanel,BorderLayout.CENTER);

        init.addActionListener(e->{
            int cap = Integer.parseInt(capacityField.getText());
            cache = new LRUCache(cap);
            cachePanel.updateCache(cache.getCache());
        });

        put.addActionListener(e->{
            int key = Integer.parseInt(keyField.getText());
            int val = Integer.parseInt(valueField.getText());

            cache.put(key,val);
            cachePanel.updateCache(cache.getCache());
        });

        get.addActionListener(e->{
            int key = Integer.parseInt(keyField.getText());
            cache.get(key);
            cachePanel.updateCache(cache.getCache());
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LRUCacheVisualizer());
    }
}