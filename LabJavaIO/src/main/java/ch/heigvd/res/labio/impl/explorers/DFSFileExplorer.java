package ch.heigvd.res.labio.impl.explorers;

import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;
import java.io.File;
import java.util.Arrays;

/**
 * This implementation of the IFileExplorer interface performs a depth-first
 * exploration of the file system and invokes the visitor for every encountered
 * node (file and directory). When the explorer reaches a directory, it visits all
 * files in the directory and then moves into the subdirectories.
 * 
 * @author Olivier Liechti
 */
public class DFSFileExplorer implements IFileExplorer {

  @Override
  public void explore(File rootDirectory, IFileVisitor visitor) {
    // Visit current directory
    visitor.visit(rootDirectory);

    // Check if it's empty
    if (!rootDirectory.exists())
      return;

    // Get list of all files and directories in directory and sort them
    File[] files = rootDirectory.listFiles();
    Arrays.sort(files);

    // Iterate over files and directories
    for (File file : files) {
      // Moves into subdirectories
      if (file.isDirectory()) {
        explore(file, visitor);
      } else if (file.isFile()) { // Or visit files
        visitor.visit(file);
      }
    }
  }

}
