package parallel;

import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.PrintStream;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import terra.shell.config.Configuration;
import terra.shell.launch.Launch;
import terra.shell.utils.keys.Event;

public class module extends terra.shell.modules.Module {
	private boolean headless;
	private Configuration conf;
	private int width, height;
	private long topWindow;
	private int glVersion;
	private long cursor;

	private GLFWErrorCallback errorCallback;

	@Override
	public String getName() {
		return "PDE";
	}

	@Override
	public void run() {
		if (headless)
			return;
		log.log("Starting PDE");
		glVersion = conf.getValueAsInt("glVersion");
		log.log("Got GL Version " + glVersion);
		boolean windowedMode = Boolean.parseBoolean((String) conf.getValue("windowedMode"));
		GLFW.glfwDefaultWindowHints();
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_TRUE);
		if (!windowedMode) {
			log.log("Creating fullscreen instance..");
			createFullScreenInstance();
		} else {
			log.log("Creating windowed instance...");
			createWindowedInstance();
		}
		new DesktopManager(topWindow, width, height);
	}

	private void createFullScreenInstance() {
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
		GLFW.glfwWindowHint(GLFW.GLFW_AUTO_ICONIFY, GLFW.GLFW_FALSE);

		topWindow = GLFW.glfwCreateWindow(100, 100, "DesktopManager", 0, 0);
		GLFW.glfwMakeContextCurrent(topWindow);

		GLFW.glfwSetCursor(topWindow, cursor);
		GLFW.glfwMakeContextCurrent(topWindow);
		GLFW.glfwShowWindow(topWindow);
		GLFW.glfwMaximizeWindow(topWindow);
	}

	private void createWindowedInstance() {

	}

	@Override
	public String getVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAuthor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOrg() {
		// TODO Auto-generated method stub
		return null;
	}

	public void init() {
		log.log("PDE Initializing on Main Thread...");
		GLFW.glfwSetErrorCallback(
				errorCallback = GLFWErrorCallback.createPrint(new PrintStream(log.getOutputStream())));
		log.debug(Thread.currentThread().getName());
		GL.createCapabilities();
		boolean glfwInit = GLFW.glfwInit();
		if (!glfwInit) {
			log.err("FAILED TO INIT GLFW");
			headless = true;
			return;
		}
		// GL.createCapabilities();
		log.log("PDE Finished LWJGL Main Thread INIT");
	}

	@Override
	public void onEnable() {
		log.log("Enabling Parallel Desktop Environment (PDE)...");
		// Check to see if headless or not
		headless = GraphicsEnvironment.isHeadless();
		if (headless) {
			log.log("Environment is headless, unable to continue");
			return;
		}
		conf = Launch.getConfig("paralleldesktop");
		if (conf == null) {
			conf = new Configuration(new File(Launch.getConfD(), "paralleldesktop"));
			conf.setValue("glVersion", 3);
			conf.setValue("windowedMode", false);
		}
		log.log("Finished enabling...");
	}

	@Override
	public void trigger(Event event) {
		// TODO Auto-generated method stub

	}

}
