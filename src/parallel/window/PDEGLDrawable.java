package parallel.window;

import java.nio.ByteBuffer;

import parallel.window.util.BoundingBox;

public interface PDEGLDrawable {

	public ByteBuffer getPaintable();

	public BoundingBox getBounds();

	public void setBounds(BoundingBox d);

}
