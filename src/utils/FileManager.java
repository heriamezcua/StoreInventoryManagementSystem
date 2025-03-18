package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import exceptions.FileManagerException;

/**
 * Utility class for managing file operations such as saving and loading
 * objects.
 */
public class FileManager {
	private static final String DATA_FOLDER = "./data/";
	private static final String LOG_FOLDER = "./logs/";
	private static final Logger LOGGER = Logger.getLogger(FileManager.class.getName());

	static {
		setupLogger();
	}

	/**
	 * Configures the logger to store logs in ./logs/app.log.
	 */
	private static void setupLogger() {
		try {
			File logDir = new File(LOG_FOLDER);
			if (!logDir.exists()) {
				logDir.mkdirs();
			}

			FileHandler fileHandler = new FileHandler(LOG_FOLDER + "app.log", true);
			fileHandler.setFormatter(new SimpleFormatter());
			LOGGER.addHandler(fileHandler);
			LOGGER.setUseParentHandlers(false); // Avoids logging to console
		} catch (IOException e) {
			e.printStackTrace(); // If logging fails, print to console as fallback
		}
	}

	/**
	 * Ensures that the data directory exists; creates it if necessary.
	 */
	private static void ensureDirectoryExists() {
		File directory = new File(DATA_FOLDER);
		if (!directory.exists() && directory.mkdirs()) {
			LOGGER.info("Created directory: " + DATA_FOLDER);
		}
	}

	/**
	 * Saves a list of objects to a file.
	 *
	 * @param <T>      The type of objects in the list.
	 * @param filename The name of the file where data will be stored.
	 * @param data     The list of objects to save.
	 */
	public static <T> void saveToFile(String filename, List<T> data) {
		ensureDirectoryExists();
		String filePath = DATA_FOLDER + filename;

		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
			oos.writeObject(data);
			LOGGER.info("Data saved successfully to " + filePath);
		} catch (IOException e) {
			LOGGER.severe("Error saving data to " + filePath + ": " + e.getMessage());
		}
	}

	/**
	 * Loads a list of objects from a file.
	 *
	 * @param <T>      The type of objects in the list.
	 * @param filename The name of the file to read data from.
	 * @return A list of objects loaded from the file.
	 * @throws FileManagerException if the file is not found or an error occurs
	 *                              while reading.
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> loadFromFile(String filename) {
		String filePath = DATA_FOLDER + filename;
		File file = new File(filePath);

		if (!file.exists()) {
			LOGGER.warning("File not found: " + filePath);
			throw new FileManagerException("File not found: " + filePath, null);
		}

		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
			LOGGER.info("Data loaded successfully from " + filePath);
			return (List<T>) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			LOGGER.severe("Error loading data from " + filePath + ": " + e.getMessage());
			throw new FileManagerException("Error loading data from " + filePath, e);
		}
	}
}
