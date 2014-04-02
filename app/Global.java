
import java.io.File;

import play.*;

public class Global extends GlobalSettings {

  @Override
  public void onStart(Application app) {
      createImgFolder();
  }  

  @Override
  public void onStop(Application app) {
    Logger.info("Application shutdown...");
  }  
  
  private void createImgFolder() {
      File img = new File("public/images");
      
      if (!img.exists()) {
          System.out.print("dir 'public/images' not found! Creating ... ");
          if (img.mkdirs())
              System.out.println("done.");
          else
              System.out.println("failed.");
      } else 
          System.out.println("found dir 'public/images'.");
  }
}