package example;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Main {

	public static enum CharType {
		operator, number, undefine
	}

	public static String getSExpression(String str, CharType lastCharType, String currentNum, Stack<String> numberStack,
			Stack<String> operatorStack) {
		if (str.isEmpty()) {
			if (!operatorStack.isEmpty() && !numberStack.isEmpty()) {
				return combineToSExpression(operatorStack.pop(), numberStack.pop(), currentNum);
			} else {
				return "";
			}
		} else {
			String currentChar = str.substring(0, 1);
			CharType currentCharType = CharType.undefine;
			if (Character.isDigit(currentChar.charAt(0))) {
				currentCharType = CharType.number;
			} else if (currentChar.equals("+") || currentChar.equals("-") || currentChar.equals("*")
					|| currentChar.equals("/")) {
				currentCharType = CharType.operator;
			}
			if (currentCharType.equals(CharType.number)) {
				return getSExpression(str.substring(1, str.length()), currentCharType, currentNum + currentChar,
						numberStack, operatorStack);
			} else if (currentCharType.equals(CharType.operator)) {
				if (!operatorStack.isEmpty() && !numberStack.isEmpty()) {
					numberStack.push(combineToSExpression(operatorStack.pop(), numberStack.pop(), currentNum));
				} else {
					numberStack.push(currentNum);
				}
				operatorStack.push(currentChar);
				return getSExpression(str.substring(1, str.length()), currentCharType, "", numberStack, operatorStack);
			} else {
				return getSExpression(str.substring(1, str.length()), lastCharType, currentNum, numberStack,
						operatorStack);
			}

		}

	}

	private static String combineToSExpression(String operator, String leftNum, String rightNum) {
		return "(" + operator + " " + leftNum + " " + rightNum + ")";
	}

	public static class Holder {
		private int i;

		public Holder(int i) {
			this.setI(i);
		}

		public int getI() {
			return i;
		}

		public void setI(int i) {
			this.i = i;
		}
	}

	public static int increase(int i) {
		return i + 1;
	}

	public static int decrease(int i) {
		return i - 1;
	}

	// increase action return decrease action
	public static Runnable increase(Holder holder) {
		holder.setI(increase(holder.getI()));
		return () -> holder.setI(decrease(holder.getI()));
	}

	// test!
	public static void main(String[] args) {
		Holder holder = new Holder(1);
		System.out.println(holder.getI());

		List<Runnable> decreaseList = new ArrayList<>();

		// increase four times
		decreaseList.add(increase(holder));
		decreaseList.add(increase(holder));
		decreaseList.add(increase(holder));
		decreaseList.add(increase(holder));

		System.out.println(holder.getI());

		// pop last increase action
		Runnable lastOne = decreaseList.remove(decreaseList.size() - 1);
		lastOne.run();

		// check the result
		System.out.println(holder.getI());

		// revoke twice again
		Runnable secondOne = decreaseList.remove(decreaseList.size() - 1);
		Runnable thirdOne = decreaseList.remove(decreaseList.size() - 1);

		secondOne.run();
		thirdOne.run();

		Stack<String> xx = new Stack<>();
		Stack<String> yy = new Stack<>();
		System.out.println(holder.getI());
		System.out.println(getSExpression("71 + 8 * 76 - 899 / 5", CharType.undefine, "", xx, yy));

	}
}
