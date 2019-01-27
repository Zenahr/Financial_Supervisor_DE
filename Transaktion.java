import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaktion {
	
	private String artikel;
	private String details;
	Date datum;
	Transaktionstyp typ;
	double betrag;
	
	public Transaktion(String titel, String details, String datum, Transaktionstyp typ, double betrag) {
		this.artikel = titel;
		this.details = details;
		String pattern = "dd.MM.yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		try { 
			this.datum = simpleDateFormat.parse(datum);
		} catch (ParseException e) {
			System.err.println("Ung�ltiges Datum");
		}
		this.typ = typ;		
		this.betrag = betrag;
		if (typ == Transaktionstyp.Ausgabe && this.betrag > 0) {
			this.betrag = -this.betrag;
		}
	}
	
	
	public String toString() {
		return "TITEL: " + artikel + "\r\n" + "DETAILS: " + details + "\r\n" + "DATUM: " + datum + "\r\n" + "\r\n" + "BETRAG: " 
	+ betrag + "\r\n" + "//////////////////////////////////////////////////////////" + "\r\n" + "\r\n" + "\r\n";
		
	}

}
