package medical.medical.files.service.impl;

import com.cloudinary.Cloudinary;
import medical.medical.files.service.CloudinaryService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {
    private static final String TEMP_FILE = "temp-file";
    private static final String URL = "url";

    private final Cloudinary cloudinary;

    public CloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String uploadImage(MultipartFile multipartFile) throws IOException {
        if (multipartFile == null || multipartFile.getOriginalFilename() == null) {
            return "";
        }
        String originalFilename = multipartFile.getOriginalFilename();
        if (!originalFilename.isEmpty()) {


            File file = File.createTempFile(TEMP_FILE, originalFilename);
            multipartFile.transferTo(file);

            String toString = this.cloudinary
                    .uploader()
                    .upload(file, Collections.emptyMap())
                    .get(URL)
                    .toString();
            return toString;
        }
        return "";
    }
}
