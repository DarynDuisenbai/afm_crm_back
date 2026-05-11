package test.afm_crm.task;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskRepository extends MongoRepository<Task, String> {

    long countByProjectId(String projectId);

    long countByProjectIdAndStatus(String projectId, TaskStatus status);

    long countByStatus(TaskStatus status);

    void deleteByProjectId(String projectId);
}
