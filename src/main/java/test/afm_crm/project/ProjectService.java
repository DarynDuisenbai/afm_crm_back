package test.afm_crm.project;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import test.afm_crm.common.exception.NotFoundException;
import test.afm_crm.project.dto.MemberInfo;
import test.afm_crm.project.dto.ProjectRequest;
import test.afm_crm.project.dto.ProjectResponse;
import test.afm_crm.task.TaskRepository;
import test.afm_crm.task.TaskStatus;
import test.afm_crm.user.User;
import test.afm_crm.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public List<ProjectResponse> getAllProjects() {
        return projectRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ProjectResponse createProject(ProjectRequest request, String ownerUsername) {
        User owner = userRepository.findByUsername(ownerUsername)
                .orElseThrow(() -> new NotFoundException("User not found: " + ownerUsername));

        Project project = Project.builder()
                .name(request.getName())
                .color(request.getColor())
                .ownerId(owner.getId())
                .memberIds(request.getMemberIds() != null ? request.getMemberIds() : List.of())
                .createdAt(LocalDateTime.now())
                .build();

        return toResponse(projectRepository.save(project));
    }

    public ProjectResponse updateProject(String id, ProjectRequest request) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Project not found: " + id));

        project.setName(request.getName());
        project.setColor(request.getColor());
        project.setMemberIds(request.getMemberIds() != null ? request.getMemberIds() : List.of());

        return toResponse(projectRepository.save(project));
    }

    public void deleteProject(String id) {
        taskRepository.deleteByProjectId(id);
        projectRepository.deleteById(id);
    }

    private ProjectResponse toResponse(Project project) {
        long totalTasks = taskRepository.countByProjectId(project.getId());
        long doneTasks = taskRepository.countByProjectIdAndStatus(project.getId(), TaskStatus.DONE);

        List<MemberInfo> members = userRepository.findAllById(project.getMemberIds())
                .stream()
                .map(MemberInfo::from)
                .toList();

        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .color(project.getColor())
                .totalTasks(totalTasks)
                .doneTasks(doneTasks)
                .members(members)
                .build();
    }
}
