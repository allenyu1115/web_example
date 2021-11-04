package example;

import java.util.ArrayList;
import java.util.List;

public class Main {

	public static enum CharType {
		operator, number, undefine, blank, end
	}

	public static String getSExpression(String str) {
		if (str.length() == 0) {
			return str;
		} else {
			return getSExpressionRecursive(str, CharType.undefine, "", "", "");
		}

	}

	private static String getSExpressionRecursive(String str, CharType lastCharType, String currentNum,
			String lastNumberStr, String lastOperator) {
		String currentChar = str.isEmpty() ? "" : str.substring(0, 1);
		CharType currentCharType = getCharType(currentChar);
		if (currentCharType.equals(CharType.number)) {
			return getSExpressionRecursive(str.substring(1, str.length()), currentCharType, currentNum + currentChar,
					lastNumberStr, lastOperator);
		} else if (currentCharType.equals(CharType.operator)) {
			return getSExpressionRecursive(str.substring(1, str.length()), currentCharType, "",
					(lastOperator != "" && lastNumberStr != "")
							? combineToSExpression(lastOperator, lastNumberStr, currentNum)
							: currentNum,
					currentChar);
		} else if (currentCharType.equals(CharType.blank)) {
			return getSExpressionRecursive(str.substring(1, str.length()), lastCharType, currentNum, lastNumberStr,
					lastOperator);
		} else if (currentCharType.equals(CharType.end)) {
			return (lastNumberStr != "" && lastOperator != "")
					? combineToSExpression(lastOperator, lastNumberStr, currentNum)
					: "";
		}
		return "";

	}

	private static CharType getCharType(String currentChar) {
		CharType currentCharType = CharType.undefine;
		if (currentChar.equals(" ")) {
			currentCharType = CharType.blank;
		} else if (currentChar == null || currentChar.equals(""))
			currentCharType = CharType.end;
		else if (currentChar.chars().allMatch(Character::isDigit)) {
			currentCharType = CharType.number;
		} else if (currentChar.equals("+") || currentChar.equals("-") || currentChar.equals("*")
				|| currentChar.equals("/")) {
			currentCharType = CharType.operator;
		}
		return currentCharType;
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

		System.out.println(holder.getI());
		System.out.println(getSExpression("71 + 8 * 76 - 899 / 5"));
	}
}
