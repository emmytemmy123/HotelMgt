package fcmb.com.good.repo.file;

import fcmb.com.good.model.entity.user.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepo extends JpaRepository<ImageData,Long> {

    Optional<ImageData> findByName(String fileName);
}
