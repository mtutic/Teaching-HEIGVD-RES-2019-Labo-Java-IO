package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 *
 * Hello\n\World -> 1\tHello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
  private int counter = 0;  // line counter
  private char lastChar;    // last char written

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    for (int i = off; i < off + len; ++i) {
      write(str.charAt(i));
    }
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    for (int i = off; i < off + len; ++i) {
      write(cbuf[i]);
    }
  }

  @Override
  public void write(int c) throws IOException {
    // Check for \r line separator
    if (lastChar == '\r' && c != '\n'){
      out.write(++counter + "\t");
    }

    // Check if it's first line
    if (counter == 0) {
      out.write(++counter  + "\t");
    }
    out.write(c);

    // Check for \n line separator
    if (c == '\n') {
      out.write(++counter + "\t");
    }

    lastChar = (char) c;
  }

}
