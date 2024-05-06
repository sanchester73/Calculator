import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IllegalAccessException {
        Scanner scanIn = new Scanner(System.in);
        System.out.println("Введите выражение из двух чисел от 1 до 10 или от I до X (римскими цифрами), разделённые пробелами и оператором: +, -, /, *");
        System.out.println(calc(scanIn.nextLine()));
    }

    public static String calc(String input) throws IllegalAccessException {
        String[] parcedBySpace = input.split(" ");
        if (parcedBySpace.length != 3) throw new IllegalAccessException("Некорректный формат ввода");
        boolean isA = isArab(parcedBySpace[0]) && isArab(parcedBySpace[2]);
        boolean isR = isRome(parcedBySpace[0]) && isRome(parcedBySpace[2]);
        if (!isA && !isR) throw new IllegalAccessException("Допустимы только два числа от 1 до 10 или от I до X (римскими цифрами)");
        int num1, num2;
        if (isA) {
            num1 = Integer.parseInt(parcedBySpace[0]);
            num2 = Integer.parseInt(parcedBySpace[2]);
        } else {
            num1 = toArab(parcedBySpace[0]);
            num2 = toArab(parcedBySpace[2]);
        }
        if (num1 >= 1 && num1 <= 10 && num2 >= 1 && num2 <= 10) {
            return switch (parcedBySpace[1]) {
                case "+" -> isA ? String.valueOf(num1 + num2) : toRome (num1 + num2);
                case "-" -> isA ? String.valueOf(num1 - num2) : toRome (num1 - num2);
                case "*" -> isA ? String.valueOf(num1 * num2) : toRome (num1 * num2);
                case "/" -> isA ? String.valueOf(num1 / num2) : toRome (num1 / num2);
                default -> throw new IllegalAccessException("Некорректный оператор");
            };
        }
        throw new IllegalAccessException("Допустимы только два числа от 1 до 10 или от I до X (римскими цифрами)");
    }

    private static boolean isArab(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isRome(String s) {
        String RomeNums = "IVX";
        for (char c : s.toCharArray()) {
            if (!RomeNums.contains(String.valueOf(c))) {
                return false;
            }
        }
        return true;
    }

    private static int toArab(String RomeNum) {
        int res = 0, i = 0;
        while (i < RomeNum.length()) {
            int curDig = getVal(RomeNum.charAt(i));
            if (i + 1 < RomeNum.length()) {
                int nextDig = getVal(RomeNum.charAt(i + 1));
                if (curDig >= nextDig) {
                    res += curDig;
                    i++;
                } else {
                    res += nextDig - curDig;
                    i += 2;
                }
            } else {
                res += curDig;
                i++;
            }
        }
        return res;
    }

    private static int getVal(char rome) {
        return switch (rome) {
            case 'I' -> 1;
            case 'V' -> 5;
            case 'X' -> 10;
            default -> 0;
        };
    }

    private static String toRome(int arabNum) throws IllegalAccessException {
        if (arabNum < 1) {
            throw new IllegalAccessException("В римской системе нет отрицательных чисел и нуля");
        }
        if (arabNum > 399) {
            throw new IllegalAccessException( "Обработка больше 399 не предусмотрена");
        }
        StringBuilder RomeNum = new StringBuilder();
        int[] vals = {100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] syms = {"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int i = 0;
        while (arabNum > 0) {
            int q = arabNum / vals[i];
            for (int x = 0; x < q; x++) {
                RomeNum.append(syms[i]);
                arabNum -= vals[i];
            }
            i++;
        }
        return RomeNum.toString();
    }
}
