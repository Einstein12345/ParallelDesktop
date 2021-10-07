package parallel.window;

import java.nio.ByteBuffer;

import parallel.DesktopManager;
import parallel.window.util.BoundingBox;
import terra.shell.utils.JProcess;

public abstract class Frame extends JProcess implements PDEGLDrawable {
	private static ByteBuffer buffer;
	private boolean resizable = true, visible = false;
	private BoundingBox bounds = new BoundingBox(500, 500, 0, 0);
	private final double frameId = Math.random();
	private long frameRate = 25;

	public Frame() {

	}

	public synchronized ByteBuffer getPaintable() {
		return buffer;
	}

	public void setResizable(boolean resize) {
		resizable = resize;
	}

	public boolean isResizable() {
		return resizable;
	}

	public void setSize(BoundingBox d) {
		this.bounds = d;
	}

	public void setBounds(BoundingBox d) {
		setSize(d);
	}

	public BoundingBox getBounds() {
		return bounds;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean vis) {
		this.visible = vis;
	}

	public double getFrameId() {
		return frameId;
	}

	public void setFrameRate(long frameRate) {
		this.frameRate = frameRate;
	}

	public long getFrameRate() {
		return frameRate;
	}

	public void dispose() {
		visible = false;
		DesktopManager.removeWindow(frameId);
		buffer = null;
		stop();
		halt();
	}

	public abstract ByteBuffer paint();

	@Override
	public boolean start() {
		long msSleep = (1000 / frameRate);
		try {
			while (true) {
				Thread.sleep(msSleep);
				if (!visible) {
					continue;
				}
				buffer = paint();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
