package org.judison.tradecalc;

import java.text.DecimalFormat;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class TradeCalc {

	@FXML
	private TextField edtBuyInventment;
	@FXML
	private TextField edtBuyPrice;
	@FXML
	private TextField edtBuyGrossAmount;
	@FXML
	private TextField edtBuyFeeValue;
	@FXML
	private TextField edtBuyNetAmount;
	@FXML
	private TextField edtBuyFee;
	@FXML
	private TextField edtSellPrice;
	@FXML
	private TextField edtSellGrossReturn;
	@FXML
	private TextField edtSellFeeValue;
	@FXML
	private TextField edtSellNetReturn;
	@FXML
	private TextField edtSellProfitValue;
	@FXML
	private TextField edtSellFee;
	@FXML
	private TextField edtSellProfit;

	private double buyInventment;
	private double buyPrice;
	private double buyFee = 0.25;
	private double sellPrice;
	private double sellFee = 0.25;

	private boolean forKeyTyped;

	@FXML
	public void onKeyTyped(Event ev) {
		Platform.runLater(() -> {
			forKeyTyped = true;
			try {
				calculate();
			} finally {
				forKeyTyped = false;
			}
		});
	}

	@FXML
	public void onCalculate() {
		calculate();
	}

	private final DecimalFormat fmt = new DecimalFormat("#,##0.00000000");
	private final DecimalFormat fmtPerc = new DecimalFormat("#,##0.00");

	private double get(TextField field, double defValue) {
		String text = field.getText();
		try {
			double value = fmt.parse(text).doubleValue();

			if (Double.isInfinite(value) || Double.isNaN(value) || value == 0) {
				error = true;
				return defValue;
			}

			if (forKeyTyped && field.isFocused())
				;
			else
				field.setText(fmt.format(value));
			return value;
		} catch (Throwable e) {
			error = true;
			return defValue;
		}
	}

	private double getPerc(TextField field, double defValue) {
		String text = field.getText();
		try {
			double value = fmtPerc.parse(text).doubleValue();

			if (Double.isInfinite(value) || Double.isNaN(value)) {
				error = true;
				return defValue;
			}

			if (forKeyTyped && field.isFocused())
				;
			else
				field.setText(fmtPerc.format(value));
			return value;
		} catch (Throwable e) {
			error = true;
			return defValue;
		}
	}

	private double set(TextField field, double value) {
		if (error)
			field.setText("");
		else
			field.setText(fmt.format(value));
		return value;
	}

	private double setPerc(TextField field, double value) {
		if (error)
			field.setText("");
		else
			field.setText(fmtPerc.format(value));
		return value;
	}

	private boolean error;

	private void calculate() {

		error = false;

		// Buy
		buyInventment = get(edtBuyInventment, buyInventment);
		buyPrice = get(edtBuyPrice, buyPrice);
		double buyGrossAmount = set(edtBuyGrossAmount, buyInventment / buyPrice);
		buyFee = getPerc(edtBuyFee, buyFee);
		double buyFeePerc = buyFee / 100;
		double buyFeeValue = set(edtBuyFeeValue, buyGrossAmount * buyFeePerc);
		double buyNetAmount = set(edtBuyNetAmount, buyGrossAmount - buyFeeValue);
		// Sell
		sellPrice = get(edtSellPrice, sellPrice);
		double sellGrossReturn = set(edtSellGrossReturn, buyNetAmount * sellPrice);
		sellFee = getPerc(edtSellFee, sellFee);
		double sellFeePerc = sellFee / 100;
		double sellFeeValue = set(edtSellFeeValue, sellGrossReturn * sellFeePerc);
		double sellNetReturn = set(edtSellNetReturn, sellGrossReturn - sellFeeValue);
		double sellProfitValue = set(edtSellProfitValue, sellNetReturn - buyInventment);
		setPerc(edtSellProfit, (sellProfitValue / buyInventment) * 100);

	}

	public void requestFocus() {
		edtBuyInventment.requestFocus();
	}

}
