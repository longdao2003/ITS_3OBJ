package vn.its.entity.respone;





import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubTaskRespone {
    private Long id;
    private String title;
    private String status;
    private int process;

    private String description;

}
