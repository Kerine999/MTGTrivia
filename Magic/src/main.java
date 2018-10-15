import java.io.IOException;

public class main {

	public static void main(String[] args) throws InterruptedException, IOException {
		String[]aer={"Amonkhet","Modern Masters 2017","Aether Revolt","Kaladesh","Conspiracy: Take the Crown","Eldritch Moon","Eternal Masters", "Shadows Over Innistrad", "Oath of the Gatewatch"};
		MainGUI2 main=new MainGUI2(aer);
		main.display();
	}

}
