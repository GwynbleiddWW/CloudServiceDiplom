package repository;

import model.Condition;
import model.File;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UploadedFileUserRepository extends CrudRepository<File, Long> {
    List<File> findByUsernameAndCondition(String username, Condition condition);

    Optional<File> findByUsernameAndNameAndCondition(String username, String name, Condition condition);
}
