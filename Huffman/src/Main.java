
import java.io.*;
import java.util.*;

public class Main {
	
	static HashMap<Character, String> encode = new HashMap<>();
	int bitLength;
	public static void main (String [] args) throws Exception {
		Scanner scan = new Scanner(System.in);
		StringBuilder s = new StringBuilder();
		System.out.print("Enter file path : ");
		String path = scan.nextLine();
		try {
			
			BufferedReader br = new BufferedReader(new FileReader(path));
	        int character;
	        char convert;
	        while ((character = br.read()) != -1) {
	            convert = ((char) character);
	            s.append(convert);
	        }
	        
	        br.close();
		 } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		 }
		
		HuffmanNode root = new HuffmanNode();
		int n = s.length();
		HashMap<Character,Integer> map = new HashMap<>();
        for(int i=0;i<n;i++) {
	        	if(map.containsKey(s.charAt(i))) {
	        		map.put(s.charAt(i),map.get(s.charAt(i))+1);
	        	}else {
	        		map.put(s.charAt(i),1);
	        	}
        }
        
        Set<Character> keys=map.keySet();
        PriorityQueue<HuffmanNode> q = new PriorityQueue<HuffmanNode>(n, (l1,l2)->l1.frequency-l2.frequency);
 
        for (char ch : keys) {
            HuffmanNode hn = new HuffmanNode();
            hn.c = ch;
            hn.frequency = map.get(ch);
            hn.left = null;
            hn.right = null;
            q.add(hn);
        }
 
        while (q.size() > 1) {
            HuffmanNode x = q.remove();
            HuffmanNode y = q.remove();
            HuffmanNode f = new HuffmanNode();
            f.frequency = x.frequency + y.frequency;
            f.c = '`';
            f.left = x;
            f.right = y;
            root = f;
            q.add(f); 
        }
        
        System.out.print("Enter file path to store compressed file : ");
        String compress_path = scan.nextLine();
        
        StringBuilder enc = Encode.encode(compress_path,root, s, encode); // As the method is static can access it without creating object
        int bitLength=enc.length();
        
        System.out.print("Enter file path to decompressed file : ");
        String decompress_path = scan.nextLine();
        File file2 = new File(decompress_path);
        Decode.decompress(file2, encode,bitLength);
	}
}
