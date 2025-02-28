package avmp.backend.services;

import avmp.backend.dtos.TaskDTO;
import avmp.backend.entities.Task;
import avmp.backend.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<TaskDTO> findAllTasks() {
        return taskRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<TaskDTO> findTaskById(int id) {
        return taskRepository.findById(id).map(this::convertToDTO);
    }

    @Transactional
    @Override
    public TaskDTO save(TaskDTO taskDTO) {
        Task task = convertToEntity(taskDTO);
        Task savedTask = taskRepository.save(task);
        return convertToDTO(savedTask);
    }

    @Override
    public Optional<TaskDTO> updateTask(TaskDTO taskDTO, long id) {
        Optional<Task> taskOptional = taskRepository.findById((int) id);

        if (taskOptional.isPresent()) {
            Task taskDB = taskOptional.get();
            
            taskDB.setTitle(taskDTO.getTitle());
            taskDB.setDescription(taskDTO.getDescription());
            
            Task updatedTask = taskRepository.save(taskDB);
            return Optional.of(convertToDTO(updatedTask));
        }
        return Optional.empty();
    }

    @Override
    public Optional<TaskDTO> deleteTaskById(int id) {
        Optional<Task> taskOptional = taskRepository.findById(id);

        taskOptional.ifPresent(task -> {
            taskRepository.delete(task);
        });
        
        return taskOptional.map(this::convertToDTO);
    }
    
    private TaskDTO convertToDTO(Task task) {
        return new TaskDTO(task.getId(), task.getTitle(), task.getDescription());
    }
    
    private Task convertToEntity(TaskDTO taskDTO) {
        return new Task(taskDTO.getId(), taskDTO.getTitle(), taskDTO.getDescription());
    }
}