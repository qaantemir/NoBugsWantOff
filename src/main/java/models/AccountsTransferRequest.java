package models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountsTransferRequest extends BaseModel {
    Long senderAccountId;
    Long receiverAccountId;
    Double amount;

}
