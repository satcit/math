package ru.satcit.math;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import com.alt.utils.gui.GuiMgr;

public class ExportRoolBaseAction {
  public static void run( File fileToExport, AbstractRoolBase... roolBases ) throws IOException {
    ObjectOutputStream out = null;
    try {
      out = new ObjectOutputStream(
          new BufferedOutputStream( new FileOutputStream( fileToExport, true ) ) );
      for( AbstractRoolBase base : roolBases ) {
        base.serialize( out );
      }
    } catch ( IOException e ) {
      GuiMgr.showError( e.getMessage() );
    } finally {
      out.close();
    }
  }
}
