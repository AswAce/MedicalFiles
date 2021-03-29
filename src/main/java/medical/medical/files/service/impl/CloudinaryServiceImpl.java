package medical.medical.files.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.transformation.Layer;
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
//        String s = cloudinary.url().transformation(new Transformation()
//                .width(200).height(200).gravity("face").crop("thumb").chain()
//                .radius(20).border("5px_solid_black").chain()
//                .opacity(50).width(0.25).flags("relative").
//                        gravity("north_east").y(10).x(10)).imageTag(toString);


        return "";
    }
}
