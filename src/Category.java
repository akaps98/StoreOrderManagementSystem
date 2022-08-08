import java.util.*;
import java.io.*;

public class Category {
    private String categoryName;
    private HashSet<Product> categoryProduct;

    public Category() {}

    public Category(String categoryName, HashSet<Product> categoryProduct) {
        this.categoryName = categoryName;
        this.categoryProduct = categoryProduct;
    }


    public void displayProductByCategory() throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        String nameCategory;
        while (true) {
            boolean invalid = false;
            System.out.println("Please enter product's category: ");
            nameCategory = scanner.nextLine();
            String[] array = nameCategory.split(" ");
            for (String s: array){
                if (!s.matches("[\\w]+")){
                    System.out.println("Please enter a valid product's category!");
                    invalid = true;
                }
            }
            if (invalid) continue;
            Scanner input = new Scanner(new File("product.db"));
            ArrayList<Product> productList = new ArrayList<>();
            while (input.hasNext()) {
                String[] line = input.nextLine().split(",");
                Product product = new Product(line[0], line[1], Integer.parseInt(line[2]), line[3]);
                if (product.getCategory().equalsIgnoreCase(nameCategory)) {
                    new Product().printProduct(product);
                    productList.add(product);
                };
            }
            input.close();
            if (productList.size() == 0) {
                System.out.println("There is no product of this category!");
            }
            break;}
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public HashSet<Product> getCategoryProduct() {
        return categoryProduct;
    }

    public void setCategoryProduct(HashSet<Product> categoryProduct) {
        this.categoryProduct = categoryProduct;
    }
}
