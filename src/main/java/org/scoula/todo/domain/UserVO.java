package org.scoula.todo.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserVO {
//    각각의 컬럼명과 동일.
    private String id;
    private String password;
    private String name;
    private String role;
}
