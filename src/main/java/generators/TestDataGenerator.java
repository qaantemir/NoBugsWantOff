package generators;

import com.github.curiousoddman.rgxgen.RgxGen;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import lombok.SneakyThrows;
import models.AuthLoginRequest;
import models.CreateUserRequest;
import models.RoleType;

public class TestDataGenerator {

  private static final Random random = new Random();

  @SneakyThrows
  public static <T> T generate(Class<T> clazz) {

    T instance = clazz.getDeclaredConstructor().newInstance();

    for (Field field : getAllFields(clazz)) {
      field.setAccessible(true);

      GeneratingRule rule = field.getAnnotation(GeneratingRule.class);

      Object v = switch (field) {
        case Field f when f.getType().equals(RoleType.class)                        // тип данных поля RoleType
            && f.getAnnotation(GeneratingRule.class) != null                        // аннотация GeneratingRule не нулл
            && f.getAnnotation(GeneratingRule.class).regex().isBlank()              // regex пустой
            && !f.getAnnotation(GeneratingRule.class).role().equals(RoleType.USER)  // не равен дефолтному (RoleType.USER)
            -> f.getAnnotation(GeneratingRule.class).role();                        // возвращаем указанный в аннотации RoleType

        case Field f when f.getType().equals(RoleType.class)                        // тип данных поля RoleType
            && f.getAnnotation(GeneratingRule.class) != null                        // аннотация GeneratingRule не нулл
            && f.getAnnotation(GeneratingRule.class).regex().isBlank()              // regex пустой
            && f.getAnnotation(GeneratingRule.class).role().equals(RoleType.USER)   // равен дефолтному (RoleType.USER)
            -> f.getAnnotation(GeneratingRule.class).role();                        // возвращаем указанный в аннотации RoleType

        case Field f when !f.getType().equals(RoleType.class)                       // тип данных поля не RoleType
            && f.getAnnotation(GeneratingRule.class) != null                        // аннотация GeneratingRule не нулл
            && !f.getAnnotation(GeneratingRule.class).regex().isBlank()             // regex пустой
            -> generateFromRegex(rule.regex(), field.getType());                    // возвращаем строку по регулярке
        default -> null;  // Аннотация есть, но поля пустые
      };

      field.set(instance, v);
    }

    return instance;
  }

//  public static <T> T generate(Class<T> clazz) {
//
//    try {
//      T instance = clazz.getDeclaredConstructor().newInstance();
//      for (Field field : getAllFields(clazz)) {
//        field.setAccessible(true);
//
//        Object value;
//        GeneratingRule rule = field.getAnnotation(GeneratingRule.class);
//        if (rule != null && !rule.regex().isEmpty()) {
//          value = generateFromRegex(rule.regex(), field.getType());
//        } else if (rule != null && !rule.role().equals(RoleType.USER)) {
//          value = rule.role();
//        } else {
////          value = generateRandomValue(field);
//          value = null;
//        }
//        field.set(instance, value);
//      }
//      return instance;
//    } catch (Exception e) {
//      throw new RuntimeException("Failed to generate entity", e);
//    }
//  }

  private static List<Field> getAllFields(Class<?> clazz) {
    List<Field> fields = new ArrayList<>();
    while (clazz != null && clazz != Object.class) {
      fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
      clazz = clazz.getSuperclass();
    }
    return fields;
  }

  private static Object generateRandomValue(Field field) {
    Class<?> type = field.getType();
    if (type.equals(String.class)) {
      return UUID.randomUUID().toString().substring(0, 8);
    } else if (type.equals(Integer.class) || type.equals(int.class)) {
      return random.nextInt(1000);
    } else if (type.equals(Long.class) || type.equals(long.class)) {
      return random.nextLong();
    } else if (type.equals(Double.class) || type.equals(double.class)) {
      return random.nextDouble() * 100;
    } else if (type.equals(Boolean.class) || type.equals(boolean.class)) {
      return random.nextBoolean();
    } else if (type.equals(List.class)) {
      return generateRandomList(field);
    } else if (type.equals(Date.class)) {
      return new Date(System.currentTimeMillis() - random.nextInt(1000000000));
    } else {
      return generate(type);
    }
  }

  private static Object generateFromRegex(String regex, Class<?> type) {
    RgxGen rgxGen = new RgxGen(regex);
    String result = rgxGen.generate();
    if (type.equals(Integer.class) || type.equals(int.class)) {
      return Integer.parseInt(result);
    } else if (type.equals(Long.class) || type.equals(long.class)) {
      return Long.parseLong(result);
    } else {
      return result;
    }
  }

  private static List<String> generateRandomList(Field field) {
    // Пытаемся определить generic-параметр списка
    Type genericType = field.getGenericType();
    if (genericType instanceof ParameterizedType) {
      ParameterizedType pt = (ParameterizedType) genericType;
      Type actualType = pt.getActualTypeArguments()[0];
      if (actualType == String.class) {
        return List.of(UUID.randomUUID().toString().substring(0, 5),
            UUID.randomUUID().toString().substring(0, 5));
      }
    }
    return Collections.emptyList();
  }

  public static class Founded {
    public static AuthLoginRequest getAuthLoginRequest(CreateUserRequest createUserRequest) {
      return AuthLoginRequest.builder()
          .username(createUserRequest.getUsername())
          .password(createUserRequest.getPassword())
          .build();
    }
  }
}