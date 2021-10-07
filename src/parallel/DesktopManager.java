package parallel;

import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glDrawPixels;
import static org.lwjgl.opengl.GL11.glRasterPos2i;

import java.awt.Image;
import java.awt.image.PixelGrabber;
import java.nio.ByteBuffer;
import java.util.Enumeration;
import java.util.Hashtable;

import parallel.window.Frame;
import parallel.window.util.BoundingBox;
import terra.shell.logging.LogManager;
import terra.shell.logging.Logger;

@SuppressWarnings("unused")
public class DesktopManager {
	private Logger log = LogManager.getLogger("PDE_DM");
	private long topWindowId;
	private static ThemeManager tm;
	private static Hashtable<Double, Frame> frames;
	private int width, height;
	private static double focusedFrame = -1;
	private static ByteBuffer backgroundImage;

	public DesktopManager(long topWindow, int width, int height) {
		log.log("Starting DesktopManager");
		this.width = width;
		this.height = height;
		log.debug("Got width and height " + width + ":" + height);
		frames = new Hashtable<Double, Frame>();
		topWindowId = topWindow;
		tm = new ThemeManager(topWindow);
		log.debug("Created ThemeManager");
		if (backgroundImage == null) {
			// Create default background
		}
		log.debug("Looping");
		loop();
	}

	private void loop() {
		glfwSwapBuffers(topWindowId);
		// Draw all visible windows;
		// Draw Background image first;
		glRasterPos2i(0, 0);
		glDrawPixels(width, height, GL_RGB, GL_UNSIGNED_BYTE, backgroundImage);

		if (focusedFrame == -1) {
			try {
				Thread.sleep(1000 / 15);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		}

		Frame focusedFrame = frames.get(DesktopManager.focusedFrame);

		Enumeration<Frame> frameList = frames.elements();
		while (frameList.hasMoreElements()) {
			Frame f = frameList.nextElement();
			if (f.getFrameId() == DesktopManager.focusedFrame)
				continue;
			BoundingBox frameBounds = f.getBounds();
			ByteBuffer framePixels = f.getPaintable();

			glRasterPos2i((int) frameBounds.getX(), (int) frameBounds.getY());
			glDrawPixels((int) frameBounds.getWidth(), (int) frameBounds.getHeight(), GL_RGB, GL_UNSIGNED_BYTE,
					framePixels);
		}

		BoundingBox frameBounds = focusedFrame.getBounds();
		ByteBuffer framePixels = focusedFrame.getPaintable();

		glRasterPos2i((int) frameBounds.getX(), (int) frameBounds.getY());
		glDrawPixels((int) frameBounds.getWidth(), (int) frameBounds.getHeight(), GL_RGB, GL_UNSIGNED_BYTE,
				framePixels);
		try {
			Thread.sleep(1000 / 30);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void addWindow(Frame f) {
		frames.put(f.getFrameId(), f);
		if (f.isVisible()) {
			focusedFrame = f.getFrameId();
		}
	}

	public static void removeWindow(double id) {
		frames.remove(id);
		if (id == focusedFrame) {
			if (frames.size() == 0) {
				focusedFrame = -1;
				return;
			}
			focusedFrame = frames.keys().nextElement();
		}
	}

	public static ThemeManager getThemeManager() {
		return tm;
	}

	public void setBackgroundImage(Image i) throws InterruptedException {
		i = i.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		int w = i.getWidth(null);
		int h = i.getHeight(null);
		int[] pixels = new int[w * h];
		PixelGrabber pg = new PixelGrabber(i, 0, 0, w, h, pixels, 0, w);
		pg.grabPixels();
		byte[] data = new byte[pixels.length];
		for (int j = 0; j < data.length; j++) {
			data[j] = (byte) pixels[j];
		}
		backgroundImage = ByteBuffer.wrap(data);
	}

}
