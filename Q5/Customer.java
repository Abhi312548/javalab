package fifth_test;

class Customer {
    private static int idCounter = 1;
    private int customerId;
    private String name;
    private String mobileNumber;

    public Customer(String name, String mobileNumber) {
        this.customerId = idCounter++;
        this.name = name;
        this.mobileNumber = mobileNumber;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }
}

class Item {
    private static int idCounter = 1;
    private int itemId;
    private String itemName;
    private double itemPrice;
    private int quantity;

    public Item(String itemName, double itemPrice, int quantity) {
        this.itemId = idCounter++;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
    }

    public int getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalCost() {
        return itemPrice * quantity;
    }
}


