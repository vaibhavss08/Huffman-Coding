

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;


public class Encode {
	static StringBuilder encode(String compress_path,HuffmanNode root, StringBuilder s,HashMap<Character,String> encode ) throws IOException {
		
		StringBuilder encoded = new StringBuilder();
		Code(root, "",encode);
        
        for (char ch : s.toString().toCharArray()) {
        	encoded.append(encode.get(ch));
        } 

        FileOutputStream fos = new FileOutputStream(compress_path);

        fos.write(toByteArray(fromString(encoded.toString())));
        fos.close();
        System.out.println("Compressed File!");
        return encoded;
	}

	public static void Code(HuffmanNode root, String s, HashMap<Character,String> encode){
        if (root.left == null && root.right == null && root.c!='`') {
            encode.put(root.c,s);
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
}
