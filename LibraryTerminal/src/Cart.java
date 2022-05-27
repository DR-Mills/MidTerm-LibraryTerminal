import java.util.ArrayList;

public class Cart {
	private ArrayList<Media> cart = new ArrayList<Media>();

	public ArrayList<Media> getCart() {
		return cart;
	}

	public void setCart(ArrayList<Media> cart) {
		this.cart = cart;
	}
	public void addToCart(Media media) {
		cart.add(media);
	}
}
