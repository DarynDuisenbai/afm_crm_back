package test.afm_crm.task;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import test.afm_crm.common.exception.NotFoundException;
import test.afm_crm.task.dto.StatusUpdateRequest;
import test.afm_crm.task.dto.TaskRequest;
import test.afm_crm.task.dto.TaskResponse;
import test.afm_crm.user.UserRepository;
import test.afm_crm.user.dto.UserResponse;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final MongoTemplate mongoTemplate;

    public List<TaskResponse> getTasks(String projectId, String status, String assigneeId) {
        Query query = new Query();

        if (projectId != null) query.addCriteria(Criteria.where("projectId").is(projectId));
        if (status != null)    query.addCriteria(Criteria.where("status").is(parseStatus(status)));
        if (assigneeId != null) query.addCriteria(Criteria.where("assigneeId").is(assigneeId));

        return mongoTemplate.find(query, Task.class)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public TaskResponse createTask(TaskRequest request) {
        Task task = Task.builder()
                .title(request.getTitle())
                .status(parseStatus(request.getStatus()))
                .priority(parsePriority(request.getPriority()))
                .projectId(request.getProjectId())
                .assigneeId(request.getAssigneeId())
                .dueDate(request.getDueDate())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return toResponse(taskRepository.save(task));
    }

    public TaskResponse updateTask(String id, TaskRequest request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task not found: " + id));

        task.setTitle(request.getTitle());
        task.setStatus(parseStatus(request.getStatus()));
        task.setPriority(parsePriority(request.getPriority()));
        task.setProjectId(request.getProjectId());
        task.setAssigneeId(request.getAssigneeId());
        task.setDueDate(request.getDueDate());
        task.setUpdatedAt(LocalDateTime.now());

        return toResponse(taskRepository.save(task));
    }

    public TaskResponse updateStatus(String id, StatusUpdateRequest request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task not found: " + id));

        task.setStatus(parseStatus(request.getStatus()));
        task.setUpdatedAt(LocalDateTime.now());

        return toResponse(taskRepository.save(task));
    }

    public void deleteTask(String id) {
        taskRepository.deleteById(id);
    }

    private TaskResponse toResponse(Task task) {
        UserResponse assignee = null;
        if (task.getAssigneeId() != null) {
            assignee = userRepository.findById(task.getAssigneeId())
                    .map(UserResponse::from)
                    .orElse(null);
        }
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .status(task.getStatus())
                .priority(task.getPriority())
                .projectId(task.getProjectId())
                .assignee(assignee)
                .dueDate(task.getDueDate())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .build();
    }

    private TaskStatus parseStatus(String value) {
        if (value == null) return TaskStatus.TODO;
        return switch (value.toLowerCase()) {
            case "todo"                  -> TaskStatus.TODO;
            case "inprogress", "in_progress" -> TaskStatus.IN_PROGRESS;
            case "review"                -> TaskStatus.REVIEW;
            case "done"                  -> TaskStatus.DONE;
            default -> TaskStatus.valueOf(value.toUpperCase());
        };
    }

    private TaskPriority parsePriority(String value) {
        if (value == null) return TaskPriority.MEDIUM;
        return TaskPriority.valueOf(value.toUpperCase());
    }
}
