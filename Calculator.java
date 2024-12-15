import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

    public class Calculator {

        private static final Map<Character, Integer> romanToArabic = new HashMap<>();

        static {
            romanToArabic.put('I', 1);
            romanToArabic.put('V', 5);
            romanToArabic.put('X', 10);
        }

        private static final String[] arabicToRoman = {
                "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"
        };

        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите математическое выражение (например, 1 + 2 или V * X):");

            String input = scanner.nextLine().trim();
            input = input.replaceAll(" ", ""); // Убираем пробелы
            String[] parts = input.split("(?<=[-+*/])|(?=[-+*/])"); // Разбиваем строку

            if (parts.length != 3) {
                throw new IllegalArgumentException("throws Exception // Формат математической операции не удовлетворяет заданию.");
            }

            String operand1 = parts[0];
            String operator = parts[1];
            String operand2 = parts[2];

            boolean isRoman = isRomanNumber(operand1) && isRomanNumber(operand2);
            boolean isArabic = isArabicNumber(operand1) && isArabicNumber(operand2);

            if (!(isRoman || isArabic)) {
                throw new IllegalArgumentException("throws Exception // Используются одновременно разные системы счисления.");
            }

            int num1, num2;

            if (isRoman) {
                num1 = romanToArabic(operand1);
                num2 = romanToArabic(operand2);
            } else {
                num1 = Integer.parseInt(operand1);
                num2 = Integer.parseInt(operand2);
            }

            if (num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10) {
                throw new IllegalArgumentException("throws Exception // Числа должны быть в диапазоне от 1 до 10 включительно.");
            }

            int result;
            switch (operator) {
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "*":
                    result = num1 * num2;
                    break;
                case "/":
                    if (num2 == 0) {
                        throw new ArithmeticException("throws Exception // Деление на ноль недопустимо.");
                    }
                    result = num1 / num2; // Целочисленное деление
                    break;
                default:
                    throw new IllegalArgumentException("throws Exception // Оператор не поддерживается.");
            }

            if (isRoman) {
                if (result < 1) {
                    throw new IllegalArgumentException("throws Exception // В римской системе нет нуля или отрицательных чисел.");
                }
                System.out.println("Результат: " + arabicToRoman(result));
            } else {
                System.out.println("Результат: " + result);
            }
        }


        private static boolean isRomanNumber(String s) {
            return s.matches("[IVXLCDM]+");
        }

        private static boolean isArabicNumber(String s) {
            return s.matches("\\d+");
        }

        private static int romanToArabic(String roman) {
            int result = 0;
            int prevValue = 0;
            for (int i = roman.length() - 1; i >= 0; i--) {
                int value = romanToArabic.get(roman.charAt(i));
                if (value < prevValue) {
                    result -= value;
                } else {
                    result += value;
                }
                prevValue = value;
            }
            return result;
        }

        private static String arabicToRoman(int number) {
            StringBuilder result = new StringBuilder();
            int[] arabicValues = {100, 90, 50, 40, 10, 9, 5, 4, 1};
            String[] romanValues = {"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
            for (int i = 0; i < arabicValues.length; i++) {
                while (number >= arabicValues[i]) {
                    result.append(romanValues[i]);
                    number -= arabicValues[i];
                }
            }
            return result.toString();
        }
    }
