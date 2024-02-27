package org.example;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class expressionInterpreter{
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        List<String> expressions = new ArrayList<>();

        for(int i = 0; i < n; i++){
            expressions.add(scanner.nextLine());
        }
        scanner.close();
        for(String ex : expressions){
            try{
                System.out.println(expressionCalculate(ex));
            } catch(Exception e){
                System.out.println("ERROR");
            }
        }
    }

    public static int expressionCalculate(String expression){
        Stack<Integer> numbers = new Stack<>();
        Stack<Character> operations = new Stack<>();
        for(int i = 0; i < expression.length(); i++){
            char c = expression.charAt(i);
            if(Character.isDigit(c)){
                int num = 0;
                while(i < expression.length() && Character.isDigit(expression.charAt(i))){
                    num = num * 10 + (expression.charAt(i) - '0');
                    i++;
                }
                i--;
                numbers.push(num);
            }
            else if(c == '('){
                int j = i + 1;
                int brackets = 1;
                while(brackets > 0){
                    if(expression.charAt(j) == '('){
                        brackets++;
                    }
                    else if(expression.charAt(j) == ')'){
                        brackets--;
                    }
                    j++;
                }
                int num = expressionCalculate(expression.substring(i + 1, j - 1));
                numbers.push(num);
                i = j - 1;
            }
            else if(c == '+' || c == '-' || c == '*' || c == '/'){
                while(!operations.isEmpty() && evalPriority(c, operations.peek())){
                    numbers.push(compute(operations.pop(), numbers.pop(), numbers.pop()));
                }
                operations.push(c);
            }
            else if(!Character.isWhitespace(c)){
                throw new IllegalArgumentException();
            }
        }

        while(!operations.isEmpty()){
            numbers.push(compute(operations.pop(), numbers.pop(), numbers.pop()));
        }

        return numbers.pop();
    }


    public static boolean evalPriority(char op1, char op2){
        if(op2 == '(' || op2 == ')'){
            return false;
        }
        if((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')){
            return false;
        }
        return true;
    }

    public static int compute(char op, int b, int a){
        switch(op){
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if(b == 0)
                    throw new UnsupportedOperationException();
                return a / b;
        }
        return 0;
    }
}