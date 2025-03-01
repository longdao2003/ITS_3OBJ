package vn.its.entity.model;





import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length= 20)
    private String title;
    
    
    private int process;
    private String description;
    @ManyToOne()
    @JoinColumn(name = "status",nullable = false)

    private Status status;


    @ManyToOne()
    @JoinColumn(name = "task",nullable = false)

    private Task task;

}
