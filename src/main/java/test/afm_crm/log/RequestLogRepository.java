package test.afm_crm.log;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RequestLogRepository extends MongoRepository<RequestLog, String> {

    List<RequestLog> findByUsername(String username);

    long countByUsername(String username);
}
