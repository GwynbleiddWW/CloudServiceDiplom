package ru.netology.cloudservicediplom.repository;

import org.springframework.data.repository.CrudRepository;
import ru.netology.cloudservicediplom.model.Condition;
import ru.netology.cloudservicediplom.model.File;

import java.util.List;
import java.util.Optional;

public interface UploadedFileUserRepository extends CrudRepository<File, Long> {
    List<File> findByUsernameAndCondition(String username, Condition condition);

    Optional<File> findByUsernameAndNameAndCondition(String username, String name, Condition condition);
}
