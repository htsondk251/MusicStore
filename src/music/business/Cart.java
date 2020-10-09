package music.business;

import java.io.Serializable;
import java.util.List;

import music.business.LineItem;

public class Cart implements Serializable {
	private List<LineItem> items;

	public Cart() {
		items = new ArrayList<LineItem>();
	}


    public void setItems(List<LineItem> lineItems) {
        items = lineItems;
    }

    public List<LineItem> getItems() {
        return items;
    }

    public int getCount() {
        return items.size();
    }


	public void addItem(LineItem item) {

		int i = items.indexOf(item);	//how to define 2 equal items???
		if (i != -1) {
			items.get(i).setQuantity(item.getQuantity());
		} else {
			items.add(item);
		}

/*
		String code = item.getProduct().getCode();
		int quantity = item.geProduct().getQuantity();

		for (LineItem lineItem : items) {
			if (lineItem.getProduct().getCode().equals(code)) {
				lineItem.setQuantity(quantity);
			}
		}

		items.add(item);
		
*/
	
		
	}


	pubic void removeItem(LineItem item) {
		if (items.size()>0) {
			String code = item.getProduct().getCode();
			for (LineItem lineItem : items) {
				if (lineItem.getProduct().getCode().equals(code)) {
					items.remove(lineItem);
					return;
				}
		}
	}
}