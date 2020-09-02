package com.king.nowedge.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;

public class ImageCut {

	public static void imgCut(String name, int x, int y, int desWidth,
			int desHeight) throws IOException {
		String srcImageFile = "D:\\wamp\\www\\Uploads\\Picture\\user\\src\\" + name;
		System.out.println(name);
		String cutImageFile = "D:\\wamp\\www\\Uploads\\Picture\\user\\cut\\" + name;
		try {
			Image img;
			ImageFilter cropFilter;
			BufferedImage bi = ImageIO
					.read(new File(srcImageFile));
			int srcWidth = bi.getWidth();
			int srcHeight = bi.getHeight();
			if (srcWidth >= desWidth && srcHeight >= desHeight) {
				Image image = bi.getScaledInstance(srcWidth, srcHeight,
						Image.SCALE_DEFAULT);
				cropFilter = new CropImageFilter(x, y, desWidth, desHeight);
				img = Toolkit.getDefaultToolkit().createImage(
						new FilteredImageSource(image.getSource(), cropFilter));
				BufferedImage tag = new BufferedImage(desWidth, desHeight,
						BufferedImage.TYPE_INT_RGB);
				Graphics g = tag.getGraphics();
				g.drawImage(img, 0, 0, null);
				g.dispose();
				// 输出文件
				ImageIO.write(tag, "JPEG", new File(cutImageFile));
			}
		} catch (IOException e) {
			throw e;
		}
	}
	
	public static boolean isImage(File imageFile) {  
	    if (!imageFile.exists()) {  
	        return false;  
	    }  
	    Image img = null;  
	    try {  
	        img = ImageIO.read(imageFile);  
	        if (img == null || img.getWidth(null) <= 0 || img.getHeight(null) <= 0) {  
	            return false;  
	        }  
	        return true;  
	    } catch (Exception e) {  
	        return false;  
	    } finally {  
	        img = null;  
	    }  
	}  
}
