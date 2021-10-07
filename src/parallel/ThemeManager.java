package parallel;

import java.io.File;

import terra.shell.config.Configuration;
import terra.shell.launch.Launch;

import static org.lwjgl.glfw.GLFW.*;

public class ThemeManager {
	private Configuration conf;
	private long windowId;

	public ThemeManager(long windowId) {
		conf = Launch.getConfig("pde_thememanager");
		if (conf == null) {
			conf = new Configuration(new File(Launch.getConfD(), "pde_thememanager"));
			conf.setValue("cursor", "default");
			conf.setValue("background", "default");
			conf.setValue("windowDecorationAssetPack", "default");
			conf.setValue("individualAssetOverride", false);
		}
		this.windowId = windowId;
		setTheme();
	}

	private void setTheme() {
		String cursorType = (String) conf.getValue("cursor");
		String backgroundType = (String) conf.getValue("background");
		String windowDecor = (String) conf.getValue("windowDecorationAssetPack");
		boolean individualAssetOverride = (boolean) conf.getValue("individualAssetOverride");

		setCursor(cursorType);
		setBackground(backgroundType);
		setWindowDecorPack(windowDecor);
	}

	private void setCursor(String cursorType) {
		if (cursorType.equals("default")) {
			glfwSetCursor(windowId, GLFW_ARROW_CURSOR);
		}
		// TODO Create cursor data strucuture to store asset packs
	}

	private void setBackground(String backgroundType) {

	}

	private void setWindowDecorPack(String decorPack) {

	}

	private boolean setIndividualAsset(String assetType) {
		return true;
	}

}
