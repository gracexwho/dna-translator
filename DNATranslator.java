import java.util.Scanner;
import java.io.*;

public class DNATranslator {

public static void main(String[] args) {
	start();
}

public static void start() {
	// starts the program, recurses if the user wants to continue
	Scanner in = new Scanner(System.in);
	System.out.println("Please input your DNA sequence.");
	String dna = in.nextLine();

	System.out.println("What would you like to do?");
	System.out.println("forward translation=[ftrans], reverse translation = [rtrans], reverse complement=[revs]");
	String command = in.nextLine();
	// three options: provide forward translation of DNA sequence entered, reverse translation, or reverse complement

	dna = dna.toUpperCase();
	if (dna.contains(""))

	switch(command) {
		case "ftrans":
		translation(dna);
		break;

		case "rtrans":
		dna = reverseComplement(dna);
		translation(dna);
		break;

		case "revs":
		reverseComplement(dna);
		break;

		default: System.out.println("Not a valid command.");
		break;

	} 

	System.out.println();
	System.out.println("Would you like to continue? Enter yes or no");
	command = in.nextLine();

	if (command.equals("yes")) {
		start();
	} else {
		System.out.println("Goodbye!");
		System.exit(0);
	}



}



public static String translation(String dna) {
	// converts DNA to mRNA then provides the amino acid sequence of the DNA if it's translated
	dna = mRNA(dna);
	// must also convert all of thymine to U

	String[] sequence = new String[64];
	String[] codon = new String[64];
	String[] type = new String[64];


	String translation = "";
	String description = "";

	try {
		// UUU Phe F Phenylalanine

	FileReader fr = new FileReader("codontable.txt");
	BufferedReader br = new BufferedReader(fr);

	String temp = br.readLine();
	int index=0;

	while(temp!=null) {
		String[] parts = temp.split(" ");
		sequence[index] = parts[0];
		type[index] = parts[1];
		codon[index] = parts[2];

		temp = br.readLine();
		index++;
	}

	br.close();
	fr.close();


} catch(IOException e) {
	System.out.println("IOException occurred.");
}

	// now find the DNA sequence three characters at a time

	int outOfFrame = dna.length()%3;
	String oof = "";

	if (outOfFrame!=0) {
		System.out.println("The reading frame of the DNA sequence is off.");

		if (outOfFrame==1) {
			oof = Character.toString(dna.charAt(dna.length()-1));
			dna = dna.substring(0, dna.length()-1);
			// removes the last character from the dna sequence and stores it in oof
		} else {
			oof = Character.toString(dna.charAt(dna.length()-2)) + Character.toString(dna.charAt(dna.length()-1));
			dna = dna.substring(0,dna.length()-2);
		}
	}

	for (int i=0;i<dna.length()-2;i+=3) {
		String seq = "";

		seq = seq + Character.toString(dna.charAt(i)) + Character.toString(dna.charAt(i+1)) + Character.toString(dna.charAt(i+2));
		System.out.println("Current sequence " + seq);

		for (int j=0;j<sequence.length;j++) {
			// searches the sequences in the database for the matching sequence
			if (sequence[j].equals(seq)) {
				translation = translation + codon[j] + " ";
				description = description + type[j] + " ";
				break;
				// concatenates the codon corresponding to the sequence that we found in sequence[] to the translation String
			} 
			if (j==63) {
				System.out.println("DNA sequences should only contain A,C,G,T.");
				return "Error";
				// If the program can't find the matching sequence, that means the user must have entered something that doesn't exist.
			}
		}
	}

	oof = oof.toLowerCase();
	translation = translation + oof;

	System.out.println("Here is the translation:");
	System.out.println(translation);
	System.out.println(description);

	return translation;

}

public static String mRNA(String dna) {
	String mRNA = "";

	for (int i=0;i<dna.length();i++) {
		if (dna.charAt(i)=='T') {
			mRNA = mRNA + "U";
		} else {
			mRNA = mRNA + Character.toString(dna.charAt(i));
		}
	}
	return mRNA;
}

public static String reverseComplement(String dna) {
	String revs = "";
	// A <-> T
	// G <-> C
	for (int i=dna.length()-1;i>=0;i--) {
		// the reverse complement basically returns the other strand in 5' to 3' direction
		// so if the string inputed is the leading strand, this returns the lagging strand

		switch(dna.charAt(i)) {
			case 'A': revs = revs + Character.toString('T');
			break;
			case 'T': revs = revs + Character.toString('A');
			break;
			case 'C': revs = revs + Character.toString('G');
			break;
			case 'G': revs = revs + Character.toString('C');
			break;
			default:
			System.out.println("DNA sequences should only contain A,C,G,T.");
			return "Error";
		}
	}
	System.out.println("Here is the reverse complement:");
	System.out.println(revs);

	return revs;
}

}
