package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

