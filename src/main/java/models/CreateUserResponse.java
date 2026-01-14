package models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserResponse extends BaseModel {
    Long id;
    String username;
    String password;
    String name;
    String role;
    List<AccountsRequest> accounts;
}

