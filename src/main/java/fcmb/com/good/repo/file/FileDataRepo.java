package fcmb.com.good.repo.file;

import fcmb.com.good.model.entity.file.FileData;
import fcmb.com.good.model.entity.file.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileDataRepo extends JpaRepository<FileData,Long> {

    Optional<FileData> findByName(String imageName);
}
