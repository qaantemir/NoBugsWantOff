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
public class AccountsRequest extends BaseModel {
    Long id;
    String accountNumber;
    Double balance;
    List<AccountsTransactionsResponse> transactions;
}
