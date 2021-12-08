package ru.netology.cloudservicediplom.repository;


import org.springframework.data.repository.CrudRepository;
import ru.netology.cloudservicediplom.model.TokenBlacklist;

public interface TokenBlacklistRepository extends CrudRepository<TokenBlacklist, String> {
}
