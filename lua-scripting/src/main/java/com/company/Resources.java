package com.company;

public class Resources {
	private int softMoney = 10;

	public boolean isEnoughMoney(int price) {
		return price < softMoney;
	}

	public void buy(int price) {

	}

	public int getSoftMoney() {
		return softMoney;
	}
}
