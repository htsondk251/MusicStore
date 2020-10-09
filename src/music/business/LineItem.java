package music.business;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.DecimalFormat;

import music.business.Product;

public class LineItem implements Serializable {
	private Long lineItemId;
	private Product product;
	private int quantity;

	public LineItem() {}


    public Long getLineItemId() {
        return lineItemId;
    }

    public void setLineItemId(Long lineItemId) {
        this.lineItemId = lineItemId;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotal() {
        double total = product.getPrice() * quantity;
        return total;
    }

    public String getTotalCurrencyFormat() {
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        return currency.format(this.getTotal());
/*
	NumberFormat formatter = new DecimalFormat("$0.00");
	return formatter.format(this.getTotal());
*/
    }	
}