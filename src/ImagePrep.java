import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * 
 * Relies on twelvemonkeys image.io libraries, so update/check those if new data formats break.
 * 
 * @author Reithger
 *
 */

public class ImagePrep {

	private static final String imageSource = "C:/Users/Reithger/Downloads/";
	private static final String imageDestinationFolder = "gen_img";
	private static final String outputFileType = "jpg";
	private static final int MAX_SIZE = 1500;
	
	
	public static void main(String[] args) throws IOException {
		File directory = new File(imageSource);
		File[] images = directory.listFiles();
		File dir = new File(imageSource + imageDestinationFolder);
		dir.mkdir();
		for(File f : images) {
			if(!f.getName().contains(".ini") && !f.getName().contains(imageDestinationFolder)) {
				System.out.println(f.getName());
				BufferedImage buf = ImageIO.read(f);
				int wid = buf.getWidth();
				int hei = buf.getHeight();
				if(hei > MAX_SIZE && hei < wid) {
					wid = wid*MAX_SIZE/hei;
					hei = MAX_SIZE;
				}
				else if (wid > MAX_SIZE && wid < hei) {
					hei = hei*MAX_SIZE/wid;
					wid = MAX_SIZE;
				}
				BufferedImage out = new BufferedImage(wid, hei, BufferedImage.TYPE_INT_RGB);
				Graphics2D g = out.createGraphics();
				g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
				g.drawImage(buf, 0, 0, wid, hei, 0, 0, buf.getWidth(), buf.getHeight(), null);
				g.dispose();
				String name = f.getName();
				name = name.replaceAll("CMYK", "RGB");
				name = name.replaceAll("\\.((jpg)|(tif)|(png))",  "");
				File outF = new File(dir.getAbsolutePath() + "/" + name + "." + outputFileType);
				System.out.println(ImageIO.write(out, outputFileType, outF));
			}
		}
		System.out.println("Done");
	}
	
}
