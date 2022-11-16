package bullscows;

import java.util.Random;
import java.util.Scanner;

public class Main {

  static Scanner s = new Scanner(System.in);

  public static void main(String[] args) {
    bullsAndCows();
  }

  public static void bullsAndCows() {
    int bulls = 0;
    int length = 0;
    int unique = 0;
    String input = "";
    try {
      System.out.printf("Input length of the secret code:%n");
      input = s.nextLine();
      length = Integer.parseInt(input);
      System.out.println("Input the number of possible symbols in the code:");
      input = s.nextLine();
      unique = Integer.parseInt(input);
    } catch (NumberFormatException e) {
      System.out.printf("Error: \"%s\" isn't a valid number.%n", input);
    }

    String random = generateNumber(length, unique);
    if ("".equals(random)) {
      return;
    }

    System.out.println("Okay, let's start a game!");
    for (int i = 1; bulls != length; i++) {
      System.out.printf("Turn %d:%n", i);
      input = s.nextLine();
      bulls = grade(random, input);
    }
    System.out.println("Congratulations! You guessed the secret code.");
  }

  public static int grade(String code, String input) {
    int bulls = 0;
    int cows = 0;
    for (int i = 0; i < code.length(); i++) {
      for (int j = 0; j < input.length(); j++) {
        if (code.charAt(i) == input.charAt(j)) {
          if (i == j) {
            bulls++;
          } else {
            cows++;
          }
        }
      }
    }

    if (bulls == 0 && cows == 0) {
      System.out.println("Grade: None.");
    } else {
      System.out.printf("Grade:%s%s%s.%n",
          bulls > 0 ? " " + bulls + " bull" + (bulls > 1 ? "(s)" : "") : "",
          bulls > 0 && cows > 0 ? " and" : "",
          cows > 0 ? " " + cows + " cow" + (cows > 1 ? "(s)" : "") : "");
    }
    return bulls;
  }

  public static String generateNumber(int length, int unique) {
    if (length > unique) {
      System.out.printf(
          "Error: it's not possible to generate a code with a length of %d with %d unique symbols.%n",
          length, unique);
      return "";
    }
    if (unique > 36) {
      System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z)");
      return "";
    }
    if (length <= 0) {
      System.out.println("Error: the length of the code must be at least 1");
      return "";
    }

    Random r = new Random();
    StringBuilder result = new StringBuilder(length);

    while (result.length() < length) {
      int rand = r.nextInt(unique) + 48; // ascii 0 starts at 48
      if (rand > 57) {
        rand += 97 - 58; // ascii a starts at 97
      }
      char randChar = (char) rand;
      if (result.lastIndexOf(String.valueOf(randChar)) != -1) {
        continue;
      }

      result.append((char) rand);
    }

    System.out.print("The secret is prepared: ");
    for (var ignored : result.toString().toCharArray()) {
      System.out.print("*");
    }

    StringBuilder chars = new StringBuilder();
    chars.append(" (0");
    if (unique <= 10 && unique > 1) {
      chars.append("-");
      chars.append((char) (unique + 47));
    }
    if (unique > 10) {
      chars.append("-9, a");
      if (unique > 11) {
        chars.append("-");
        chars.append((char) (unique + 86));
      }
    }

    chars.append(")");
    System.out.println(chars);

    return result.toString();
  }
}
