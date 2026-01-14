package models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountsTransferResponse extends BaseModel {
  private String message;
  private Long senderAccountId;
  private Long receiverAccountId;
  private Double amount;
}
