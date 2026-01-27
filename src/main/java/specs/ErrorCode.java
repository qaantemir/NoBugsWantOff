package specs;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
  FORBIDDEN_UNAUTHORIZED_ACCESS(
      "Unauthorized access to account"
  ),
  ACCOUNT_DEPOSIT_BAD_REQUEST_DEPOSIT_AMOUNT_MUST_BE_AT_LEAST(
      "Deposit amount must be at least 0.01"
  ),
  ACCOUNT_DEPOSIT_BAD_REQUEST_DEPOSIT_AMOUNT_CANNOT_EXCEED(
      "Deposit amount cannot exceed 5000"
  ),
  ACCOUNT_TRANSFER_BAD_REQUEST_DEPOSIR_AMOUNT_MUST_BE_AT_LEAST(
      "Transfer amount must be at least 0.01"
  ),
  ACCOUNT_TRANSFER_BAD_REQUEST_DEPOSIT_AMOUNT_CANNOT_EXCEED(
      "Transfer amount cannot exceed 10000"
  ),
  ACCOUNT_TRANSFER_BAD_REQUEST_INVALID_TRANSFER(
      "Invalid transfer: insufficient funds or invalid accounts"
  ),
  CUSTOMER_PROFILE_BAD_REQUEST_NAME_MUST_CONTAIN(
      "Name must contain two words with letters only"
  );

  private final String errorCode;
}