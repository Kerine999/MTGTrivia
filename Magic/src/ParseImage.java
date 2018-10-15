import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.awt.*;

//some code written by Kalani Ruwanpathirana 
//Author: Anthony Cho
public class ParseImage {
	public static ArrayList<ImageIcon> imageParce(String set, int name, int after) throws IOException {
		String modifiedName = "";
		String URL ="http://image.deckbrew.com/mtg/multiverseid/"+name+".jpg";
		
		//URL="https://image.deckbrew.com/mtg/multiverseid/423771.jpg";
		String fileName = name + ".jpg";
		//fileName="423771.jpg";
		//get the file?
		File varTmpDir = new File(fileName);
		boolean exists = varTmpDir.exists();
		if (!exists) {
			try (InputStream in = new URL(URL).openStream()) {
				Files.copy(in, Paths.get(fileName));
			}

		}
		File file = new File(fileName);
		FileInputStream fis = new FileInputStream(file);
		BufferedImage image = ImageIO.read(varTmpDir); // reading the image file
		BufferedImage imgs[] = new BufferedImage[5]; // Image array to hold
														// image chunks
		//the full card
		imgs[0] = image;

		//just the photo
		imgs[1] = new BufferedImage(223, 311, image.getType());
		Graphics2D gr = imgs[1].createGraphics();
		if(after==-1){
		gr.drawImage(image, 17, 35, 206, 173, 17, 35, 206, 173, null);}
		else if(after==0){
			gr.drawImage(image, 17, 35, 206, 105, 17, 35, 206, 105, null);}
		else if(after==1){
			gr.drawImage(image, 122, 174, 187, 285, 122, 174, 187, 285, null);}
		gr.dispose();

		//for finding power/toughness
		imgs[2] = new BufferedImage(223, 311, image.getType());
		Graphics2D gr1 = imgs[2].createGraphics();
		gr1.drawImage(image, 171, 277, 212, 296, 171, 277, 212, 296, null);
		gr1.dispose();

		//name+image
		imgs[3]= new BufferedImage (312,240, image.getType());
        {
             Graphics2D gr2 = imgs[3].createGraphics();
             gr2.drawImage(image, 0, 0, 160, 35, 0, 0, 160, 35, null);
     		 gr2.drawImage(image, 0, 35, 206, 173, 0, 35, 206, 173, null);
     		 gr2.dispose();
    }
        //image+mana cost
        imgs[4]= new BufferedImage (312,240, image.getType());
        {
             Graphics2D gr3 = imgs[4].createGraphics();
             gr3.drawImage(image, 160, 0, 223, 35, 160, 0, 223, 35, null);
             gr3.drawImage(image, 17, 35, 223, 173, 17, 35, 223, 173, null);
             gr3.dispose();
    }
       
		
		ArrayList<ImageIcon> convert = new ArrayList<ImageIcon>();
		for (int i = 0; i < imgs.length; i++) {
			convert.add(new ImageIcon(imgs[i]));
		}
		varTmpDir.delete();
		file.delete();
		return convert;
	}
	public static BufferedImage image(int name) throws IOException {
		String modifiedName = "";
		String URL ="http://image.deckbrew.com/mtg/multiverseid/"+name+".jpg";
		
		//URL="https://image.deckbrew.com/mtg/multiverseid/423771.jpg";
		String fileName = name + ".jpg";
		//fileName="423771.jpg";
		//get the file?
		File varTmpDir = new File(fileName);
		boolean exists = varTmpDir.exists();
		if (!exists) {
			try (InputStream in = new URL(URL).openStream()) {
				Files.copy(in, Paths.get(fileName));
			}

		}
		File file = new File(fileName);
		FileInputStream fis = new FileInputStream(file);
		BufferedImage image = ImageIO.read(varTmpDir); // reading the image file
		
		varTmpDir.delete();
		file.delete();
		
		return image;
	}
}
