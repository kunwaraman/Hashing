package hashmap;
import java.util.*;

public class HashMapImplementaion {
	static class Map<K,V> { // generics
	    private class Node{  // define linkedlist node
	    	K key;   // each lineked list le andar key aur value hogi
	    	V value;
	    	
	    	public Node (K key, V value) {
	    		this.key = key;
	    		this.value = value;
	    	}
	    }
	    
	    private int n ; // n -nodes
	    private int N ; // total no of buckets
	    private LinkedList<Node> buckets[]; // N = buckets.length  bucket array ka type hai linkedkist of node
	     
	    
		public Map() {
			this.N = 4;
			this.buckets = new LinkedList[4];
			for(int i=0;i<4;i++) {
				this.buckets[i] = new LinkedList<>();
			}
			
		}
		private int hashFunction(K key) {
			int bucktIdx = key.hashCode();
			return Math.abs(bucktIdx)% N;
			
		}
		private int searchInLL(K key,int bucktIdx) {
			LinkedList<Node> ll = buckets[bucktIdx];
			for(int i=0;i<ll.size();i++) {
				if(ll.get(i).key==key) {
					return i; //data index;
				}
			}
			return -1;	
			
		}
		private void rehash() {
			LinkedList<Node> oldBucket[] = buckets;
			buckets = new LinkedList[N*2];
			
			for(int i=0;i<N*2;i++) {  // to do linkedlist empty kyuki hmne bana to diya size aab hme wha pr ushko null se empty bhi to karna pdega
				buckets[i]=new LinkedList<>();
			}
			 
			
			for(int i=0;i<oldBucket.length;i++) {
				LinkedList<Node> ll = oldBucket[i]; // har bucket ke index par ek ll store hoga
				for(int j=0;j<ll.size();j++) { // ush ll par loop chala lo
					Node node = ll.get(j); // ll ka ek ek node nikalo 
					put(node.key,node.value); // aur ushko put function ke liye call kr do
					// aur put fuction ew bucket ke andar hi hmesa add krta hai hmare nodes ko
				}
			}
		}
		
		public void put (K key,V value) {
			int bucktIdx = hashFunction(key);  // this is blackbox hashfuction
			int dataIndex = searchInLL(key,bucktIdx); //(gives data index)   tells us kya meri key hai mre bucket index pe exist krti hai ya nahi krti
			// agar mera data index ki value ek valid value hai (0+)mtlb  phle se exist krti hai
			// agar mera data index ki value -1 hogi means key does not exits
			
			if(dataIndex==-1) { // key doesn't exist
				buckets[bucktIdx].add(new Node(key,value));
				n++;
			}else {
				Node node = buckets[bucktIdx].get(dataIndex);
				node.value = value;
			}
			double lambda = (double)n/N;
			if(lambda>2.0) {
				// rehashing \
				rehash();
			}
		}
		public boolean containsKey(K key) {
			int buckIdx=hashFunction(key);
			int dataIndex = searchInLL(key,buckIdx);
			
			if(dataIndex==-1) {
				return false;
			}else {
				return true; 
			}
			
		}
		public V remove(K key) {
			int buckIdx  = hashFunction(key);
			int dataIndex = searchInLL(key,buckIdx);
			
			if(dataIndex==-1) {
				return null;
			}else {
				Node node = buckets[buckIdx].remove(dataIndex);
				n--;
				return node.value;
						
			}
		}
		public V get(K key) {
			int buckIdx = hashFunction(key);
			int dataIndex = searchInLL(key,buckIdx);
			
			if(dataIndex==-1) {
				return null;
			}else {
				Node node = buckets[buckIdx].get(dataIndex);
				return node.value;
			}
			
		}
		public ArrayList<K> keySet(){
			ArrayList<K> keys = new ArrayList<>();
			for(int i=0;i<buckets.length;i++) { // bi
				LinkedList<Node> ll = buckets[i];
				for(int j =0;j<ll.size();j++) { // di
					Node node = ll.get(j);
					keys.add(node.key);
				}
			}
			return keys;
		}
		public boolean isEmpty() {
			return n==0;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map<String,Integer> map = new Map<>();
		map.put("india", 190);
		map.put("china", 200);
		map.put("us",50); 
		
		ArrayList<String> keys = map.keySet();
		for(int i=0;i<keys.size();i++ ) {
			System.out.println(keys.get(i)+" "+map.get(keys.get(i)));
		}
		map.remove("india");
		System.out.println(map.get("india"));

	}

}
