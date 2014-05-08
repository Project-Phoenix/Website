import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import play.*;

public class Global extends GlobalSettings {

  @Override
  public void onStart(Application app) {
      this.getTmpFolder();
  }  

  @Override
  public void onStop(Application app) {
    Logger.info("Application shutdown...");
  }  
  
  private void getTmpFolder() {
      try {
          File tmp = new File(System.getProperty("java.io.tmpdir").concat(File.separator).concat("img_phoenix"));
          File img = new File("public/images");
          if (!img.exists())
              img.mkdirs();
          Files.createSymbolicLink( img.toPath(), tmp.toPath()); 
      } catch (IOException e) {
          System.out.println("COULD NOT CREATE IMAGE FOLDER !!"+e);
      } catch (UnsupportedOperationException x) {
          // Some file systems do not support symbolic links.
          System.out.println("ATTENTION !!!");
          System.out.println(x);
      }
  }
}