package avmp.backend.controllers;

import avmp.backend.dtos.TaskDTO;
import avmp.backend.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks/")
public class TaskController {
    private final TaskService taskService;
    
    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(summary = "Listar todas las tareas", description = "Devuelve una lista con todas las tareas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tareas encontradas"),
            @ApiResponse(responseCode = "404", description = "No hay tareas disponibles"),
    })
    @GetMapping
    public List<TaskDTO> getTasks() {
        return taskService.findAllTasks();
    }


    @Operation(summary = "Listar tarea por ID", description = "Devuelve una tarea")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tarea encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskDTO.class))),
            @ApiResponse(responseCode = "404", description = "Tarea no encontrada"),
    })
    @GetMapping("{id}")
    public ResponseEntity<?> view(@PathVariable long id) {
        Optional<TaskDTO> taskDTO = taskService.findTaskById((int) id);
        if (taskDTO.isPresent()) {
            return new ResponseEntity<>(taskDTO.orElseThrow(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Crear una nueva tarea", description = "Crea una tarea en el sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Tarea creada éxitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskDTO.class))),
            @ApiResponse(responseCode = "400", description = "Los datos ingresados son incorrectos"),
    })
    @PostMapping
    public ResponseEntity<?> createTask(@Valid @RequestBody TaskDTO taskDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return validation(bindingResult);
        }
        
        TaskDTO taskNew = taskService.save(taskDTO);
        return new ResponseEntity<>(taskNew, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar una tarea", description = "Actualizar los detalles de una tarea")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tarea actualizada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskDTO.class))),
            @ApiResponse(responseCode = "400", description = "Los datos ingresados son incorrectos"),
            @ApiResponse(responseCode = "404", description = "Tarea no encontrada"),
    })
    @PutMapping("{id}")
    public ResponseEntity<?> updateTask(@Valid @RequestBody TaskDTO taskDTO, BindingResult bindingResult, @PathVariable long id) {
        if (bindingResult.hasErrors()) {
            return validation(bindingResult);
        }
        
        Optional<TaskDTO> taskOptional = taskService.updateTask(taskDTO, (int) id);
        if (taskOptional.isPresent()) {
            return new ResponseEntity<>(taskOptional.orElseThrow(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Borrar una tarea por ID", description = "Elimina una tarea existente dado su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tareas eliminada con éxito"),
            @ApiResponse(responseCode = "404", description = "Tarea no encontrada"),
    })
    public ResponseEntity<?> deleteTask(@PathVariable long id) {
        Optional<TaskDTO> taskOptional = taskService.deleteTaskById((int) id);
        if (taskOptional.isPresent()) {
            return new ResponseEntity<>(taskOptional.orElseThrow(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<?> validation(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();

        bindingResult.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), "El campo " + error.getField() + " " + error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
