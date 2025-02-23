package vn.its.entity.request;




import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubTaskDTO {
    private String title;
    private String status;
    private int process;
 
    private String description;

}
