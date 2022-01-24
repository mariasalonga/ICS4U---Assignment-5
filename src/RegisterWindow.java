/*Maria Salonga
 * Monday, December 13, 2021
 * This is the register window in which the user can register their information*/
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class RegisterWindow {
	private Text firstNameText;
	private Text lastNameText;
	private Text emailAddressText;
	private Text usernameText;
	private Text passwordText;
	static int key = 6;
	
	/**
	 * This method encodes the String (password). The password is encoded using a key.
	 * @param password contains the password that the user enters
	 * @param key contains the the number that the password is shifted by.
	 * @return The encoded the password.
	 */
	public static String encodePassword(String password, int key) {
		String encodedPassword = "";
		char messageArray[] = password.toCharArray();
		
		for(int x = 0;x < messageArray.length;x++) {
			int newPosition;
				if((messageArray[x]) >= 65 && messageArray[x] <= 90) {
				
					newPosition = (char) (((messageArray[x] - 'A') + key) % 26);
					messageArray[x] = (char) (newPosition + 'A');
				
				} else if(messageArray[x] >= 97 && messageArray[x] <= 122) {
				
					newPosition = (char) (((messageArray[x] - 'a') + key) % 26);
					messageArray[x] = (char) (newPosition + 'a');
					
				}
			}
		encodedPassword = String.valueOf(messageArray);
		return encodedPassword;

	}
	
	/**
	 * This method counts the lines in a file.
	 * @param file contains the user's information.
	 * @return the number of lines in a file.
	 * @throws IOException
	 */
	public static int countLines(File file) throws IOException {
		Scanner scanner = new Scanner(file);
		
		int lineCount = 0;
		while(scanner.hasNextLine()) {
			
			scanner.nextLine();
			lineCount++;
			
		}
		scanner.close();
		return lineCount;
	}

	/**
	 * This method saves the contents of a file into an array. Each entry in the array is one line of the file.
	 * @param file contains the user's information
	 * @return the contents of the array.
	 * @throws IOException
	 */
	public static String[] saveFileToArray(File file) throws IOException {
		
		int lineCount = countLines(file);
		
		String[] arrayFile = new String[lineCount];
		
		Scanner scanner = new Scanner(file);
		
		for(int x = 0; x < lineCount; x++) {

			arrayFile[x] = scanner.nextLine();
			
		}
		
		scanner.close();
		return arrayFile;
		
	}

	/**
	 *This method prints the contents of 2 arrays onto the file.
	 * @param arrayFile contains the user's information on the file.
	 * @param userInput contains new information.
	 * @param file contains all the user's input.
	 * @throws IOException
	 */
	public static void printOntoFile(String[] arrayFile, String[] userInput, File file) throws IOException {

		PrintWriter output = new PrintWriter(file);
			
			for(int x = 0; x < arrayFile.length; x++) {
			
				output.println(arrayFile[x]);
				
			}
			
		
		for(int x = 0;x < userInput.length;x++) {
			
			output.print(userInput[x]);
			
			if(x != (userInput.length - 1)) {
				
				output.print(";");
				
			}
			
		}
		
		output.close();
		
	}
	
	/**
	 *This method checks if the String (password) is not equal to any entries in a file. It returns a boolean indicating
	 *whether or not the password is valid.
	 * @param password contain's the user's password.
	 * @return nothing.
	 * @throws IOException
	 */
	public static boolean inFile(String password) throws IOException {

		File file = new File("badPasswords.txt");
		boolean validity = true;
		Scanner scanner = new Scanner(file);
		while(scanner.hasNextLine()) {
			
			if(password.equals(scanner.nextLine())) {
				
				validity = false;
				return validity;
			}
			
		}
		scanner.close();
		return validity;
		
	}
	
	/**
	 *This method checks if the String (password) is more than 4 characters. It returns a boolean indicating whether or not the password
	 *was valid.
	 * @param password contains the user's password.
	 * @return boolean that indicates whether or not the password was valid.
	 * @throws IOException
	 */
	public static boolean validPassword(String password) throws IOException{
		
		boolean validity = true;
		
		if(password.length() <= 3) {
			
			validity = false;
			
		}
		
		return validity;
		
	}
	
	/**
	 *This method checks if the String (username) is an entry in a file. 
	 * @param file contains all the bad passwords.
	 * @param username contains the user's username.
	 * @return boolean that indicates if the username is valid or not.
	 * @throws IOException
	 */
	public static boolean validUsername(File file, String username) throws IOException {
		
		boolean validity = true;
		Scanner scanner = new Scanner(file);
		
		while(scanner.hasNextLine()) {
			
			String[] tokens = ((scanner.nextLine()).split(";"));
			
			if((tokens[2]).equals(username)) {
				
				validity = false;
				return validity;
				
			}
			
		}
		scanner.close();
		return validity;
		
	}
	
	//
	/**
	 *This method counts how many times a char (letter) appears in a String (word).
	 * @param word contains the email that the user has entered.
	 * @param letter contains the character that is being counted in the word.
	 * @return integer that represents how many times the letter appears in the word.
	 */
	public static int countChar(String word, char letter) {
		
		int count = 0;
		char[] array = word.toCharArray();
		
		for(int x = 0; x < array.length;x++) {
			
			if(array[x] == letter) {
	
				count++;
				
			}
			
		}
		
		return count;
		
	}
	
	/**
	 * This method checks if the email has maximum 1 '@', a letter before the '@', and a dot after the '@'. It returns a boolean indicating
	 * if the email is valid or not.
	 * @param email contains the email the user has inputed.
	 * @return boolean that indicates if the email is valid or not.
	 */
	public boolean validEmail(String email) {
		
		boolean validity = false;
		char[] array;
		boolean letterChar = false;
		boolean letterCharTwo = false;
		boolean letterCharThree = false;
		int dotCharPlace = 0;
		
		array = email.toCharArray();
		
		if(countChar(email, '@') == 1) {
			
			for(int x = 0;x < array.length;x++) {
				
				if(array[x] == '.' && x > email.indexOf("@")) {
					
					dotCharPlace = x;
							
				}
			
			}
			
		}
		
			for(int x = 0; x < array.length ; x ++) {
			
				if(array[x] >= 65 && array[x] <= 90 && x < email.indexOf("@")) {
					
					letterChar = true;
					
					
				} else if (array[x] >= 97 && array[x] <= 122 && x < email.indexOf("@")) {
					
					letterChar = true;
					
					
				} else if(array[x] >= 65 && array[x] <= 90 && x > email.indexOf("@") && dotCharPlace > x) {
					
					letterCharTwo = true;
					
					
				} else if (array[x] >= 97 && array[x] <= 122 && x > email.indexOf("@") && dotCharPlace > x) {
					
					letterCharTwo = true;
				
					
				} else if(array[x] >= 65 && array[x] <= 90 && x > dotCharPlace) {
					
					letterCharThree = true;
					
					
				} else if (array[x] >= 97 && array[x] <= 122 && x > dotCharPlace) {
					
					letterCharThree = true;
					
					
				}
			
			}
		
			if(letterChar == true && letterCharTwo == true && letterCharThree == true) {
			
				validity = true;
			
			}
			
			return validity;
		}
		
	
	/**
	 * This is the constructor. The register window is created here.
	 * @param display
	 */
	public RegisterWindow(Display display) {
		
		Shell shell = new Shell(display);
		
		shell.setImage(SWTResourceManager.getImage("images\\site-logo.png"));
		
		//These are the components of the registration window
		Label instructionLabel = new Label(shell, SWT.NONE);
		instructionLabel.setBounds(113, 27, 222, 25);
		instructionLabel.setText("Fill out this form to Register");
		
		Label firstNameLabel = new Label(shell, SWT.NONE);
		firstNameLabel.setBounds(31, 71, 99, 25);
		firstNameLabel.setText("First Name:");
		
		firstNameText = new Text(shell, SWT.BORDER);
		firstNameText.setBounds(31, 113, 395, 31);
		
		Label lastNameLabel = new Label(shell, SWT.NONE);
		lastNameLabel.setText("Last Name:");
		lastNameLabel.setBounds(31, 173, 99, 25);
		
		lastNameText = new Text(shell, SWT.BORDER);
		lastNameText.setBounds(31, 215, 395, 31);
		
		Label emailAddressLabel = new Label(shell, SWT.NONE);
		emailAddressLabel.setText("Email Address:");
		emailAddressLabel.setBounds(31, 283, 128, 25);
		
		emailAddressText = new Text(shell, SWT.BORDER);
		emailAddressText.setBounds(31, 325, 395, 31);
		
		Label usernameLabel = new Label(shell, SWT.NONE);
		usernameLabel.setText("Username:");
		usernameLabel.setBounds(31, 388, 99, 25);
		
		usernameText = new Text(shell, SWT.BORDER);
		usernameText.setBounds(31, 430, 395, 31);
		
		Label passwordLabel = new Label(shell, SWT.NONE);
		passwordLabel.setText("Password:");
		passwordLabel.setBounds(31, 488, 99, 25);
		
		passwordText = new Text(shell, SWT.BORDER | SWT.PASSWORD);
		passwordText.setBounds(31, 530, 395, 31);
		
		//This is the action listener for the register button
		Button registerButton = new Button(shell, SWT.NONE);
		registerButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				try {
					
					//These are the variables that I created to store the user's input
					File userInfo = new File("userInfo.txt");
					String[] userInput = new String[5];
					
					//This if statement checks that the username, password, and email text boxes are not left empty and notifies the user
					if (usernameText.getText().equals("") || passwordText.getText().equals("") || emailAddressText.getText().equals("")) {
						
						MessageBox emptyUsernameField = new MessageBox(shell, SWT.ICON_WARNING);
						emptyUsernameField.setMessage("Username/Password/Email field cannot be left empty.");
						emptyUsernameField.open();
					
					//This checks that the password is more than 3 characters
					} else if(!(validPassword(passwordText.getText()))){
						
						MessageBox emptyUsernameField = new MessageBox(shell, SWT.ICON_WARNING);
						emptyUsernameField.setMessage("Password must be at least 4 characters long.");
						emptyUsernameField.open();
					
					//This checks that the user's password is not one of the "bad passwords" I have created
					} else if(!(inFile((passwordText.getText())))) {
						
						MessageBox invalidPassword = new MessageBox(shell, SWT.ICON_WARNING);
						invalidPassword.setMessage("Invalid password. Please try again.");
						invalidPassword.open();
					
					//This checks that the username does not already exist
					} else if(!(validUsername(userInfo,usernameText.getText()))) {
						
						MessageBox invalidUsername = new MessageBox(shell, SWT.ICON_WARNING);
						invalidUsername.setMessage("Username already exists.");
						invalidUsername.open();
					
					/*This checks that the email contains one,  and only one, ‘@” and at least a ‘.’ after the ‘@’, and if
					the email address has at least one letter before the ‘@’*/
					} else if(!(validEmail(emailAddressText.getText()))){
						
						MessageBox invalidEmail = new MessageBox(shell, SWT.ICON_WARNING);
						invalidEmail.setMessage("Invalid email. Please try again.");
						invalidEmail.open();
						
					} else {
					
						//These strings contain what the user is inputing		
						userInput[0] = firstNameText.getText();
						userInput[1] = lastNameText.getText();
						userInput[2] = usernameText.getText();
						userInput[3] = encodePassword(passwordText.getText(), key);
						userInput[4] = emailAddressText.getText();
						
						//This array saves the user's previous info
						String[] arrayFile = saveFileToArray(userInfo);
						printOntoFile(arrayFile, userInput, userInfo);
						
						//This notifies the user that they have registered
						MessageBox successfulRegister = new MessageBox(shell, SWT.ICON_WARNING);
						successfulRegister.setMessage("You have registered.");
						successfulRegister.open();
						shell.close();
					
					}
					
				} catch(Exception i) {
					
					i.printStackTrace();
					
				}
				
			}
		});
		registerButton.setBounds(179, 607, 105, 35);
		registerButton.setText("Register");		
		
		shell.setText("Registration Window");
		shell.setSize(491, 734);
		
		shell.layout();
		shell.open();
			
		while (!shell.isDisposed()) {
			
			if (!display.readAndDispatch()) {
				
				display.sleep();
				
			}
			
		}

	}

}
