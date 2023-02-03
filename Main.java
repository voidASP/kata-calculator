import java.util.Scanner;

public class Main {
    final static String[] ROMANIANNUMBERS = new String[] { "C", "XC", "LXXX", "LXX", "LX", "L", "XL", "XXX", "XX", "X", "IX", "VIII", "VII", "VI", "V", "IV", "III", "II", "I" };
    final static int[] ARABICNUMBERS = new int[] { 100, 90, 80, 70, 60, 50, 40, 30, 20, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1 };
    public static void main(String[] args) throws CalculatorException {
        Scanner console = new Scanner(System.in);
        System.out.println("Введите выражение форматом \"a + b\" (принимаются только римские (I, II, III...) и арабские (1,2,3...)");
        System.out.println("цифры от 1 до 10 (от I до X), а также операторы \"+\", \"-\", \"*\", \"/\".");
        System.out.println("Нельзя использовать разные типы чисел в одном выражении.");
        String input = console.nextLine();                                                                              // создаем сканер
        System.out.println(calc(input));                                                                                // выводим в консоль результат вычислений
    }
    static class CalculatorException extends Exception {
        public CalculatorException(String description) {
            super(description);
        }
    }
    static boolean tryParseInt(String value) {                                                                          // проверяем введена строка или число
        try {                                                                                                           //
            Integer.parseInt(value);                                                                                    // если удается перевести в число, значит в строке число
            return true;                                                                                                // возвращаем true
        } catch (NumberFormatException e) {                                                                             // если получаем exception, значит в строке не число
            return false;                                                                                               // возвращаем false
        }
    }
    static int toArabic(String romanianNumber) throws CalculatorException {                                             // перевод римского числа в арабское
        int arabicNumber = 0;                                                                                           // инициализация локальной переменной
        for (int i = ROMANIANNUMBERS.length - 1; i >= 0; i--) {                                                         // перебираем массив с римскими числами пока не найдем эквивалентное число
            arabicNumber++;                                                                                             // увеличиваем счётчик, количество витков будет соответствовать арабскому числу
            if (romanianNumber.equalsIgnoreCase(ROMANIANNUMBERS[i])) {                                                            // если введенное римское число соответствует числу в массиве
                break;                                                                                                  // завершаем цикл
            } else if ( arabicNumber > 10 ) {                                                                           // если счётчик выше 10, значит введенное число не соответствует заданному диапазону
                throw new CalculatorException("Одно из введенных чисел превышает допустимый диапазон значений.");       // если число не найдено или не соответсвует заданному диапазону, завершаем программу с ошибкой
            }
        }
        return arabicNumber;                                                                                            // возвращаем значение арабского числа, если оно было найдено в массиве
    }
    static int operation(String[] expression) throws CalculatorException {                                              // проверяем оператор на соответствие заданному значению
        int a = Integer.parseInt(expression[0]);                                                                        // переводим переданный в метод элемент массива в число
        int b = Integer.parseInt(expression[2]);                                                                        // - // -
        int arabicResult = 0;
        switch (expression[1]) {                                                                                        // и выполняем необходимое действие
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
            default:                                                                                                    // если оператор не соответствует заданному списку значений
                throw new CalculatorException("Оператор не соответствует заданному значению.");                         // выбрасываем ошибку
        } return arabicResult;                                                                                          // возвращаем целочисленный результат выполнения программы
    }
    public static String calc(String input) throws CalculatorException {                                                // сам калькулятор
        int arabicResult = 0;                                                                                           // объявляем локальную переменную
        String stringResult = "";                                                                                       // объявляем переменную для строкового возвращаемого значения результата
        String[] expression = input.split(" ");                                                                   // разбиваем массив с введенными данными на операнды и оператор
        int a = 0;                                                                                                      // объявляем локальные переменные для самого выражения
        int b = 0;                                                                                                      // -//-
        if (expression.length != 3) {                                                                                   // если длина массива не соответствует 3 ( формату выражения операнд - оператор - операнд)
            throw new CalculatorException("Формат математической операции не удовлетворяет заданию.");                  // выбрасываем ошибку
        }
        boolean isFirstnumber = tryParseInt(expression[0]);                                                             // проверяем тип первого операнда
        boolean isSecondNumber = tryParseInt(expression[2]);                                                            // проверяем тип второго операнда
        if (!isFirstnumber && isSecondNumber || isFirstnumber && !isSecondNumber) {                                     // если первое или второе число отличаются друг от друга системой счисления
            throw new CalculatorException("Введенные значения не могут быть в разных системах счисления.");             // выбрасываем ошибку
        }
        if (isFirstnumber && isSecondNumber) {                                                                          // если оба числа целые
            a = Integer.parseInt(expression[0]);                                                                        // переводим из строки в число
            b = Integer.parseInt(expression[2]);                                                                        // -//-
            if (a > 10 || b > 10) {                                                                                     // если первое или второе число больше 10
                throw new CalculatorException("Одно из введенных чисел превышает допустимый диапазон значений.");       // выбрасываем ошибку
            }
            arabicResult = operation(expression);                                                                       // вычисляем выражение
            stringResult = arabicResult + "";                                                                           // переводим целочисленную переменную в строковую
        } else if (!isFirstnumber && !isSecondNumber) {                                                                 // если оба операнда имеют строковое значение (т.е. являются римскими числами из заданного диапазона)
            expression[0] = toArabic(expression[0]) + "";                                                               // переводим в арабское число первый операнд
            expression[2] = toArabic(expression[2]) + "";
            arabicResult = operation(expression);// переводим в арабское число второй операнд
            if (arabicResult <= 0) {
                throw new CalculatorException("В римской системе счисления нет 0 и отрицательных чисел.");
            }
            for(int i = 0; i < ARABICNUMBERS.length; i++) {                                                             // перевод результата арабских чисел в римское
                while(arabicResult >= ARABICNUMBERS[i]) {                                                               // пока результат вычислений больше или равен числу из массива целых чисел с индексом i
                    int temp = arabicResult / ARABICNUMBERS[i];                                                         // объявляем локальную переменную для хранения промежуточного значения и передаем в нее результат деления результата вычислений и первого i-того элемента целочисленного массива
                    arabicResult = arabicResult % ARABICNUMBERS[i];                                                     // вычисляем остаток от деления результата вычислений и i-того элемента целочисленного массива и присваиваем его результату вычислений
                    for(int j = 0; j < temp; j++)                                                                       // счётчик
                        stringResult += String.join("", ROMANIANNUMBERS[i]);                                   // при первом витке присвоится значение десятков, которое будет взято из i-того элемента массива с римскими числами, при втором витке будет присвоен разряд единиц
                }
            }
        }   return stringResult;                                                                                        // возвращаем результат вычислений в метод main
    }
}