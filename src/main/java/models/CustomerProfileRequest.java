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
public class CustomerProfileRequest extends BaseModel {
    @GeneratingRule(regex = "^[a-zA-Z]{1,50}\s[a-zA-Z]{1,50}$")
    String name;
}