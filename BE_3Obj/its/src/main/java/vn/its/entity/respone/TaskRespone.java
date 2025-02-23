package vn.its.entity.respone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskRespone {
    private Long id;
    private String title;

    private int process;

    private String description;
   
    private String status;
}
