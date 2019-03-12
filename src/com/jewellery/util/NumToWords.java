package com.jewellery.util;

import java.math.BigDecimal;

import net.sf.jasperreports.engine.JRDefaultScriptlet;

public class NumToWords extends JRDefaultScriptlet
{
  String string;
  String[] st1 = { "", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE" }; 
  String[] st2 = { "HUNDRED", "THOUSAND", "LAKH", "CRORE" }; 
  String[] st3 = { "TEN", "ELEVEN", "TWELVE", "THIRTEEN", "FOURTEEN", "FIFTEEN", "SIXTEEN", "SEVENTEEN", "EIGHTEEN", "NINTEEN" };
  String[] st4 = { "TWENTY", "THIRTY", "FOURTY", "FIFTY", "SIXTY", "SEVENTY", "EIGHTY", "NINTY" };

  public String convert(BigDecimal fnumber)
  {
    int n = 1;
    int word;
    int number =fnumber.intValue();
    this.string = "";
    while (number != 0) {
      switch (n) {
      case 1:
         word = number % 100;
        pass(word);
        if ((number > 100) && (number % 100 != 0)) {
          show("AND ");
        }
        number /= 100;
        break;
      case 2:
         word = number % 10;
        if (word != 0) {
          show(" ");
          show(this.st2[0]);
          show(" ");
          pass(word);
        }
        number /= 10;
        break;
      case 3:
         word = number % 100;
        if (word != 0) {
          show(" ");
          show(this.st2[1]);
          show(" ");
          pass(word);
        }
        number /= 100;
        break;
      case 4:
         word = number % 100;
        if (word != 0) {
          show(" ");
          show(this.st2[2]);
          show(" ");
          pass(word);
        }
        number /= 100;
        break;
      case 5:
         word = number % 100;
        if (word != 0) {
          show(" ");
          show(this.st2[3]);
          show(" ");
          pass(word);
        }
        number /= 100;
      }

      n++;
    }
    return this.string;
  }

  @SuppressWarnings("unused")
private static int parseInt(BigDecimal fnumber)
  {
    return 0;
  }

  public void pass(int number)
  {
    if (number < 10) {
      show(this.st1[number]);
    }
    if ((number > 9) && (number < 20)) {
      show(this.st3[(number - 10)]);
    }
    if (number > 19) {
      int word = number % 10;
      if (word == 0) {
        int q = number / 10;
        show(this.st4[(q - 2)]);
      } else {
        int q = number / 10;
        show(this.st1[word]);
        show(" ");
        show(this.st4[(q - 2)]);
      }
    }
  }

  public void show(String s)
  {
    String st = this.string;
    this.string = s;
    this.string += st;
  }

 /* public static void main(String[] args) {
    @SuppressWarnings("unused")
	NumToWords w = new NumToWords();

    double number = 12.0D;
    @SuppressWarnings("unused")
	int inum = (int)number;
  }*/
}