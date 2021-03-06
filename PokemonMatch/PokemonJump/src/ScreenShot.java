

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ScreenShot {
	/**
	 * method to capture screen shot
	 * 
	 * @param String
	 *            uploadPath to save screen shot as image
	 * @returns boolean true if capture successful else false
	 */
	public static boolean captureScreenShot(String uploadPath) {
		boolean isSuccesful = false;
		Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit()
				.getScreenSize());
		BufferedImage capture;
		try {
			capture = new Robot().createScreenCapture(screenRect);
			// screen shot image will be save at given path with name
			// "screen.jpeg"
			ImageIO.write(capture, "jpg", new File(uploadPath, "screen.jpeg"));
			isSuccesful = true;
		} catch (AWTException awte) {
			awte.printStackTrace();
			isSuccesful = false;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			isSuccesful = false;
		}
		return isSuccesful;
	}
	
	public static void main(String[] args){
		captureScreenShot("C:/Users/John/Desktop/pokemonCries/Screenshot");
	}
}