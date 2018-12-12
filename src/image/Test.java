package image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Test {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		BufferedImage image = VerifyCode.getImage();
		VerifyCode.output(image, new FileOutputStream(new File("C:\\Users\\zhufukun\\Desktop\\2.jpeg")));
	}

}
