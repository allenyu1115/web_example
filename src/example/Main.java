package example;

import java.util.ArrayList;
import java.util.List;

public class Main {
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

	public static Runnable increase(Holder holder) {
		holder.setI(increase(holder.getI()));
		return () -> holder.setI(decrease(holder.getI()));
	}

	public static void main(String[] args) {
             Holder holder = new Holder(1);
             System.out.println(holder.getI());
             
             List<Runnable> decreaseList = new ArrayList<>();
             
             decreaseList.add(increase(holder));
             decreaseList.add(increase(holder));
             decreaseList.add(increase(holder));
             decreaseList.add(increase(holder));
             
             System.out.println(holder.getI());
             
             Runnable lastOne = decreaseList.remove(decreaseList.size() -1);
             lastOne.run();
             
             System.out.println(holder.getI());
             
             Runnable secondOne = decreaseList.remove(decreaseList.size() -1);
             Runnable thirdOne = decreaseList.remove(decreaseList.size() -1);
             
             secondOne.run();
             thirdOne.run();
             
             System.out.println(holder.getI());
             
             
	}
}
