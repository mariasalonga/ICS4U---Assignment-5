/*Maria Salonga
 * Monday, December 13, 2021
 * The screen will ask for the user’s name and password. It will check the user’s name and the digested
 * password against a userName file that contains the users’ names, email addresses and digested passwords. 
*/
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class MainWindow {
	private static Text usernameTextBox;
	private static Text passwordTextBox;
	private static final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	static Shell loginWindow = new Shell();
	static int key = 6;

	/**
	 * This method checks that a String (username) and a String (password) are in the same entry of a file.
	 * @param file contains all the users' information.
	 * @param username contains the username that the user enters.
	 * @param password contains the password that the user enters.
	 * @return boolean that indicates if the login is valid.
	 * @throws IOException
	 */
	public static boolean checkLogin(File file, String username, String password) throws IOException {
		
		Scanner scanner = new Scanner(file);
		
		boolean loginValidity = false;
		
		while(scanner.hasNextLine()) {
			
			String[] tokens = ((scanner.nextLine()).split(";"));
			
			if((tokens[2].equals(username)) && (tokens[3].equals(password))) {
				
				loginValidity = true;
			
				scanner.close();

				return loginValidity;
				
			}
			
		}
		scanner.close();
		return loginValidity;
		
	}
	
	/**
	 * This method encodes a String (password) using an int (key).
	 * @param password contains the password that the user enters.
	 * @param key contains the number that the password is shifted by
	 * @return the encoded password shifted by the key
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
	 * This method retrieves the corresponding email to a String (username) from a file.
	 * @param file contains all users' information
	 * @param username contains the username that the user enters
	 * @return  the email that corresponds to the username
	 * @throws IOException
	 */
	public static String getEmail(File file, String username) throws IOException {

		Scanner scanner = new Scanner(file);
		String email = "";
		
		while(scanner.hasNextLine()) {
			
			String[] tokens = ((scanner.nextLine()).split(";"));
			
			if(tokens[2].equals(username)) {
				email = tokens[4];
				scanner.close();
				return email;
			}
			
		}
		scanner.close();
		return email;
		
	}

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		
		//This is the display
		Display display = Display.getDefault();
		
		//Here I am creating message boxes and setting
		MessageBox blankUserMessage = new MessageBox(loginWindow, SWT.ICON_WARNING);
		blankUserMessage.setMessage("Username field cannot be left blank.");
		
		MessageBox usernameNonExistent = new MessageBox(loginWindow, SWT.ICON_WARNING);
		usernameNonExistent.setMessage("Username does not exist.");
		
		MessageBox incorrectField = new MessageBox(loginWindow, SWT.ICON_WARNING);
		incorrectField.setMessage("Password or username is incorrect.");

		loginWindow.setSize(876, 548);
		loginWindow.setText("Assignment 5 - Login");
		loginWindow.setImage(SWTResourceManager.getImage("images\\site-logo.png"));
		
		//This creates the label on the frame
		Label verticalSeparator = new Label(loginWindow, SWT.SEPARATOR | SWT.VERTICAL);
		verticalSeparator.setBounds(352, 50, 2, 367);
				
		//This creates the text boxes for the user to input the name and password
		usernameTextBox = new Text(loginWindow, SWT.BORDER);
		usernameTextBox.setBounds(27, 167, 237, 31);
				
		Label Username = new Label(loginWindow, SWT.NONE);
		Username.setBounds(27, 137, 81, 25);
		Username.setText("Username");
				
		Label Password = new Label(loginWindow, SWT.NONE);
		Password.setText("Password");
		Password.setBounds(27, 272, 81, 25);
				
		passwordTextBox = new Text(loginWindow, SWT.BORDER | SWT.PASSWORD);
		passwordTextBox.setBounds(27, 303, 237, 31);	
				
		//This creates the login button for the user to press
		Button LoginButton = formToolkit.createButton(loginWindow, "Login", SWT.NONE);
		LoginButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
						
					try {
						//This scanner reads through the file
						File userInfo = new File("userInfo.txt");
							
						//These variables hold the user's input
						String username = usernameTextBox.getText();
						String password = passwordTextBox.getText();
						
						//This checks if the username box is empty
						if(username.equals("")) {
							
							blankUserMessage.open();
						
						//This checks if the login is invalid and notifies the university
						} else if(!(checkLogin(userInfo, username, encodePassword(password, key)))) {
							
							incorrectField.open();
						
						//This checks if the login is valid and notifies the user
						} else if(checkLogin(userInfo, username, encodePassword(password, key))) {
							
							MessageBox successfulLogin = new MessageBox(loginWindow, SWT.ICON_WARNING);
							successfulLogin.setMessage("You have logged in successfully!");
							successfulLogin.open();
								
						}
							
					} catch (IOException e1){
							
					}
				
				}
			});
			LoginButton.setBounds(115, 432, 105, 35);
						
			//This creates the image on the GUI
			Label image = new Label(loginWindow, SWT.NONE);
			image.setImage(SWTResourceManager.getImage("images\\site-logo.png"));
			image.setBounds(451, 75, 300, 321);
			formToolkit.adapt(image, true, true);
			
			
			
			//This is the action listener for the register button
			Button btnRegister = new Button(loginWindow, SWT.NONE);
			btnRegister.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
						
					new RegisterWindow(display);
						
				}
			});
			btnRegister.setBounds(366, 432, 105, 35);
			formToolkit.adapt(btnRegister, true, true);
			btnRegister.setText("Register");
			
			//This disposes the display after the main window is closed.
			loginWindow.addListener(SWT.Close, new Listener()
		     {
		        @Override
		        public void handleEvent(Event event)
		        {
		           
		           display.dispose();
		           
		        }
		     });
			
			//This is the action listener for the forgot password button
			Button btnForgotPassword = new Button(loginWindow, SWT.NONE);
			btnForgotPassword.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {

						
					try {
						//This is the variable for the file
						File userInfo = new File("userInfo.txt");
							
						//This variable holds the user's input
						String username = usernameTextBox.getText();
							
						//This checks if the username text box is empty
						if(username.equals("")){
								
							blankUserMessage.open();
							
						//This checks if the user exists
						} else if(getEmail(userInfo, username).equals("")) {
								
							usernameNonExistent.open();
							
						//This notifies the user that an email has been sent to their user
						} else {
								
							MessageBox messageBox = new MessageBox(loginWindow, SWT.ICON_WARNING);
							messageBox.setMessage("An email has been sent to: " + getEmail(userInfo, username));
							messageBox.open();
								
						}
							
					} catch (IOException e1) {
							
					
					}
						
						
				}
			});
			
				btnForgotPassword.setBounds(595, 432, 146, 35);
				formToolkit.adapt(btnForgotPassword, true, true);
				btnForgotPassword.setText("Forgot Password");

				loginWindow.open();
				loginWindow.layout();
				while (!loginWindow.isDisposed()) {
					
				if (!display.readAndDispatch()) {
					
					display.sleep();
				
			}
			
		}
		
	}
	
}
