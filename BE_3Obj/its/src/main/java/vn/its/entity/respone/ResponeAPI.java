package vn.its.entity.respone;

import java.util.List;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponeAPI {
    private boolean status;
    private String message;
    private List<?> data;
}
