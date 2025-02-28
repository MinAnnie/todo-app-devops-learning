package avmp.backend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private String title;
    private String description;
    
    public TaskDTO() {
    }
    
    public TaskDTO(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
