package SNET.utils;

import java.io.File;
import java.io.IOException;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class UtilsForImage {
	
	//@Value("${project.manager.avatar.dir.path}")
	@Value("C:\\Folder")
	private static String avatarDirPath;
		
	public static String BIG_AVATAR_POSTFIX = "_big_thumb.png";
	
	public static void saveImages(MultipartFile[] files, String nameFile) {
	    
		if (files != null) {
			for (MultipartFile multipartFile : files) {
	            String filePath = avatarDirPath + File.separator + nameFile + File.separator;
	    
	            if(! new File(filePath).exists()) {
	                new File(filePath).mkdirs();
	            }
	            
	            try {
	                FileUtils.cleanDirectory(new File(filePath));
	        
	                String orgName = multipartFile.getOriginalFilename();
	                String fullFilePath = filePath + orgName;
	        
	                File dest = new File(fullFilePath);
	                multipartFile.transferTo(dest);
	                Thumbnails.of(dest).size(200, 200).crop(Positions.CENTER).toFile(new File(filePath + nameFile + BIG_AVATAR_POSTFIX));
	                
	            } catch (IllegalStateException e) {
	                System.out.println(e);
	                e.printStackTrace();
	            } catch (IOException e) {
	                System.out.println(e);
	                e.printStackTrace();
	            }
	        }
		}
	}
}
