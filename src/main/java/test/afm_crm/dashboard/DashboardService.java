package test.afm_crm.dashboard;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import test.afm_crm.contact.ContactRepository;
import test.afm_crm.contact.ContactStatus;
import test.afm_crm.dashboard.dto.DashboardStats;
import test.afm_crm.task.TaskRepository;
import test.afm_crm.task.TaskStatus;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final TaskRepository taskRepository;
    private final ContactRepository contactRepository;

    public DashboardStats getStats() {
        return DashboardStats.builder()
                .totalTasks(taskRepository.count())
                .inProgressTasks(taskRepository.countByStatus(TaskStatus.IN_PROGRESS))
                .doneTasks(taskRepository.countByStatus(TaskStatus.DONE))
                .totalContacts(contactRepository.count())
                .leads(contactRepository.countByStatus(ContactStatus.LEAD))
                .build();
    }
}
