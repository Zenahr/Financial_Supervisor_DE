import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {

	private static Scanner fileSc = null;
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
		String path = pathFinder(sc);
		while (path == null) {
			path = pathFinder(sc);
		}

		System.out.println("M�chten Sie eine Transaktion protokollieren? Wenn ja dann (p) wenn nicht, dann eben nicht");
		while (sc.nextLine().equals("p")) {

			readInput(path);
			System.out.println(
					"M�chten Sie eine weitere Transaktion protokollieren? Wenn ja dann (p) wenn nicht, dann eben nicht");
		}
		File file = new File(path);
		String sum = calcSumString(file);
		String wasSchonDrinIs = wasSchonDrinis(file);
		PrintWriter writeWithSum;
		try {
			writeWithSum = new PrintWriter(file);
			writeWithSum.write(wasSchonDrinIs + "<<<SUMME:" + sum + ">>>" + "\r\n");
			writeWithSum.close();
		} catch (FileNotFoundException e) {
		}
		close();
	}

	// Aus der ifElseAbfragen Klasse
	public static String pathFinder(Scanner pathFinderScanner) throws IOException {
		System.out.println(
				"Bitte geben Sie Ihren lokalen Windows Benutzernamen an. \r\nDie Datei FINANZEN.txt wird auf Ihrem Desktop zu finden sein");
		String raw_text = "C://users/" + pathFinderScanner.next() + "/Desktop/FINANZEN.txt";
		File datei = new File(raw_text);
		if (datei.canWrite() || datei.createNewFile()) {
			return raw_text;
		} else {
			return null;
		}
	}

	// Aus der ifElseAbfragen Klasse
	public static String wasSchonDrinis(File datei) {
		String toWrite = "";
		try {
			@SuppressWarnings("resource")
			Scanner fileSc = new Scanner(datei); // + das was vorher drin war
			boolean first = true;
			while (fileSc.hasNextLine()) {
				if (first) {
					toWrite = fileSc.nextLine();
				} else {
					toWrite = toWrite + "\r\n" + fileSc.nextLine();
				}
				first = false;
			}
		} catch (FileNotFoundException a) {
		}
		toWrite = toWrite.replaceAll("<<<.*?>>>", "");
		return toWrite;
	}

	public static void close() {
		sc.close();
		if (fileSc != null) {
			fileSc.close();
		}
	}

	private static void toFile(Transaktion transaktion, String path) {
		File finanzenDatei = new File(path);
		try {
			String dasiswichtig = wasSchonDrinis(finanzenDatei);// methodenaufruf statt towrite dann
			// ergebnis der methode
			PrintWriter textToFile = new PrintWriter(finanzenDatei);
			if (dasiswichtig.equals("")) {
				dasiswichtig = transaktion.toString();
			} else {
				dasiswichtig = dasiswichtig + "\r\n" + transaktion.toString();
			}

			textToFile.write(dasiswichtig);
			textToFile.close();
		} catch (FileNotFoundException e) {
			System.err.println("Da ging etwas schief");
			e.printStackTrace();
		}
	}

	public static void readInput(String path) {
		System.out.println("TITEL: ");
		String artikel = sc.nextLine();
		System.out.println("DETAILS: ");
		String details = sc.nextLine();
		System.out.println("DATUM: ");
		String datum = sc.nextLine();
		System.out.println("EINNAHME (e) /AUSGABE (a): ");
		Transaktionstyp typ;
		if (sc.nextLine().toLowerCase().equals("e")) {
			typ = Transaktionstyp.Einnahme;
		} else {
			typ = Transaktionstyp.Ausgabe;
		}
		System.out.println("BETRAG: ");
		String betragString = sc.nextLine();
		betragString = betragString.replace(",", ".");
		double betrag = Double.parseDouble(betragString);
		Transaktion t = new Transaktion(artikel, details, datum, typ, betrag);
		toFile(t, path);
	}

	public static String calcSumString(File datei) {
		String raw_text = wasSchonDrinis(datei);
		raw_text = textExtractor(raw_text); // Hierbei werden alle nicht-numerischen Zeichen entfernt.
		double sum = 0;
		String parseMePlease = "";
		while (raw_text.length() > 0) {
			if (raw_text.charAt(0) == ' ') {
				if (parseMePlease.equals("") || parseMePlease.equals(" ")) {
					sum = sum + 0;
				} else {
					sum = sum + Double.parseDouble(parseMePlease);
					parseMePlease = "";
				}
			} else {
				parseMePlease = parseMePlease + raw_text.charAt(0);
			}
			raw_text = raw_text.substring(1);
		}
		sum = sum + Double.parseDouble(parseMePlease);
		return sum + "";
	}

	/**
	 * Entfernt alle nicht-numerischen Zeichen.
	 * 
	 * @param unformattierter
	 *            Originaltext
	 **/
	public static String textExtractor(String string) {
		string = string.replaceAll("\r\n", "");
		string = string.replaceAll("TITEL:.*?BETRAG:", "");
		string = string.replaceAll("/", "");
		return string;
	}
}
