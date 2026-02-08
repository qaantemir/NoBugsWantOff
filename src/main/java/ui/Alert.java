package ui;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Alert {
  NAME_UPDATED_SUCCESSFULLY("✅ Name updated successfully!"),
  NAME_MUST_CONTAIN_TWO_WORDS_WITH_LETTERS_ONLY("Name must contain two words with letters only"),
  SUCCESSFULLY_DEPOSITED("✅ Successfully deposited"),
  UNSUCCESSFUL_DEPOSITED("❌ Please deposit less or equal to 5000$."),
  SUCCESSFULLY_TRANSFERRED("✅ Successfully transferred"),
  UNSUCCESSFUL_TRANSFERRED("❌ Error: Transfer amount cannot exceed 10000");

  private String alertText;
}
