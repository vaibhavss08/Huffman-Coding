
import java.io.*;
import java.util.*;

class Encode {
	static void encode() throws IOException {
		try (Scanner scan = new Scanner(System.in)) {
			HashMap<String, String> encode = new HashMap<>();
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
			 }catch (IOException e) {
			      System.out.println("An error occurred.");
			      e.printStackTrace();
			 }
			
			HuffmanNode root = new HuffmanNode();
			int n = s.length();
			HashMap<String,Integer> map = new HashMap<>();
			for(int i=0;i<n;i++) {
				String add = String.valueOf(s.charAt(i));
				if(s.charAt(i)=='\r') {
					add = "\\r";
				}else if(s.charAt(i)=='\n'){
					add = "\\n";
				}
					
				if(map.containsKey(add)){
			    	map.put(add,map.get(add)+1);
			    }else {
			    	map.put(add,1);
			    }
			}
					
			Set<String> keys=map.keySet();
			PriorityQueue<HuffmanNode> q = new PriorityQueue<HuffmanNode>(n, (l1,l2)->l1.frequency-l2.frequency);
			for (String ch : keys) {
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
			    f.c = null;
			    f.left = x;
			    f.right = y;
			    root = f;
			    q.add(f); 
			}
			
			System.out.print("Enter file path to store compressed file : ");
			String compress_path = scan.nextLine();
			
			StringBuilder encoded = new StringBuilder();
			Code(root, "",encode);
			
			for (char ch : s.toString().toCharArray()) {
				if(ch=='\n') encoded.append(encode.get(String.valueOf("\\n")));
				else if(ch=='\r') encoded.append(encode.get(String.valueOf("\\r")));
				else encoded.append(encode.get(String.valueOf(ch)));
			} 
			
			FileOutputStream fos = new FileOutputStream(compress_path);
			fos.write(toByteArray(fromString(encoded.toString())));
			fos.close();
			System.out.println("Compressed File!");
			mapfile(encode,compress_path);
		}

	}

	public static void Code(HuffmanNode root, String s, HashMap<String,String> encode){
        if (root.left == null && root.right == null && root.c!=null) {
        	//System.out.println(root.c+" "+ (int) root.c+" "+s);
        	encode.put(String.valueOf(root.c),s);
            return;
        }
        Code(root.left, s + "0", encode);
        Code(root.right, s + "1", encode);
    }
	
	private static BitSet fromString(String binary) {
        BitSet bitset = new BitSet(binary.length());
        for (int i = 0; i < binary.length(); i++) {
            if (binary.charAt(i) == '1') {
                bitset.set(i,true);
            }
            else{
                bitset.set(i,false);
            }
        }
        return bitset;
    }

    public static byte[] toByteArray(BitSet bits) {
        byte[] bytes = new byte[(bits.length() + 7) / 8];
        for (int i=0; i<bits.length(); i++) {
        	boolean b = bits.get(i);
            if (b) {
                bytes[i / 8] |= 128 >> (i % 8);
            }
        }
        return bytes;
    }
    
    public static void mapfile(HashMap<String, String> map,String decompress) throws IOException {
    	try (BufferedWriter bf = new BufferedWriter(new FileWriter(decompress.split("\\.")[0]+"map.txt"))) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
			    bf.write(entry.getKey() + ":"+ entry.getValue());
			    bf.newLine();
			}
			bf.flush();
		}
    }
}
