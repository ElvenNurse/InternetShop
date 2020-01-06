package mate.academy.internetshop.model;

import java.util.ArrayList;
import java.util.List;

public class Bucket {
    private Long id;
    private List<Item> items;
    private Long userId;

    public Bucket(User user) {
        items = new ArrayList<>();
        userId = user.getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Item> getItems() {
        return items;
    }

    public Long getUserId() {
        return userId;
    }

    public void addItem(Item item) {
        if (items.contains(item)) {
            System.out.println(item.getName() + " already in bucket");
            return;
        }
        items.add(item);
    }

    public void deleteItem(Item item) {
        items.remove(item);
    }

    public void clear() {
        items.clear();
    }
}
