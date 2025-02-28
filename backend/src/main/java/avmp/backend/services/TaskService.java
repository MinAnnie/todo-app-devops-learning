package avmp.backend.services;

import avmp.backend.dtos.TaskDTO;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<TaskDTO> findAllTasks();

    Optional<TaskDTO> findTaskById(int id);

    TaskDTO save(TaskDTO task);

    Optional<TaskDTO> updateTask(TaskDTO task, long id);

    Optional<TaskDTO> deleteTaskById(int id);
}
