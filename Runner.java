package ie.atu.sw;

public class Runner {

	public static void main(String[] args) throws Exception { // MAIN METHOD FOR APPLICATION
		try {
			Menu m = new Menu(); // NEW INSTANCE OF MENU
			m.start(); // APPLICATION START
		} catch (Exception e) { // Throwable Exception
			System.out.println("[ERROR] Apllication has stopped working!!!"); // SYSTEM CRASH ERROR //$NON-NLS-1$
																				// MESSAGE
			e.printStackTrace(); // //prints the throwable along with other details like the line number and
									// class name where the exception occurred
		}
	}

}
