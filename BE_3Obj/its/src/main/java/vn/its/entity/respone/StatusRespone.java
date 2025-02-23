package vn.its.entity.respone;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StatusRespone {
    private Long id;

    private String title;
    private String description;
}
