import java.util.*;

class Main {
	
	public static void main (String [] args) throws Exception {
		try (Scanner scan = new Scanner(System.in)) {
			System.out.println("WELCOME!");
			
			System.out.println("Want do you want to do?");
			System.out.println("1. Compression");
			System.out.println("2. Decompression");
			System.out.print("Enter your choice : ");
			int choice = scan.nextInt();
			switch(choice) {
				case 1 : 
					Encode.encode(); // As the method is static can access it without creating object
					break;
				case 2 :
					Decode.decompress();
					break;
				default : System.out.println("Incorrect Choice."); 
				
			}
			
		}
	}
}

