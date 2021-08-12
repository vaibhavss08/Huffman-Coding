import java.io.*;
import java.nio.file.Files;
import java.util.*;

class Decode {
	public static void decompress () throws Exception{
		
		try (Scanner scan = new Scanner(System.in)) {
			System.out.print("Enter file path to decompressed file : ");
			String path = scan.nextLine();
			File file = new File(path);
			System.out.print("Enter file path to map file : ");
			String mapfile = scan.nextLine();
			HashMap<String, String> encode = HashMapFromTextFile(mapfile);
			
			byte[] contents = Files.readAllBytes(file.toPath());
			char check;
			String checkString = "";
			String finalString = "";
			
			String dec = binaryToText(contents);

			for (int i = 0; i < dec.length();i++){
			    check = dec.charAt(i);
			    checkString = checkString + Character.toString(check);
			    if (encode.containsValue(checkString)){
			    	String curr = String.valueOf(getKeyByValue(checkString,encode));
			    	if(curr.equals("\\n")) {
			    		finalString = finalString+"\n";
			    	}else if(curr.equals("\\r")) {
			    		finalString = finalString+"\r";
			    	}else {
			    		finalString = finalString + curr;
			    	}
			        checkString="";
			    }
			}
			
			
			System.out.print("Enter file path to store decompressed file : ");
			String decompress_path = scan.nextLine();
			
			FileOutputStream fos = new FileOutputStream(decompress_path);
			byte b[]=finalString.getBytes();
			fos.write(b);
			fos.close();
			System.out.println("Decompressed File!");
		}
    }
	
	public static String binaryToText(byte[] contents){

        StringBuilder decompressed = new StringBuilder();
        for(int i = 0; i <contents.length; i++){
            String s = String.format("%8s", Integer.toBinaryString(contents[i] & 0xFF)).replace(' ','0');
            decompressed.append(new StringBuilder(s).toString());
        }
        
        return decompressed.toString();
    }

    public static String getKeyByValue(String value,HashMap<String, String> encode) {
        for (Map.Entry<String, String> entry : encode.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
    
    public static HashMap<String, String> HashMapFromTextFile(String filePath){
    	Map<String, String> map = new HashMap<>();
        BufferedReader br = null;
        try {
            File file = new File(filePath);
            br = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = br.readLine()) != null) {
            		String[] parts = line.split(":");
            		map.put(parts[0],parts[1]);
            }
        }
        
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (br != null) {
                try {
                    br.close();
                }
                catch (Exception e) {
                };
            }
        }
         
        return (HashMap<String, String>) map;
    }
  
}
