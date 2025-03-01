package vn.its.entity.request;




import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubTaskDTO {
    @NotBlank(message="Title không được để trống")
    @Size(min = 4, max = 20, message="Độ dài title phải từ 4->20 kí tự")
    @Pattern(regexp = "^[\\p{L}\\p{M}\\s.,!?(){}[\\\\]\"-]*$", message = "Title không được có kí tự lạ")
    private String title;
    private Long idStatus;
    private int process;
    private String description;

}
