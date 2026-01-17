package models;

import generators.GeneratingRule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest extends BaseModel {
    @GeneratingRule(regex = "^[A-Za-z0-9]{3,15}$")
    String username;
    @GeneratingRule(regex = "^[A-Z][a-z]\\d\\w{4}\\$$")
    String password;
    @GeneratingRule(role = RoleType.USER)
    RoleType role;
}