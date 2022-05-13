package common;

import Collection.Organization;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

public class InputUtil {
    public static Organization requestOrganization(InputRequester inputRequester) {
        Function<String, Object> intFunction = input -> {
            try {
                int l = Integer.parseInt(input);
                return l > 0 ? l : null;
            } catch (NumberFormatException ex) {
                return null;
            }
        };
        return new Organization(
                inputRequester.requestInput("Введите название: ", input -> {
                    if (input.isEmpty()) {
                        return null;
                    }
                    return input;
                }), new Organization.Coordinates(inputRequester.requestInput("Введите Х: ", intFunction),
                inputRequester.requestInput("Введите У: ", intFunction)
        ),
                inputRequester.requestInput("Введите годовой оборот: ", input -> {
                    try {
                        Float f = Float.parseFloat(input);
                        return f > 0 ? f : null;
                    } catch (NumberFormatException ex) {
                        return null;
                    }
                }),
                inputRequester.requestInput("Введите фамилию: ", input -> {
                    if (input.isEmpty()) {
                        return null;
                    }
                    return input;
                }),
                inputRequester.requestInput("Введите количество рабочих: ", intFunction),
                inputRequester.requestInput(Arrays.stream(Organization.OrganizationType.values()).map(Enum::name)
                                .collect(Collectors.joining("\n", "Возможные варианты:  \n", "\n")) + "Введите тип организации: ",
                        input -> {
                            try {
                                return Organization.OrganizationType.valueOf(input);
                            } catch (IllegalArgumentException ex) {
                                return null;
                            }
                        }),
                new Organization.Address(inputRequester.requestInput("Введите почтовый индекс: (Должно быть больше 4 символов) ",
                        input -> input.length() < 4 ? "null" : input))
        );
    }
}
