/*
Многие программисты любят участвовать в турнирах по спортивному программированию.
Некоторые даже любят проводить свои турниры. Но они не хотят следить за тем, кто
сколько задач решил, сколько штрафных минут набрал, какое место занял. Они хотят
просто уметь по логу соревнований строить турнирную таблицу с количеством решённых
задач и штрафным временем, упорядоченную по убыванию результатов.
Одна из таких групп программистов попросила вас помочь им написать программу,
которая будет по логу соревнования строить итоговую турнирную таблицу.


Пример

Ввод:
Petya A 5:15 OK
Petya B 9:22 OK
Vasya A 12:11 WA
Petya C 15:00 OK
Vasya A 15:21 OK
Petya D 19:23 WA
Zhenya F 33:45 CE

Вывод:
+-+-------+-+-+-+-+-+-+-+--+
|1|Petya  |+|+|+|-| | |3|31|
+-+-------+-+-+-+-+-+-+-+--+
|2|Vasya  |+| | | | | |1|36|
+-+-------+-+-+-+-+-+-+-+--+
|3|Zhenya | | | | | |-|0| 0|
+-+-------+-+-+-+-+-+-+-+--+
*/




import java.util.*;

public class Table{

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Integer min = 0;
        Integer sec = 0;
        String time = "";
        String letter = "";
        String name = "";
        String result = "";
        int a = 0;
        boolean found = false;
        ArrayList<Person> all = new ArrayList<>();
        while (scanner.hasNext()) {
            found = false;
            name = scanner.next();

            letter = scanner.next();

            time = scanner.next();
            a = time.length();
            sec = Integer.parseInt(time.substring(a - 2, a));
            min = Integer.parseInt(time.substring(0, a - 3));
            result = scanner.next();
            if (all != null) for (int i = 0; i < all.size(); i++)
                if (all.get(i).name.equals(name)) {
                    found = true;
                    if (!all.get(i).solved[letter.charAt(0) - 'A']) {
                        if (result.equals("OK")) {
                            all.get(i).penatly += min + (sec != 0 ? 1 : 0) + 20 * all.get(i).tried[letter.charAt(0) - 'A'];
                            all.get(i).solved[letter.charAt(0) - 'A'] = true;
                            all.get(i).total++;
                            all.get(i).tried[letter.charAt(0) - 'A']++;
                        } else all.get(i).tried[letter.charAt(0) - 'A']++;
                    }
                }
            if (!found) {
                all.add(new Person());
                all.get(all.size() - 1).name = name;
                all.get(all.size() - 1).penatly = 0;
                if (result.equals("OK")) {
                    all.get(all.size() - 1).penatly += min + (sec != 0 ? 1 : 0);
                    all.get(all.size() - 1).solved[letter.charAt(0) - 'A'] = true;
                    all.get(all.size() - 1).total++;
                    all.get(all.size() - 1).tried[letter.charAt(0) - 'A']++;
                } else all.get(all.size() - 1).tried[letter.charAt(0) - 'A']++;
            }
        }
        boolean wrong;
        Person tmp;
        for (int i = 0; i < all.size() - 1; i++)
            for (int j = i + 1; j < all.size(); j++) {
                wrong = all.get(i).comparepls(all.get(j));
                if (wrong) {
                    tmp = all.get(i);
                    all.set(i, all.get(j));
                    all.set(j, tmp);
                }
            }

        int len_numb = Integer.toString(all.size()).length();
        int max_total = Integer.toString(all.get(0).total).length();
        int name_numb = 0;
        for (int i = 0; i < all.size(); i++)
            if (all.get(i).name.length() > name_numb) name_numb = all.get(i).name.length();
        name_numb++;
        int max_problems_numb = 0;
        for (int i = 0; i < all.size(); i++)
            for (int j = 25; j >= 0; j--)
                if (all.get(i).tried[j] > 0) {
                    if (max_problems_numb < j) max_problems_numb = j;
                    break;
                }

        int max_pen_numb = 0;
        for (int i = 0; i < all.size(); i++)
            if (all.get(i).penatly > max_pen_numb) max_pen_numb = all.get(i).penatly;
        int mp_numb = Integer.toString(max_pen_numb).length();

        System.out.print("+");
        for (int i = 0; i < len_numb; i++) System.out.print("-");
        System.out.print("+");
        for (int i = 0; i < name_numb; i++) System.out.print("-");
        System.out.print("+");
        for (int i = 0; i <= max_problems_numb; i++) System.out.print("-+");
        for (int i = 0; i < max_total; i++) System.out.print("-");
        System.out.print("+");
        for (int i = 0; i < mp_numb; i++) System.out.print("-");
        System.out.print("+");
        System.out.println();

        for (int i = 0; i < all.size(); i++) {
            System.out.print("|");
            for (int j = 0; j < len_numb - Integer.toString(i+1).length(); j++) System.out.print(" ");
            System.out.print(i + 1);
            System.out.print("|" + all.get(i).name);
            for (int j = 0; j < name_numb - all.get(i).name.length(); j++) System.out.print(" ");
            for (int j = 0; j <= max_problems_numb; j++) {
                System.out.print("|");
                if (all.get(i).solved[j]) System.out.print("+");
                else if (all.get(i).tried[j] > 0) System.out.print("-");
                else System.out.print(" ");
            }
            System.out.print("|");
            for (int j = 0; j < max_total - Integer.toString(all.get(i).total).length(); j++) System.out.print(" ");
            System.out.print(all.get(i).total);
            System.out.print("|");
            for (int j = 0; j < mp_numb - Integer.toString(all.get(i).penatly).length(); j++) System.out.print(" ");
            System.out.print(all.get(i).penatly);
            System.out.print("|");
            System.out.println();


            System.out.print("+");
            for (int j = 0; j < len_numb; j++) System.out.print("-");
            System.out.print("+");
            for (int j = 0; j < name_numb; j++) System.out.print("-");
            System.out.print("+");
            for (int j = 0; j <= max_problems_numb; j++) System.out.print("-+");
            for (int j = 0; j < max_total; j++) System.out.print("-");
            System.out.print("+");
            for (int j = 0; j < mp_numb; j++) System.out.print("-");
            System.out.print("+");
            System.out.println();
        }
    }

}

class Person {
    String name;

    Person() {
        solved = new boolean[26];
        tried = new int[26];
        penatly = 0;
        name = "";
        total = 0;
    }

    boolean[] solved;
    int[] tried;
    int penatly;
    int total;

    boolean comparepls(Person other) {
        if (this.total > other.total) return false;
        if (this.total < other.total) return true;
        if (this.penatly < other.penatly) return false;
        if (this.penatly > other.penatly) return true;
        if (this.name.compareTo(other.name) > 0) return true;
        return false;
    }
}

