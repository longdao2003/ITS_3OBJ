package vn.its.entity.request;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDTO {
    private String title;

    private int process;

    private String description;
   
    private String status;
}
