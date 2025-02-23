package vn.its.entity.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private  String title;
    private  String description;

    @OneToMany(mappedBy = "status")
    private List<SubTask> subTasks;


    @OneToMany(mappedBy = "status")
    private List<Task> tasks;
}
