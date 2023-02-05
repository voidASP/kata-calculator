import java.util.Scanner;

public class Main {
    final static String[] ROMANIANNUMBERS = new String[] { "C", "XC", "LXXX", "LXX", "LX", "L", "XL", "XXX", "XX", "X", "IX", "VIII", "VII", "VI", "V", "IV", "III", "II", "I" };
    final static int[] ARABICNUMBERS = new int[] { 100, 90, 80, 70, 60, 50, 40, 30, 20, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1 };
    public static void main(String[] args) throws CalculatorException {
        Scanner console = new Scanner(System.in);
        System.out.println("Введите выражение форматом \"a + b\" (принимаются только римские (I, II, III...) и арабские (1,2,3...)");
        System.out.println("цифры от 1 до 10 (от I до X), а также операторы \"+\", \"-\", \"*\", \"/\".");
        System.out.println("Нельзя использовать разные типы чисел в одном выражении.");
        String input = console.nextLine();
        System.out.println(calc(input));
    }
    static class CalculatorException extends Exception {
        public CalculatorException(String description) {
            super(description);
        }
    }
    static boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    static int toArabic(String romanianNumber) throws CalculatorException {
        int arabicNumber = 0;
        for (int i = ROMANIANNUMBERS.length - 1; i >= 0; i--) {
            arabicNumber++;
            if (romanianNumber.equalsIgnoreCase(ROMANIANNUMBERS[i])) {
                break;
            } else if ( arabicNumber > 10 ) {
                throw new CalculatorException("Одно из введенных чисел превышает допустимый диапазон значений.");
            }
        }
        return arabicNumber;
    }
    static int operation(String[] expression) throws CalculatorException {
        int a = Integer.parseInt(expression[0]);
        int b = Integer.parseInt(expression[2]);
        int arabicResult = 0;
        switch (expression[1]) {
            case "+":
                arabicResult = a + b;
                break;
            case "-":
                arabicResult = a - b;
                break;
            case "*":
                arabicResult = a * b;
                break;
            case "/":
                arabicResult = a / b;
                break;
            default:
                throw new CalculatorException("Оператор не соответствует заданному значению.");
        } return arabicResult;
    }
    public static String calc(String input) throws CalculatorException {
        int arabicResult = 0;
        String stringResult = "";
        String[] expression = input.split(" ");
        int a = 0;
        int b = 0;
        if (expression.length != 3) {
            throw new CalculatorException("Формат математической операции не удовлетворяет заданию.");
        }
        boolean isFirstnumber = tryParseInt(expression[0]);
        boolean isSecondNumber = tryParseInt(expression[2]);
        if (!isFirstnumber && isSecondNumber || isFirstnumber && !isSecondNumber) {
            throw new CalculatorException("Введенные значения не могут быть в разных системах счисления.");
        }
        if (isFirstnumber && isSecondNumber) {
            a = Integer.parseInt(expression[0]);
            b = Integer.parseInt(expression[2]);
            if (a > 10 || b > 10) {
                throw new CalculatorException("Одно из введенных чисел превышает допустимый диапазон значений.");
            }
            arabicResult = operation(expression);
            stringResult = arabicResult + "";
        } else if (!isFirstnumber && !isSecondNumber) {
            expression[0] = toArabic(expression[0]) + "";
            expression[2] = toArabic(expression[2]) + "";
            arabicResult = operation(expression);// переводим в арабское число второй операнд
            if (arabicResult <= 0) {
                throw new CalculatorException("В римской системе счисления нет 0 и отрицательных чисел.");
            }
            for(int i = 0; i < ARABICNUMBERS.length; i++) {
                while(arabicResult >= ARABICNUMBERS[i]) {
                    int temp = arabicResult / ARABICNUMBERS[i];
                    arabicResult = arabicResult % ARABICNUMBERS[i];
                    for(int j = 0; j < temp; j++)
                        stringResult += String.join("", ROMANIANNUMBERS[i]);
                }
            }
        }   return stringResult;
    }
}