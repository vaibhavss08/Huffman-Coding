

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class Decode {
	public static void decompress (File file, HashMap<Character, String> encode, int bitLength) throws Exception{
		Scanner scan = new Scanner(System.in);
        byte[] contents = Files.readAllBytes(file.toPath());
        char check;
        String checkString = "";
        String finalString = "";
        
        String dec = binaryToText(contents);
        
        for (int i = 0; i < bitLength;i++){
            check = dec.charAt(i);
            checkString = checkString + Character.toString(check);
            if (encode.containsValue(checkString)){
                finalString = finalString + getKeyByValue(checkString,encode);
                checkString="";
            }
        }
        
        System.out.print("Enter file path to store decompressed file : ");
		String decompress_path = scan.nextLine();
		
		FileOutputStream fos = new FileOutputStream(decompress_path);
		byte b[]=finalString.getBytes();
        fos.write(b);
        fos.close();
        
        //mapfile(encode,decompress);
    }
	
	public static String binaryToText(byte[] contents){

        StringBuilder decompressed = new StringBuilder();
        for(int i = 0; i <contents.length; i++){
            String s = String.format("%8s", Integer.toBinaryString(contents[i] & 0xFF)).replace(' ','0');
            decompressed.append(new StringBuilder(s).toString());
        }
        
        return decompressed.toString();
    }

    public static char getKeyByValue(String value,HashMap<Character, String> encode) {
        for (Map.Entry<Character, String> entry : encode.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return '`';
    }
    
    public static void mapfile(HashMap<Character, String> map,String decompress) throws IOException {
    	BufferedWriter bf = new BufferedWriter(new FileWriter(decompress+"map.txt"));
    	  
        // iterate map entries
        for (Map.Entry<Character, String> entry : map.entrySet()) {

            // put key and value separated by a colon
            bf.write(entry.getKey() + ":"+ entry.getValue());

            // new line
            bf.newLine();
        }

        bf.flush();
    }
}
